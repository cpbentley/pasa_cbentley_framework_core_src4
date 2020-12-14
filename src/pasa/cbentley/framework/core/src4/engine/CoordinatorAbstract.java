package pasa.cbentley.framework.core.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.interfaces.ITechStateBO;
import pasa.cbentley.byteobjects.src4.interfaces.StatorWriterBO;
import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.framework.core.src4.app.IAppli;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherAppli;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.coredata.src4.engine.StatorReaderCoreData;
import pasa.cbentley.framework.coredata.src4.engine.StatorWriterCoreData;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;

/**
 * 
 * Coordinate the life cycle the {@link IAppli} to the {@link ILauncherHost}.
 * 
 * The {@link CoordinatorAbstract} implementation is the appendice of the {@link ILauncherHost}
 * 
 * One to zero/one relation between {@link CoordinatorAbstract} and {@link IAppli}
 * 
 * <li> Start them in the correct order
 * <li> The coordinator is used by the host canvas to interact stop/pause/start the application.
 * 
 * The Host is created before the application instance.
 * 
 * Because Android/J2ME launchers must extend a specific classes, we cannot have multiple inheritance
 * 
 * This class is that second head to the launcher. Its the primary API used by the Bentley framework.
 * 
 * {@link ILauncherHost} being more close to the metal and the host implementation.
 * 
 * The {@link ILauncherHost} creates a specific instance of {@link CoordinatorAbstract} which
 * will provide getter access to specific hosts such as Android's Activity or J2ME MIDlet
 * 
 * The {@link CoordinatorAbstract} starts the appli with a {@link ILauncherAppli}
 * 
 * For other stuff, see {@link CoreFrameworkCtx}
 * 
 * @author Charles Bentley
 *
 */
public abstract class CoordinatorAbstract implements IStringable {

   protected IAppli               app;

   protected CoreFrameworkCtx     cfc;

   protected ILauncherAppli       launcherAppli;

   protected ILauncherHost        launcherHost;

   protected IAppli               parent;

   protected StatorReaderCoreData stateReader;

   /**
    * The coordinator created needs an {@link ILauncherHost}
    * 
    * Its empty until a launch Appli method is called.
    * 
    * @param cfc
    * @param launcherHost
    */
   protected CoordinatorAbstract(CoreFrameworkCtx cfc, ILauncherHost launcherHost) {
      this.cfc = cfc;
      this.launcherHost = launcherHost;
   }

   public StatorReaderCoreData getStateReader() {
      return stateReader;
   }

   /**
    * {@link IAppli} notifies the {@link CoordinatorAbstract} that it has entered exit state on its own decision.
    * 
    * Coordinator does the necessary framework cleaning, state saving and exit the process depending on what the canvas owner 
    * 
    * Ask the host what it should do. it will depend on the wrapper.
    * Stand alone wrapper will mean exit.. otherwise application closes
    * If parent appli, return result to parent TODO
    */
   public void appliWantBeDestroyed() {
      subExit();
      //notify parent that we closed. if stand alone. exit
      if (parent != null) {

      } else {
         System.exit(0);
      }
   }

   /**
    * {@link IAppli} notifies the {@link CoordinatorAbstract} that it has entered pause state on its own decision.
    */
   public void appliWantBePaused() {
      subPause();
   }

   private StatorWriterCoreData stateWriter;

   private boolean              toStringIsFullDebug;

   /**
    * 
    */
   public void frameworkExit() {
      //#debug
      toDLog().pBridge("", this, CoordinatorAbstract.class, "frameworkExit");

      StatorWriterBO stateWriterCtx = getStateWriter().getStateWriter(ITechStateBO.TYPE_3_CTX);
      cfc.getUCtx().getCtxManager().stateWriteTo(stateWriterCtx);

      //modules outside the app have static settings. This is now all those objects are saved

      // first send exit hooks to the application
      if (app != null) {

         //first ask to write state
         app.stateWriteTo(stateWriter);

         //close the appmodule
         //unconditionally close the application
         app.amsAppExit();
      }

      //for all those objects which cannot be accounted for here.
      //downside is that the order of the calls is not under our control. Order in which they were added
      cfc.getUCtx().getEventBusRoot().sendNewEvent(IEventsCore.PID_4_LIFE, IEventsCore.PID_4_LIFE_5_DESTROYED, this);

      //then send exit hooks to the Manager subclass. this enable them to close VLC instances
      subExit(); //TODO send exit hooks to module that use VLC 

      LifeContext context = new LifeContext();
      cfc.lifeStopped(context);
      
      //serialize

      stateWriter.serialize();

      //host knows how to clean exit app, depending on wrapper context
      launcherHost.appExit();
   }

