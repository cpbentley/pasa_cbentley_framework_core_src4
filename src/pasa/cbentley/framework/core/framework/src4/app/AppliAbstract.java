package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.ITechStatorBO;
import pasa.cbentley.core.src4.ctx.CtxManager;
import pasa.cbentley.core.src4.ctx.IConfigU;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.i8n.IStringProducer;
import pasa.cbentley.core.src4.i8n.LocaleID;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.stator.IStatorOwner;
import pasa.cbentley.core.src4.stator.Stator;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.utils.DateUtils;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.ctx.IEventsCoreFramework;
import pasa.cbentley.framework.core.framework.src4.ctx.ObjectCFC;
import pasa.cbentley.framework.core.framework.src4.ctx.ToStringStaticCoreFramework;
import pasa.cbentley.framework.core.framework.src4.engine.CoreAppModel;
import pasa.cbentley.framework.core.framework.src4.engine.CoreAppView;
import pasa.cbentley.framework.coredata.src4.db.IByteStore;
import pasa.cbentley.framework.coredata.src4.stator.StatorCoreData;
import pasa.cbentley.framework.coredata.src4.stator.StatorReaderCoreData;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.coreui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.coreui.src4.event.AppliEvent;
import pasa.cbentley.framework.coreui.src4.event.BEvent;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.interfaces.ITechEventHost;

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
public abstract class AppliAbstract extends ObjectCFC implements IAppli, IBOCtxSettingsAppli, IStatorOwner {
   /**
    * 
    */
   protected AppCtx        apc;

   private CoreAppModel    coreAppModel;

   private CoreAppView     coreAppView;

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

   private StatorCoreData  stator;

   private LifeContext lifeContext;

   public AppliAbstract(AppCtx apc) {
      super(apc.getCFC());
      this.apc = apc;
      state = STATE_0_CREATED;
      lifeContext = new LifeContext(apc);
   }

   public void amsAppExit() {

      //increment running time value.
      int incr = DateUtils.getMinutes(startTime, System.currentTimeMillis());
      apc.getBOCtxSettings().increment(IBOCtxSettingsAppli.CTX_APP_OFFSET_05_RUNNING_TIME4, 4, incr);
      state = STATE_4_DESTROYED;

      apc.getUC().getApiManager().lifeStopped(lifeContext);
      
      try {
         IConfigApp configApp = apc.getConfigApp();
         if (configApp.isAppStatorWrite()) {
            amsAppExitWriteStator();
         } else {
            //#debug
            toDLog().pFlow("isAppStatorWrite returns false. Does not write App settings to disk", configApp, AppliAbstract.class, "amsAppExit@103", LVL_05_FINE, true);
         }
      } catch (Exception e) {
         //#debug
         toDLog().pEx("msg", this, AppliAbstract.class, "amsAppExit", e);
         e.printStackTrace();
      }

      subAppExit();
   }

   /**
    */
   protected void amsAppExitWriteStator() {
      //#debug
      toDLog().pFlow("Writing State From Disk", this, AppliAbstract.class, "amsAppExitWriteStator@116", LVL_05_FINE, true);

      //modules outside the app have static settings. This is now all those objects are saved
      StatorCoreData stator = getStator();

      CtxManager ctxManager = cfc.getUC().getCtxManager();
      ctxManager.stateOwnerWrite(stator);

      this.stateOwnerWrite(stator);

      stator.serializeToStore();

      //#debug
      toDLog().pData("Stator Written", stator, AppliAbstract.class, "amsAppExitWriteStator@124", LVL_05_FINE, false);
   }

   /**
    * 
    */
   public void amsAppLoad() {
      if (state != STATE_0_CREATED) {
         throwExceptionBadState("Pause");
      }
      try {
         amsAppLoadStator();
      } catch (Exception e) {
         //#debug
         toDLog().pEx("Error while loading state", this, AppliAbstract.class, "amsAppLoad", e);
         e.printStackTrace();
      }
      subAppLoad();
      state = STATE_1_LOADED;
   }

