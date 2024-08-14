package pasa.cbentley.framework.core.framework.src4.engine;

import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechDev;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.framework.core.framework.src4.app.IAppli;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.ctx.ObjectCFC;
import pasa.cbentley.framework.core.framework.src4.interfaces.ICreatorAppli;
import pasa.cbentley.framework.core.framework.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasAppli;

/**
 * 
 * Coordinate the life cycle the {@link IAppli} to the {@link ILauncherHost}.
 * 
 * <p>
 * 
 * The {@link CoordinatorAbstract} implementation is the appendice of the {@link ILauncherHost}
 * 
 * One to zero/one relation between {@link CoordinatorAbstract} and {@link IAppli}
 * </p>
 * 
 * <li> Start them in the correct order
 * <li> The coordinator is used by the host canvas to interact stop/pause/start the application.
 * 
 * <p>
 * 
 * The Host is created before the application instance.
 * 
 * Because Android/J2ME launchers must extend a specific classes, we cannot have multiple inheritance
 * 
 * This class is that second head to the launcher. Its the primary API used by the Bentley framework.
 * </p>
 * 
 * <p>
 * 
 * {@link ILauncherHost} being more close to the metal and the host implementation.
 * 
 * The {@link ILauncherHost} creates a specific instance of {@link CoordinatorAbstract} which
 * will provide getter access to specific hosts such as Android's Activity or J2ME MIDlet
 * 
 * The {@link CoordinatorAbstract} starts the appli with a {@link ICreatorAppli}
 * 
 * For other stuff, see {@link CoreFrameworkCtx}
 * </p>
 * 
 * @author Charles Bentley
 *
 */
public abstract class CoordinatorAbstract extends ObjectCFC implements IStringable {

   protected IAppli        app;

   /**
    * We want a testCase to be able to read this
    */
   private volatile boolean hasStartedInitUI = false;

   private volatile boolean isExitingAlready;

   protected ICreatorAppli launcherAppli;

   protected ILauncherHost launcherHost;

   protected IAppli        parent;

   private boolean         toStringIsFullDebug;

   /**
    * The coordinator created needs an {@link ILauncherHost}
    * 
    * Its empty until a launch Appli method is called.
    * 
    * @param cfc
    * @param launcherHost
    */
   protected CoordinatorAbstract(CoreFrameworkCtx cfc, ILauncherHost launcherHost) {
      super(cfc);
      this.launcherHost = launcherHost;
   }

   /**
    * Called internally.
    * Should not be called by classes outside the framework
    * {@link IAppli} notifies the {@link CoordinatorAbstract} that it has entered exit state on its own decision.
    * 
    * Coordinator does the necessary framework cleaning, state saving and exit the process depending on what the canvas owner 
    * 
    * Ask the host what it should do. it will depend on the wrapper.
    * Stand alone wrapper will mean exit.. otherwise application closes
    * If parent appli, return result to parent TODO
    */
   public void appliWantBeDestroyed() {
      //#debug
      toDLog().pFlow("", this, CoordinatorAbstract.class, "appliWantBeDestroyed@93", LVL_05_FINE, true);

      //will check for 
      frameworkExit();

      //notify parent that we closed. if stand alone. exit
      if (parent != null) {
         //we do not want parent application to die. just notifies it
      } else {
         //#debug
         toDLog().pFlow("Calling System.exit(0)", null, CoordinatorAbstract.class, "appliWantBeDestroyed@line97", LVL_05_FINE, true);
         System.exit(0);
      }
   }

   /**
    * {@link IAppli} notifies the {@link CoordinatorAbstract} that it has entered pause state on its own decision.
    */
   public void appliWantBePaused() {
      subPause();
   }