   public StatorWriterCoreData getStateWriter() {
      if (stateWriter == null) {
         String storeName = app.getAppCtx().getConfigApp().getAppName();
         stateWriter = new StatorWriterCoreData(cfc.getCoreDataCtx(), storeName);
      }
      return stateWriter;
   }

   /**
    * @param boState
    */
   public void stateReadFrom(StatorReader state) {
      cfc.getUCtx().getCtxManager().stateReadFrom(state);
   }

   public void frameworkPause() {
      if (app != null) {
         app.amsAppPause();
      }
      subPause();
      launcherHost.appPause();
   }

   /**
    * Restart the app
    */
   public void frameworkRestart() {
      frameworkStart(launcherAppli);
   }

   public void frameworkResume() {
      if (app != null) {
         app.amsAppStart();
      }
      subResume();
   }

   /**
    * At this stage, no Gui components have yet been created.
    * 
    * @param launcherAppli
    */
   public void frameworkStart(ILauncherAppli launcherAppli) {
      if (app != null) {
         throw new IllegalStateException("A Coordinator is a one time use for a single Appli. Create another coordinator");
         //app.amsDestroyApp();
      }
      //link os specifics to the driver from the launcher
      launcherHost.setOSSpecifics(cfc);
      this.launcherAppli = launcherAppli;
      startUIThread();
   }

   /**
    * Ask the host to run the start in its specific thread format.
    * 
    * Will call {@link CoordinatorAbstract#initUIThreadInside()} inside the ui thread or this thread
    * if host knows this is already the UI thread
    */
   protected abstract void startUIThread();

   /**
    * Called by implementation inside its ui thread
    */
   protected void initUIThreadInside() {
      //normal an app should create a canvas
      app = launcherAppli.createAppOnFramework(cfc); //launcher creates the app

      //check for existing state and if versions match

      app.amsAppLoad();

      //state cannot be loaded here. we have to check for app version before on the core app state
      app.amsAppStart();

      if (cfc.getCUC().getRootCanvas() == null) {
         //headless mode no canvas
      }

      if (toStringIsFullDebug) {
         Runnable run = new Runnable() {

            public void run() {
               //#debug
               toDLog().pFlow("After Main Launch", CoordinatorAbstract.this, CoordinatorAbstract.class, "startAppli:", LVL_05_FINE, false);

            }
         };
         cfc.getCUC().runGUI(run);
      }
   }

   public void toStringEnableFullDebug() {
      toStringIsFullDebug = true;
   }

   /**
    * Null if framework was not started
    * @return
    */
   public IAppli getAppli() {
      return app;
   }

   public CoreFrameworkCtx getCFC() {
      return cfc;
   }

   public ILauncherAppli getLauncherAppli() {
      return launcherAppli;
   }

   public ILauncherHost getLauncherHost() {
      return launcherHost;
   }

   public boolean isLoaded() {
      return app != null;
   }

   /**
    * Set by the creator of {@link CoordinatorAbstract}, the {@link ILauncherHost}
    * @param stateReader
    */
   public void setStateReader(StatorReaderCoreData stateReader) {
      this.stateReader = stateReader;
   }

   /**
    * Implementation takes the current screen configuration and loads the {@link ICanvasAppli}s of the App with the saved tech and positions.
    * <br>
    * 
    * launch default with {@link IAppli#getCanvas(int, ByteObject)}
    * <li> When implementation does not support multi screen, 
    * <li> When no state is present
    * 
    * 
    * @returns false, if last state could not be found.
    */
   public abstract boolean loadLastState();

   /**
    * If an apps already runs, destroys it.
    * 
    * @param launcherAppli
    */
   public void setAppliLauncher(ILauncherAppli launcherAppli) {
      this.launcherAppli = launcherAppli;
   }

   protected abstract void subExit();

   protected abstract void subPause();

   protected abstract void subResume();

   public IDLog toDLog() {
      return cfc.toDLog();
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, CoordinatorAbstract.class, "@line299");
      dc.rootCtx(cfc, CoreFrameworkCtx.class);
      toStringPrivate(dc);
      dc.nlLvl(app, "IAppli");
      dc.nlLvl(parent, "parent IAppli");
      dc.nlLvl(launcherAppli, "launcherAppli");
      dc.nlLvl(launcherHost, "launcherHost");
      dc.nlLvl(stateReader, "stateReader");
      dc.nlLvl(stateWriter, "stateWriter");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoordinatorAbstract.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return cfc.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