   /**
    * Inverse of {@link AppliAbstract#amsAppExitWriteStator()}
    */
   protected void amsAppLoadStator() {
      //#debug
      toDLog().pFlow("Reading State From Disk", this, AppliAbstract.class, "amsAppLoadStator@150", LVL_05_FINE, true);

      StatorCoreData stator = null;
      IConfigU configU = apc.getUC().getConfigU();
      if (configU.isStatorRead()) {
         stator = getStator();
         CtxManager ctxManager = cfc.getUC().getCtxManager();
         ctxManager.stateOwnerRead(stator);
      } else {
         //#debug
         toDLog().pFlow("IConfigU#isStatorRead returns false. Does not read settings from disk", configU, AppliAbstract.class, "amsAppLoadStator@147", LVL_05_FINE, true);

      }

      //in call stator cases. this method must be called
      subAppLoadPostCtxSettings();

      IConfigApp configApp = apc.getConfigApp();
      if (configApp.isAppStatorRead()) {
         //#debug
         toDLog().pFlow("isAppStatorRead returns true. Reading settings from disk", configApp, AppliAbstract.class, "amsAppLoadStator@147", LVL_05_FINE, true);

         if (stator != null) {
            this.stateOwnerRead(stator);
            
            
            amsAppLoadStatorCheckFail(stator);
            
         } else {
            //#debug
            toDLog().pFlow("Stator is null because of  IConfigU", configU, AppliAbstract.class, "amsAppLoadStator@147", LVL_05_FINE, true);
         }
      } else {
         //#debug
         toDLog().pFlow("isAppStatorRead returns false. Does not read settings from disk", configApp, AppliAbstract.class, "amsAppLoad@147", LVL_05_FINE, true);

      }


   }

   private void amsAppLoadStatorCheckFail(StatorCoreData stator) {
      if (stator.isFailed()) {
         //TODO test again with changed BO type
         //we have exceptions

         //#debug
         toDLog().pAlways("Error while loading previous state. Erasing old state...", this, AppliAbstract.class, "amsAppLoadStator", LVL_09_WARNING, true);
         stator.deleteDataAll();

         //#debug
         toDLog().pAlways("Previous state deleted. Loading again...", this, AppliAbstract.class, "amsAppLoadStator", LVL_09_WARNING, true);

         CtxManager ctxManager = cfc.getUC().getCtxManager();
         ctxManager.stateOwnerRead(stator);
         subAppLoadPostCtxSettings();
         this.stateOwnerRead(stator);
      }
   }

   /**
    * Called after the reading of code ctx saved state and before application saved ctx state.. 
    */
   protected abstract void subAppLoadPostCtxSettings();

   public void amsAppPause() {
      if (state != STATE_2_STARTED) {
         throwExceptionBadState("Pause");
      }

      apc.getUC().getApiManager().lifePaused(lifeContext);
      
      //launch an event in the GUI thread. here we are in the application thread
      AppliEvent ae = new AppliEvent(getCUC(), ITechEventHost.ACTION_8_APPLI_PAUSED);
      ae.setEventID(IEventsCoreFramework.PID_01_LIFE_02_APP_PAUSED);
      getCUC().publishEventOnAllCanvas(ae);

      subAppPause();

      state = STATE_3_PAUSED;
   }

   /**
    * 
    */
   public void amsAppResume() {
      if (state != STATE_3_PAUSED) {
         throwExceptionBadState("Resume");
      }
      int incr = DateUtils.getMinutes(pauseTime, System.currentTimeMillis());
      apc.getBOCtxSettings().increment(IBOCtxSettingsAppli.CTX_APP_OFFSET_06_STAND_BY_TIME4, 4, incr);
      
      apc.getUC().getApiManager().lifeResumed(lifeContext);
      
      
      subAppUnPaused();
      state = STATE_2_STARTED;
   }

