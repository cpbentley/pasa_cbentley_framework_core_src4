package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.ITechStateBO;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.byteobjects.src4.stator.StatorWriterBO;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.i8n.IStringProducer;
import pasa.cbentley.core.src4.i8n.LocaleID;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.utils.DateUtils;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.ctx.ToStringStaticFrameworkCore;
import pasa.cbentley.framework.core.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.coredata.src4.db.IByteStore;
import pasa.cbentley.framework.coredata.src4.engine.StatorReaderCoreData;
import pasa.cbentley.framework.coreui.src4.ctx.IBOTypesCoreUI;
import pasa.cbentley.framework.coreui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.coreui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.coreui.src4.event.BEvent;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;

/**
 * Base implementation of {@link IAppli}.
 * 
 * Belongs to a {@link CoreFrameworkCtx} in the {@link AppCtx}
 * 
 * When the Framework starts, the Appli starts and might need zero, one or several canvas
 * <li> zero (headless)
 * <li> one (common case)
 * <li> several (Desktop with several windows)
 * <br>
 * <br>
 * The Bentley framework uses several views and when a new view replaces another,
 * it may asks the host to display it into a new independant window because the 2 views
 * cooperate with each other. On a small phone screen that is not possible.
 * <br>
 * The container calls the {@link System#exit(int)} method only 
 * @author Charles Bentley
 *
 */
public abstract class AppliAbstract implements IAppli, ITechCtxSettingsAppli {
   /**
    * 
    */
   protected AppCtx        apc;

   /**
    * Application that spawned this instance. 
    * May be null
    */
   protected AppliAbstract parent;

   /**
    * 
    */
   protected long          pauseTime;

   private ByteObject      profileActive;

   /**
    * 
    */
   protected long          startTime;

   private int             state;

   private String          storeName;

   public AppliAbstract(AppCtx apc) {
      this.apc = apc;

   }

   public void amsAppExit() {
      //increment running time value.
      int incr = DateUtils.getMinutes(startTime, System.currentTimeMillis());
      apc.getSettingsBO().increment(ITechCtxSettingsAppli.CTX_APP_OFFSET_05_RUNNING_TIME4, 4, incr);
      state = STATE_4_DESTROYED;

      subAppExit();
   }

   /**
    * 
    */
   public void amsAppLoad() {
      if (state != STATE_0_CREATED) {
         throwExceptionBadState("Pause");
      }

      subAppLoad();
      state = STATE_1_LOADED;
   }

   public void amsAppPause() {
      if (state != STATE_2_STARTED) {
         throwExceptionBadState("Pause");
      }

      subAppPause();
      state = STATE_3_PAUSED;
   }

   /**
    * Return the active (shown) canvas for the Application
    * @return
    */
   public ICanvasAppli[] getCanvasActive() {
      CanvasHostAbstract[] canvases = apc.getCUC().getCanvasesShown();
      ICanvasAppli[] appc = new ICanvasAppli[canvases.length];
      for (int i = 0; i < canvases.length; i++) {
         appc[i] = canvases[i].getCanvasAppli();
      }
      return appc;
   }

   /**
    * 
    */
   public void amsAppResume() {
      if (state != STATE_3_PAUSED) {
         throwExceptionBadState("Resume");
      }
      int incr = DateUtils.getMinutes(pauseTime, System.currentTimeMillis());
      apc.getSettingsBO().increment(ITechCtxSettingsAppli.CTX_APP_OFFSET_06_STAND_BY_TIME4, 4, incr);
      subAppUnPaused();
      state = STATE_2_STARTED;
   }

   public void amsAppStart() {
      if (state == STATE_3_PAUSED) {
         amsAppResume();
         return;
      } else if (state == STATE_0_CREATED || state == STATE_4_DESTROYED) {
         throwExceptionBadState("Start");
      } else if (state == STATE_2_STARTED) {
         //do nothing
         return;
      }

      //#debug
      apc.toStringAssert(state == STATE_1_LOADED, "Loaded State");

      //what is appli needs to display a frame "connecting"

      amsAppStartInside();
      state = STATE_2_STARTED;

   }

