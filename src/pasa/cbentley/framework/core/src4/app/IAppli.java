package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.src4.interfaces.ITechHost;
import pasa.cbentley.framework.coreui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.coreui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.tech.ITechCanvasHost;

/**
 * Interface for the host for controlling the application.
 * 
 * Its not possible to access the application context as such
 * <br>
 * If the host wants to manipulate the application, it must retain a reference to it
 * or cast the IAppli to its implementation.
 * Example:
 * 2 simple Applis draw a rectangle in a random color.
 * Swing app wants to access this color, you have to cast IAppli.
 * 
 * AppModule are handled by host devices(Android, J2ME) and framework (Swing)
 * <br>
 * <br>
 * Visible methods to the {@link CoordinatorAbstract} to interact with the {@link IAppli}.
 * <br>
 * <br>
 * The {@link ITechHost}.
 * <br>
 * Live statis of an {@link IAppli}
 * <li>
 * @author Charles-Philip
 *
 */
public interface IAppli extends ITechAppli, IStringable, IStatorable {

   /**
    * This method is called by the {@link CoordinatorAbstract} immediately after creating the {@link IAppli} object
    * <br>
    * The method {@link IAppli#getTechAppli()} will throw an exception if ams not loaded
    * 
    * @throws IllegalStateException when state is not {@link ITechAppli#STATE_0_CREATED}
    */
   public void amsAppLoad();

   /**
    * Application Management Software (AMS) calls this method to start the app for the user.
    * When called 
    * <li> {@link ITechAppli#STATE_3_PAUSED} calls resumes 
    * <li> {@link ITechAppli#STATE_2_STARTED} does nothing
    * <li> {@link ITechAppli#STATE_1_LOADED} starts the app
    * 
    * @throws IllegalStateException when state is {@link ITechAppli#STATE_0_CREATED} or {@link IAppli#STATE_4_DESTROYED}
    */
   public void amsAppStart();

   /**
    * 
    * @return
    */
   public int getState();

   /**
    * AMS calls this method when the Application will be closed.
    * <br>
    * For closing the App from inside the app, use the method {@link CoordinatorAbstract#appliWantBeDestroyed()}
    * <br>
    * Set state to {@link ITechAppli#STATE_4_DESTROYED}.
    * <br>
    * the {@link IAppli} must cleanup and release all resources. 
    * 
    * This can be called from any state except destroyed
    */
   public void amsAppExit();

   /**
    * Called by AppModule controller
    * 
    * This lets the app writes state to disk
    * 
    * @throws IllegalStateException when not in {@link ITechAppli#STATE_2_STARTED} state.
    */
   public void amsAppPause();
   
   
   /**
    * Must be called when state 
    * 
    * @throws IllegalStateException when not in {@link ITechAppli#STATE_3_PAUSED} state.
    */
   public void amsAppResume();
   

   /**
    * This method will return Factory settings of the Apps. Manifest value usually don't change.
    * <br>
    * Could be null if app is too small?
    * <br>
    * Each Module Loads its settings 
    * <br>
    * <br>
    * {@link ITechAppli}
    * @return
    * @see ITechCtxSettingsAppli
    */
   public ByteObject getTechAppli();

   /**
    * 
    * @return
    */
   public AppCtx getAppCtx();

   /**
    * Asks the application to create a {@link ICanvasAppli}, usually to meant to be displayed.
    * 
    * When you want to an existing canvas, the tech must define it, its a special tech
    * 
    * A {@link CanvasAppliAbstract} always has a non null {@link CanvasHostAbstract}
    * 
    * @param id
    * @param tech when null returns the default canvas for the given id, {@link ITechCanvasHost}
    * defining the properties of the canvas.
    * @return
    */
   public ICanvasAppli getCanvas(int id, ByteObject tech);

}