   /**
    * 
    */
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
    * Template for starting an application called by {@link AppliAbstract#amsAppStart()}
    * 
    * <Li> Reads {@link IBOCtxSettingsAppli}
    * <ol>
    * <li> If first run, calls install {@link AppliAbstract#subAppFirstLaunch()}
    * </ol>
    * <li> {@link LocaleID}
    * <li> {@link AppliAbstract#loadState()}
    * <li> {@link AppliAbstract#loadCoreAppModel()}
    * <li> {@link AppliAbstract#loadCoreViewModel()}
    * <li> {@link AppliAbstract#showCanvasDefault()}
    * <li> {@link AppliAbstract#subAppStarted()}
    * 
    * @return
    */
   private void amsAppStartInside() {

      //check number of times run. if totally new install. run app installer.
      //check version. if do not match. run app updaters on current state to this version

      startTime = cfc.getTimeCtrl().getNowClock();

      IStringProducer strings = apc.getStrings();
      //coreStateApp are fixed global state defined by the framework for the app ctx
      //its rarely updated. its strucutre is well known in advance
      ByteObject coreStateApp = apc.getBOCtxSettings();
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

      int appstart = coreStateApp.get2(IBOCtxSettingsAppli.CTX_APP_OFFSET_04_STARTS2);
      appstart++;
      coreStateApp.set2(IBOCtxSettingsAppli.CTX_APP_OFFSET_04_STARTS2, appstart);
      if (appstart == 1) {
         //follows a scenario of pages.
         //called before installation
         //first launch is a special command, on which application queue their own commands
         subAppFirstLaunch();

         String versionOfState = coreStateApp.getVarCharString(CTX_APP_OFFSET_12_VARCHAR_VERSION10, 2);
         String versionOfCode = apc.getVersion();
         if (!versionOfCode.equals(versionOfState)) {
            //
            subAppVersionChange();
         }
      }

      //check if previous view state was successfully loaded

      if (getCtxSettingsAppli().hasFlag(IBOCtxSettingsAppli.CTX_APP_OFFSET_01_FLAG, IBOCtxSettingsAppli.CTX_APP_FLAG_1_HEADLESS)) {

         //TODO headless means a fake canvas for the executionctx of commands
         //remove headless. we don't want it
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
      ByteObject appliTech = getCtxSettingsAppli();
      int val = appliTech.get1(IBOCtxSettingsAppli.CTX_APP_OFFSET_08_ANTI_ALIAS1);
      if (val == IBOCtxSettingsAppli.CTX_APP_ALIAS_2_OFF) {
         val = IBOCtxSettingsAppli.CTX_APP_ALIAS_1_ON;
      } else {
         val = IBOCtxSettingsAppli.CTX_APP_ALIAS_2_OFF;
      }
      appliTech.set1(IBOCtxSettingsAppli.CTX_APP_OFFSET_08_ANTI_ALIAS1, val);

      //appli tech changed.. apply them to each canvas ?

      CanvasHostAbstract[] canvases = apc.getCFC().getCUC().getCanvases();
   }

   /**
    * When Application extends {@link CoreAppModel}, it also has to override this method
    * @return
    */
   public CoreAppModel createAppModel() {
      if (coreAppModel == null) {
         coreAppModel = new CoreAppModel(cfc);
      }
      return coreAppModel;
   }

   public CoreAppView createAppView() {
      if (coreAppView == null) {
         coreAppView = new CoreAppView(cfc);
      }
      return coreAppView;
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

   public abstract ICanvasAppli createCanvas(int id, ByteObject boCanvasHost, Object params);

   public ICanvasAppli getCanvas(int id) {
      ICanvasHost ch = apc.getCUC().getCanvasFromID(id);
      if (ch != null) {
         return ch.getCanvasAppli();
      }
      return null;
   }

   public CoreUiCtx getCUC() {
      return apc.getCUC();
   }

   /**
    * Return the active (shown) canvas for the Application.
    * 
    * @return never null. zero length is none
    */
   public ICanvasAppli[] getCanvasAll() {
      CanvasHostAbstract[] canvases = apc.getCUC().getCanvasesShown();
      ICanvasAppli[] appc = new ICanvasAppli[canvases.length];
      for (int i = 0; i < canvases.length; i++) {
         appc[i] = canvases[i].getCanvasAppli();
      }
      return appc;
   }

   /**
    * 
    * @return
    */
   public ICanvasHost[] getCanvasHostAll() {
      CanvasHostAbstract[] canvases = apc.getCUC().getCanvases();
      return canvases;
   }

   public ICanvasAppli getCanvasRoot() {
      CanvasHostAbstract canvasRoot = apc.getCUC().getCanvasRootHost();
      if (canvasRoot != null) {
         return canvasRoot.getCanvasAppli();
      }
      return null;
   }

   public ICtx getCtxOwner() {
      return apc;
   }

   /**
    * @return {@link IBOCtxSettingsAppli}
    */
   public ByteObject getCtxSettingsAppli() {
      return apc.getBOCtxSettings();
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
    * {@link IBOProfileApp}
    * 
    * {@link IBOProfileApp#PROFILE_OFFSET_08_NAME15}
    * 
    * @return
    */
   public ByteObject getProfileActive() {
      if (profileActive == null) {
         profileActive = apc.createEmptyProfile();
         //sets default
         String str = apc.getConfigApp().getProfileNameDef();
         profileActive.setVarCharString(IBOProfileApp.PROFILE_OFFSET_08_NAME15, 15, str);
      }
      return profileActive;
   }

   /**
    * Profile based DB name.
    * @param dbName
    * @return
    */
   public String getProfiledDBName(String dbName) {
      return getProfileString() + dbName;
   }

   /**
    * Returns available profiles.
    * @return
    */
   public IntToObjects getProfiles() {
      String storeName = getProfileStoreName();
      IntToObjects its = new IntToObjects(apc.getUC());
      IByteStore bs = getByteStore();
      if (bs != null) {
         int base = bs.getBase();
         byte[] data = bs.getBytes(storeName, base);
         if (data != null) {

         }
      }
      return its;
   }

   public String getProfileStoreName() {
      String appName = apc.getConfigApp().getAppName();
      return appName + "_Profiles";
   }

   /**
    * {@link IBOCtxSettingsAppli#CTX_APP_OFFSET_04_STARTS2}
    * @return
    */
   public int getBONumStarts() {
      ByteObject bo = getCtxSettingsAppli();
      return bo.get2(IBOCtxSettingsAppli.CTX_APP_OFFSET_04_STARTS2);
   }

   /**
    * {@link IBOProfileApp#PROFILE_OFFSET_08_NAME15} 
    * @return
    */
   public String getProfileString() {
      ByteObject active = this.getProfileActive();
      return active.getVarCharString(IBOProfileApp.PROFILE_OFFSET_08_NAME15, 15);
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

   public String getStatorName() {
      IConfigApp configApp = apc.getConfigApp();
      String storeName = configApp.getAppName();
      return storeName + getProfileString();
   }

   /**
    * Create it if null and add {@link StatorFactoryApp}
    * @return
    */
   public StatorCoreData getStator() {
      if (stator == null) {
         String storeName = getStatorName();
         stator = new StatorCoreData(cfc.getCoreDataCtx(), storeName);
         //dangerous parts where things could go wrong
         try {
            stator.checkConfigErase();
            stator.importFromStore();
         } catch (Exception e) {
            //#debug
            toDLog().pEx("msg", this, AppliAbstract.class, "getStator@488", e);
            e.printStackTrace();
            stator = new StatorCoreData(cfc.getCoreDataCtx(), storeName);
         }
      }
      return stator;
   }

   /**
    * Delete exsiting settings data on disk 
    */
   public void deleteStatorData() {
      getStator().deleteDataAll();
      stator = null;
      apc.getUC().getCtxManager().deleteStartData();
   }

   /**
    * Returns true when the container is not chained to another
    * @return
    */
   public boolean isMaster() {
      return true;
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
    * In this framework, we assume that first Canvas shown is single. That the minimum requirement for hosts.
    * 
    * <p>
    * If Application requires more stand alone canvases, it will create them itself in the {@link AppliAbstract#subAppStarted()} implementation.
    * provided the underlying host has the capabilities.
    * </p>
    * 
    * Called at startup by {@link AppliAbstract#amsAppStartInside()}
    */
   protected void showCanvasDefault() {

      //check if canvas id 0 is load

      CoreUiCtx cuc = apc.getCUC();

      if (this.getCanvasRoot() == null) {
         //load the default canvas
         ICanvasAppli canvas = createCanvas(0, null, null);
         IConfigApp configApp = apc.getConfigApp();
         
         String title = configApp.getAppName();
         String icon = configApp.getAppIcon();

         canvas.setTitle(title);
         canvas.setIcon(icon);

         ICanvasHost canvasHost = canvas.getCanvasHost();
         
         canvasHost.titleIconComesticUpdate();
         //#debug
         toDLog().pFlow("Creating default canvas with title=" + title + " icon=" + icon + " from ConfigApp", this, AppliAbstract.class, "showCanvasDefault", LVL_05_FINE, true);
         //the host implementation of canvas knows what is possible for a default position
         canvasHost.setDefaultStartPosition();
      }

      //show the canvases, either from state or from new
      cuc.showAllCanvases();
   }

   /**
    * Reads {@link AppliAbstract} data from state.
    * <li> Number of canvas
    * <li> Each {@link CanvasAppliAbstract} then reads state from the {@link Stator}
    * 
    * When and where is the positioning of the canvas done ?
    * Its a {@link CanvasHostAbstract} issue.
    */
   public void stateOwnerRead(Stator stator) {

      CoreAppModel model = createAppModel();
      model.stateOwnerRead(stator);

      CoreAppView view = createAppView();
      view.stateOwnerRead(stator);
   }

   public void stateOwnerWrite(Stator stator) {

      CoreAppModel model = createAppModel();
      model.stateOwnerWrite(stator);

      CoreAppView view = createAppView();
      view.stateOwnerWrite(stator);

   }

   /**
    * sub class of {@link AppliGeneric} can close things
    */
   protected abstract void subAppExit();

   /**
    * called when the app is launched first .
    * 
    * Returns a scenario of Activities (license agreement, install wizard, )
    */
   protected void subAppFirstLaunch() {

   }

   /**
    * Database connection/thread/style
    */
   protected abstract void subAppLoad();

   /**
    * Convention is that sub prefixed methods are abstract and don't have to be called
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

   /**
    * When the App is launched for the first time with a new code version.
    * 
    * <p>
    * Some data has to be migrated to new formats
    * </p>
    */
   protected void subAppVersionChange() {

   }

   protected void throwExceptionBadState(String msg) {
      throw new IllegalStateException(msg + ": bad state " + ToStringStaticCoreFramework.toStringState(state) + "(" + state + ")");
   }

   //#mdebug

   public void toString(Dctx dc) {
      dc.root(this, AppliAbstract.class, 570);
      dc.rootCtx(apc, AppCtx.class);
      dc.appendVarWithSpace("state", state);
      dc.appendBracketedWithSpace(ToStringStaticCoreFramework.toStringState(state));

      dc.appendVar("startTime", startTime);
      dc.appendVar("pauseTime", pauseTime);

      dc.nlLvl(profileActive, "profileActive");
      dc.nlLvl(stator, "stator");

      dc.nlLvl(parent, "parentAppli");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, AppliAbstract.class);
   }

   //#enddebug

}