   /**
    * Template for starting an application
    * @return
    */
   private void amsAppStartInside() {

      //check number of times run. if totally new install. run app installer.
      //check version. if do not match. run app updaters on current state to this version

      startTime = System.currentTimeMillis();

      IStringProducer strings = apc.getStrings();
      //coreStateApp are fixed global state defined by the framework for the app ctx
      //its rarely updated. its strucutre is well known in advance
      ByteObject coreStateApp = apc.getSettingsBO();
      String suffix = coreStateApp.getVarCharString(CTX_APP_OFFSET_09_VARCHAR_LANSUFFIX2, 2);

      LocaleID lc = strings.getLocale(suffix);
      if (lc != null) {
         strings.setLocalID(lc);
      }
      if (strings.getLocaleID() == null)
         throw new NullPointerException();

      //app settings are stored in 
      //ask the Launcher to

      // sets the profile

      int appstart = coreStateApp.get2(ITechCtxSettingsAppli.CTX_APP_OFFSET_04_STARTS2);
      appstart++;
      coreStateApp.set2(ITechCtxSettingsAppli.CTX_APP_OFFSET_04_STARTS2, appstart);
      if (appstart == 1) {
         //follows a scenario of pages.
         //called before installation
         subAppFirstLaunch();

         // call the installation routine of the application
         boolean isInstalled = install();
         if (!isInstalled) {
            //do something about it

         }
         //launch the first time wizard ? License agreement?

         String versionOfState = coreStateApp.getVarCharString(CTX_APP_OFFSET_12_VARCHAR_VERSION10, 2);
         String versionOfCode = apc.getVersion();
         if (!versionOfCode.equals(versionOfState)) {
            //
            subAppVersionChange();
         }
      }

      //check if previous view state was successfully loaded

      if (getTechAppli().hasFlag(ITechCtxSettingsAppli.CTX_APP_OFFSET_01_FLAG, ITechCtxSettingsAppli.CTX_APP_FLAG_1_HEADLESS)) {
         
         //#debug
         toDLog().pInit("Headless Flag", this, AppliAbstract.class, "amsAppStartInside", LVL_09_WARNING, false);
         return;
      }
      //when is the normal loading state of previous ? After install, agreement, connection established
      showCanvasDefault();

      subAppStarted();
   }

   /**
    * 
    */
   public void cmdToggleAlias() {
      ByteObject appliTech = getTechAppli();
      int val = appliTech.get1(ITechCtxSettingsAppli.CTX_APP_OFFSET_08_ANTI_ALIAS1);
      if (val == ITechCtxSettingsAppli.CTX_APP_ALIAS_2_OFF) {
         val = ITechCtxSettingsAppli.CTX_APP_ALIAS_1_ON;
      } else {
         val = ITechCtxSettingsAppli.CTX_APP_ALIAS_2_OFF;
      }
      appliTech.set1(ITechCtxSettingsAppli.CTX_APP_OFFSET_08_ANTI_ALIAS1, val);

      //appli tech changed.. apply them to each canvas ?

      CanvasHostAbstract[] canvases = apc.getCFC().getCUC().getCanvases();
   }

   /**
    * Publish this event. By default all events are sent to the root canvas
    * @param ge
    */
   public void eventPublish(BEvent ge) {
      //TODO main event bridge. not linked to canvas?
      apc.getCFC().getCUC().getRootCanvas().eventBridge(ge);
   }

   public IAppli getApp() {
      return apc.getAppli();
   }

   public AppCtx getAppCtx() {
      return apc;
   }

   protected IByteStore getByteStore() {
      IByteStore bs = apc.getCFC().getCoreDataCtx().getByteStore();
      return bs;
   }

   /**
    * A Container may contains several {@link AppliAbstract}.
    * A Swing windows with 3 applications side by side managed by a pure Swing interface.
    * <br>
    * Those ContainerBridges are not frame based.
    * <br>
    * They are used for legacy Swing applications to use Bentley applications.
    * <br>
    * 
    * @return
    */
   public AppliAbstract getParent() {
      return parent;
   }

   /**
    * {@link ITechProfile}
    * @return
    */
   public ByteObject getProfileActive() {
      return profileActive;
   }

   public String getProfiledDBName(String dbName) {
      return getProfileString() + dbName;
   }

