package pasa.cbentley.framework.core.src4.interfaces;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.CtxManager;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.src4.app.AppCtx;
import pasa.cbentley.framework.core.src4.app.IAppli;
import pasa.cbentley.framework.core.src4.app.IConfigApp;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.coredata.src4.stator.StatorCoreData;
import pasa.cbentley.framework.coreui.src4.interfaces.IWrapperManager;

/**
 * The {@link ILauncherHost} is the first object to be instantiated.
 * <br>
 * <li> J2ME : it is a Midlet
 * <li> Android : it is an Activity
 * <li> J2SE Swing: it is nothing. There is no special requirement to run a J2SE app. A JFrame though.
 * <li> JUnit Test: It is nothing. One class is designed to implement the methods
 * <br>
 * <br>
 * As soon as possible, it loads the previous state, including ctx settings into the {@link CtxManager}
 * <br>
 * It creates all the {@link ACtx} required by the underlying platform (Swing,Android).
 * <br>
 * The {@link ILauncherHost} knows what kind of GUI is possible. To manage those possibilities, an {@link IWrapperManager}
 * is created by the {@link ILauncherHost} along with the host {@link CoordinatorAbstract}.
 * <br>
 * A Swing application using a Bentley Framework Application will uses a launcher that fits the context within which
 * the {@link IAppli} is used. 
 * 
 */
public interface ILauncherHost extends IStringable {

   /**
    * Create the {@link IConfigApp} for the {@link AppCtx} that is created by 
    * 
    * {@link ILauncherHost#startAppli(ICreatorAppli)}
    * 
    * @param uc
    * @return {@link IConfigApp}
    */
   public IConfigApp createConfigApp(UCtx uc);

   /**
    * The coordinator [second head] for this {@link ILauncherHost}.
    * 
    * The {@link CoordinatorAbstract} does most of the plumbing work. The {@link ILauncherHost} is mostly a glue class
    * to handle the host specificities.
    * 
    * The framework business logic of launching an {@link IAppli} is located in the {@link CoordinatorAbstract}.
    * @return
    */
   public CoordinatorAbstract getCoordinator();

   /**
    * Specific {@link IDependencies} for the {@link ICreatorAppli}. 
    * 
    * Example
    * <li> A Sound Ctx for generating sounds 
    * <li> String Producer
    * <li> OpenGL context.
    * 
    * @return
    */
   public IDependencies getDependencies();

   /**
    * Assigns the {@link ICreatorAppli} and creates the {@link IAppli} with the {@link ICreatorAppli#createAppOnFramework(CoreFrameworkCtx)}.
    * 
    * @param launcherAppli {@link ICreatorAppli}
    * @throws IllegalStateException if Launcher has already launched an {@link IAppli}
    */
   public void startAppli(ICreatorAppli launcherAppli);

   /**
    * Called by {@link CoordinatorAbstract} when application exits.
    * 
    * Launcher gets a chance to clean its own things up.
    */
   public void appExit();

   /**
    * Called by {@link CoordinatorAbstract} when application exits.
    * 
    * Launcher gets a chance to clean its own things up.
    */

   public void appPause();

   /**
    * Called by {@link CoordinatorAbstract} for launcher to set
    * 
    * Like linking os specific services with 
    * 
    * {@link CoreFrameworkCtx#registerServiceProvider(Object, int)}
    * 
    * The thread in which this is called is the starting thread.
    * 
    * In Swing, it means its not the Gui thread
    */
   public void setOSSpecifics(CoreFrameworkCtx hoc);

   /**
    * Owner {@link ICtx} of the {@link ILauncherHost}.
    * @return
    */
   public CoreFrameworkCtx getCFC();

   //#mdebug
   /**
    * Launcher creates the logging configuration to be used
    * 
    * @return ILoggingConfig implementation
    */
   public ILogConfigurator toStringGetLoggingConfig();

   //#enddebug
}