   /**
    * In Any case calling this method does not call any host terminating methods
    */
   public void frameworkExit() {
      //#debug
      toDLog().pFlow("isExitingAlready=" + isExitingAlready, null, CoordinatorAbstract.class, "frameworkExit@117", ITechLvl.LVL_05_FINE, ITechDev.DEV_4_THREAD);

      if (isExitingAlready) {
         //#debug
         toDLog().pFlow("Thread potential Lock. Exiting method now...", null, CoordinatorAbstract.class, "frameworkExit@117", ITechLvl.LVL_05_FINE, ITechDev.DEV_4_THREAD);
         return;
      }

      isExitingAlready = true;

      //Ctx Manager Debug gives the whole tree of the application at the time of the exit

      //#debug
      toDLog().pFlowBig("StartOfMethod", getCFC().getUC().getCtxManager(), CoordinatorAbstract.class, "frameworkExit");

      // first send exit hooks to the application
      if (app != null) {
         //close the appmodule
         //unconditionally close the application
         app.amsAppExit();
      }

      //for all those objects which cannot be accounted for here.
      //downside is that the order of the calls is not under our control. Order in which they were added
      cfc.getUC().getEventBusRoot().sendNewEvent(IEventsCore.PID_04_LIFE, IEventsCore.PID_04_LIFE_5_DESTROYED, this);

      //then send exit hooks to the Manager subclass. this enable them to close VLC instances
      subExit(); //TODO send exit hooks to module that use VLC 

      cfc.getCUC().onExit();

      //host knows how to clean exit app, depending on wrapper context
      launcherHost.appExit();
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
      launcherHost.appPause();
   }

   /**
    * At this stage, no Gui components have yet been created.
    * 
    * @param launcherAppli
    */
   public void frameworkStart(ICreatorAppli launcherAppli) {
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
    * Null if framework was not started
    * @return
    */
   public IAppli getAppli() {
      return app;
   }

   public CoreFrameworkCtx getCFC() {
      return cfc;
   }

   public ICreatorAppli getCreatorAppli() {
      return launcherAppli;
   }

   public ILauncherHost getLauncherHost() {
      return launcherHost;
   }

   /**
    * Called by implementation inside its ui thread
    */
   protected void initUIThreadInside() {
      //#debug
      toDLog().pTest("msg", this, CoordinatorAbstract.class, "run", LVL_05_FINE, true);

      hasStartedInitUI = true;
      //normal an app should create a canvas
      app = launcherAppli.createAppOnFramework(cfc); //launcher creates the app

      //check for existing state and if versions match

      app.amsAppLoad();

      //state cannot be loaded here. we have to check for app version before on the core app state
      app.amsAppStart();

      //#debug
      toDLog().pFlow("", this, CoordinatorAbstract.class, "initUIThreadInside@line218", LVL_05_FINE, true);

      if (cfc.getCUC().getCanvasRootHost() == null) {
         //headless mode no canvas
      }

      if (toStringIsFullDebug) {
         Runnable run = new Runnable() {

            public void run() {
               //#debug
               toDLog().pInitBig("[toStringIsFullDebug=true] After Launch", CoordinatorAbstract.this, CoordinatorAbstract.class, "initUIThreadInside");

            }
         };
         IExecutor executor = cfc.getCUC().getExecutor();
         executor.executeMainLater(run);
      }
   }

   public boolean isHasStartedInitUI() {
      return hasStartedInitUI;
   }

   public boolean isLoaded() {
      return app != null;
   }

   /**
    * If an apps already runs, destroys it.
    * 
    * @param launcherAppli
    */
   public void setAppliLauncher(ICreatorAppli launcherAppli) {
      this.launcherAppli = launcherAppli;
   }

   /**
    * Ask the host to run the start in its specific thread format.
    * 
    * Will call {@link CoordinatorAbstract#initUIThreadInside()} inside the ui thread or this thread
    * if host knows this is already the UI thread
    */
   protected abstract void startUIThread();

   protected abstract void subExit();

   /**
    * Implementation takes the current screen configuration and loads the {@link ICanvasAppli}s of the App with the saved tech and positions.
    * <br>
    * 
    * <li> When implementation does not support multi screen, 
    * <li> When no state is present
    * 
    * 
    * @returns false, if last state could not be found.
    */
   protected abstract boolean subLoadLastState();

   protected abstract void subPause();

   protected abstract void subResume();

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CoordinatorAbstract.class, "@line299");
      dc.rootCtx(cfc, CoreFrameworkCtx.class);
      toStringPrivate(dc);
      dc.nlLvl(app, "IAppli");
      dc.nlLvl(parent, "parent IAppli");
      dc.nlLvl(launcherAppli, "launcherAppli");
      dc.nlLvl(launcherHost, "launcherHost");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoordinatorAbstract.class);
      toStringPrivate(dc);
   }

   public void toStringEnableFullDebug() {
      toStringIsFullDebug = true;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