   /**
    * Returns available profiles.
    * @return
    */
   public IntToObjects getProfiles() {
      IntToObjects its = new IntToObjects(apc.getUCtx());
      IByteStore bs = getByteStore();
      if (bs != null) {
         int base = bs.getBase();
         byte[] data = bs.getBytes(storeName, base);
         if (data != null) {

         }
      }
      return its;
   }

   public String getProfileString() {
      return this.getProfileActive().getVarCharString(ITechProfile.PROFILE_OFFSET_04_NAME30, 30);
   }

   /**
    * <li> {@link ITechAppli#STATE_0_CREATED}
    * <li> {@link ITechAppli#STATE_1_LOADED}
    * <li> {@link ITechAppli#STATE_2_STARTED}
    * <li> {@link ITechAppli#STATE_3_PAUSED}
    * <li> {@link ITechAppli#STATE_4_DESTROYED}
    */
   public int getState() {
      return state;
   }

   /**
    * @return {@link ITechCtxSettingsAppli}
    */
   public ByteObject getTechAppli() {
      return apc.getSettingsBO();
   }

   /**
    * Implements to install first stuff
    * 
    * @return true when installation was successful.
    */
   public boolean install() {

      return true;
   }

   /**
    * Returns true when the container is not chained to another
    * @return
    */
   public boolean isMaster() {
      return true;
   }

   /**
    * called when the app is launched first .
    * 
    * Returns a scenario of Activities (license agreement, install wizard, )
    */
   protected void subAppFirstLaunch() {

   }

   /**
    * 
    */
   protected void subAppVersionChange() {

   }

   public void notifyDestroyed() {
      apc.getCoordinator().appliWantBeDestroyed();
   }

   public void notifyPaused() {
      apc.getCoordinator().appliWantBePaused();
   }

   /**
    * Each modules save its state in a predictable manner
    * @param ms
    */
   public void saveState() {
   }

   /**
    * 
    */
   protected void showCanvasDefault() {
      CoordinatorAbstract coordinator = apc.getCoordinator();
      StatorReaderCoreData stateReader = coordinator.getStateReader();
      if (stateReader != null) {
         this.stateReadFrom(stateReader);
      }

      //we have to check on the view
      if (stateReader == null || stateReader.getStateReader(ITechStateBO.TYPE_1_VIEW).hasFlag(ITechStateBO.FLAG_1_FAILED)) {
         //for example, license agreement based on the version of the app
         //
         //load the default canvas
         ICanvasAppli canvas = getCanvas(0, null);
         String title = apc.getConfigApp().getAppName();
         canvas.setTitle(title);
         String icon = apc.getConfigApp().getAppIcon();
         canvas.setIcon(icon);
         canvas.getCanvasHost().titleIconComesticUpdate();
         //#debug
         toDLog().pFlow("Creating default canvas with title=" + title + " icon=" + icon + " from ConfigApp", this, AppliAbstract.class, "showCanvasDefault", LVL_05_FINE, true);
         //the host implementation of canvas knows what is possible for a default position
         canvas.getCanvasHost().setDefaultStartPosition();
      }

      //show the canvases, either from state or from new
      apc.getCFC().getCUC().showAllCanvases();
   }

   public void stateReadFrom(StatorReader state) {

      StatorReaderBO stator = (StatorReaderBO) state;

      //is the ui state driving the model? or is the model state driving the ui state ?
      //in single screen, ui state/model state always match
      //apc.getCFC().stateReadAppUi((StatorReaderBO) state);
      //
      //once canvases have been raised from their graves

      //what to put inside? a canvas appli with its state loaded
      //only the real appli can match the serialized id to the canvas implementation
      StatorReaderBO stateView = stator.getStateReader(ITechStateBO.TYPE_1_VIEW);

      if (stateView.hasData()) {
         stateView.addFactory(new AppStatorFactory(apc));
         try {
            //read the different available
            int numCanvases = stateView.getDataReader().readInt();
            for (int i = 0; i < numCanvases; i++) {
               int canvasID = stateView.getDataReader().readInt();
               ByteObject techCanvasHost = stateView.readByteObject();

               //#debug
               techCanvasHost.checkType(IBOTypesCoreUI.TYPE_5_TECH_CANVAS_HOST);

               CanvasAppliAbstract canvasAppli = (CanvasAppliAbstract) getCanvas(canvasID, techCanvasHost);
               canvasAppli.stateReadFrom(state);

            }
         } catch (Exception e) {
            e.printStackTrace();
            stateView.setFlag(ITechStateBO.FLAG_1_FAILED, true);
         }
         //check if we have at least one canvas
         if (apc.getCUC().getRootCanvas() == null) {
            stateView.setFlag(ITechStateBO.FLAG_1_FAILED, true);
         }
      } else {
         stateView.setFlag(ITechStateBO.FLAG_1_FAILED, true);
         //#debug
         toDLog().pData("No View Data", stateView, AppliAbstract.class, "stateReadFrom", LVL_05_FINE, true);
      }

      //apply model data to views
      StatorReaderBO stateModel = stator.getStateReader(ITechStateBO.TYPE_2_MODEL);

   }

   public void stateWriteTo(StatorWriter state) {
      StatorWriterBO stator = (StatorWriterBO) state;
      //the caller/coordinator decides the key(screen config) of this state if any 
      //our job is to write our state. period. 

      //asks host ui context to saves state of canvases?
      //they may have some specific data to save

      //we don't want j2me to handle any such state. only state of main canvas
      //delegate the ui host state read/write to the uictx
      //apc.getCFC().stateWriteAppUi((StatorWriterBO) state);

      //.the application is designed to not know if it has several canvas.
      // is that possible? We have Screen/Fragment/MyGui that are displayed in a Canvas.
      // by default, its over the other.. on j2se the user may want in a new canvas on a given screen
      // so we have to associate "Visiblity Action", depending on the host 
      // on a single screen, it would be as if the single canvas was divided in 2,3,4 sub areas
      // show replace, show on top, show new screen, system provides Alt tab inside the app
      // relation between perspective and state? 
      // ui perspective changes how the content is displayed
      // model perspective changes the content

      //that's why the UI state is decided by the CanvasHosts. An app can decide to behave outside the
      //paradigm described but then it has to access the CanvasHosts to learn how its own CanvasApplis

      //once canvases have been raised from their graves

      //the question is.. what kind of model state do we have? if its just a screen ID (maps the class name)..
      // the app code will create blank canvas type and that's it.

      StatorWriterBO stateWriterView = stator.getStateWriter(ITechStateBO.TYPE_1_VIEW);

      //state write flags tell us what kind of state to persist in this batch

      //what to put inside? We n
      CanvasHostAbstract[] canvases = apc.getCFC().getCUC().getCanvases();
      int numCanvases = canvases.length;
      stateWriterView.getDataWriter().writeInt(numCanvases);
      for (int i = 0; i < canvases.length; i++) {
         canvases[i].stateWriteTo(state);
      }
   }

   /**
    * sub class of {@link AppliGeneric} can close things
    */
   protected abstract void subAppExit();

   /**
    * Database connection/thread/style
    */
   protected abstract void subAppLoad();

   /**
    * Convetion is that sub prefixed methods are abstract and don't have to be called
    */
   protected abstract void subAppPause();

   /**
    * Hook up for the application.
    * 
    * What should i do here? Application wide initialization after windows have been shown.
    * 
    * It might be the default window(s) or the last saved state
    */
   protected abstract void subAppStarted();

   /**
    * 
    */
   protected void subAppUnPaused() {

   }

   protected void throwExceptionBadState(String msg) {
      throw new IllegalStateException(msg + ": bad state " + ToStringStaticFrameworkCore.toStringState(state) + "(" + state + ")");
   }

   //#mdebug
   public IDLog toDLog() {
      return apc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, AppliAbstract.class);
      dc.rootCtx(apc, AppCtx.class);
      dc.appendVarWithSpace("State", ToStringStaticFrameworkCore.toStringState(state) + "(" + state + ")");
      dc.appendVar("StartTime", startTime);
      dc.appendVar("pauseTime", pauseTime);
      dc.nlLvl(parent, "parentAppli");
      dc.appendVarWithSpace("storeName", storeName);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, AppliAbstract.class);
   }

   public UCtx toStringGetUCtx() {
      return apc.getUCtx();
   }
   //#enddebug

}
