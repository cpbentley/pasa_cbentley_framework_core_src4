package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.framework.src4.engine.CoreAppView;
import pasa.cbentley.framework.core.framework.src4.interfaces.IBOHost;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;

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
 * The {@link IBOHost}.
 * <br>
 * Live statis of an {@link IAppli}
 * <li>
 * @author Charles-Philip
 *
 */
public interface IAppli extends ITechAppli, IStringable {

   /**
    * This method is called by the {@link CoordinatorAbstract} immediately after creating the {@link IAppli} object
    * <br>
    * The method {@link IAppli#getCtxSettingsAppli()} will throw an exception if ams not loaded
    * 
    * @throws IllegalStateException when state is not {@link ITechAppli#STATE_0_CREATED}
    */
   public void amsAppLoad();

   /**
    * Application Management Software (AMS) calls this method to start the app for the user.
    * When called 
    * 
    * <p>
    * Shouldn't this creates the root Canvas anyways ?
    * </p>
    * 
    * <li> {@link ITechAppli#STATE_3_PAUSED} calls resumes 
    * <li> {@link ITechAppli#STATE_2_STARTED} does nothing
    * <li> {@link ITechAppli#STATE_1_LOADED} starts the app
    * 
    * @throws IllegalStateException when state is {@link ITechAppli#STATE_0_CREATED} or {@link IAppli#STATE_4_DESTROYED}
    */
   public void amsAppStart();

   /**
    * 
    * <li>{@link ITechAppli#STATE_0_CREATED}
    * <li>{@link ITechAppli#STATE_1_LOADED}
    * <li>{@link ITechAppli#STATE_2_STARTED}
    * <li>{@link ITechAppli#STATE_3_PAUSED}
    * <li>{@link ITechAppli#STATE_4_DESTROYED}
    * @return
    */
   public int getState();

   /**
    * The Application Management System (AMS) calls this method when the {@link IAppli} will be closed.
    * 
    * <p>
    * For closing the App from inside the app, use the method {@link CoordinatorAbstract#appliWantBeDestroyed()}
    * </p>
    * 
    * <p>
    * Set <b>state</b> to {@link ITechAppli#STATE_4_DESTROYED}.
    * Once this method returns, 
    * the {@link IAppli} must cleanup and release all resources. 
    * </p>
    * <br>
    * 
    * <p>
    * This can be called from any state except destroyed
    * </p>
    */
   public void amsAppExit();

   /**
    * Called by <b>Application Manager System</b> Module controller.
    * 
    * This lets the app writes state to disk
    * 
    * @throws IllegalStateException when not in {@link ITechAppli#STATE_2_STARTED} state.
    */
   public void amsAppPause();

   /**
    * Called by the {@link CoordinatorAbstract}.
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
    * {@link IBOCtxSettingsAppli}
    * @return
    * @see IBOCtxSettingsAppli
    */
   public ByteObject getCtxSettingsAppli();

   /**
    * The {@link AppCtx} owner of this appli.
    * 
    * @return
    */
   public AppCtx getAppCtx();

   /**
    * Asks the application to create an implementation of {@link ICanvasAppli}.
    * 
    * <p>
    * When you want to an existing canvas, the {@link IBOCanvasHost} can define it.
    * 
    * </p>
    * 
    * A {@link CanvasAppliAbstract} always has a non null {@link CanvasHostAbstract}
    * 
    * {@link ICanvasAppli} being {@link IStatorable} the {@link CoreAppView} creates
    * 
    * IBOCa
    * How do you send parameters that are unknown at this level ? Application level parameters.
    * 
    * The implementation of this method goes back down to the application level where those
    * parameters are known.
    * 
    * them from saved state.
    * 
    * Calling this method twice on a single Canvas Host ? Should not be possible.
    * 
    * Its a bonus for such hosts or specific App for those hosts.
    * 
    * <p>
    * Can you create a Canvas without using this method ? Directly ?
    * 
    * </p>
    * <p>
    * 
    * Does this method doing any registering ? I guess so. But then the Canvas has to register with Appli
    * since he has to own the AppCtx, that's easy.
    * </p>
    * 
    * An {@link ICanvasAppli} implementation belongs to the AppCtx of that application.
    * 
    * @param id creation time. or class ID from the Statorable
    * @param boCanvasHost {@link IBOCanvasHost} when null returns the default canvas for the given id, {@link IBOCanvasHost}
    * defining the properties of the canvas.
    * @param params TODO
    * @return {@link ICanvasAppli}
    */
   public ICanvasAppli createCanvas(int id, ByteObject boCanvasHost, Object params);


   /**
    * Returns an existing canvas. null if id is not linked to an {@link ICanvasAppli}
    * 
    * When not existing, it has to be fetched using {@link IBOCanvasHost#CANVAS_HOST_OFFSET_03_ID2}
    * 
    * Single Responsability Principle -> Avoid mixing Get/Create logics
    * @param id
    * @return
    */
   public ICanvasAppli getCanvas(int id);

   /**
    * The {@link ICanvasAppli} of the first {@link ICanvasHost} that was created.
    * 
    * <p>
    * {@link CoreUiCtx} controls the list of existing {@link ICanvasHost}, from which {@link ICanvasAppli}
    * can be get/set
    * </p>
    * 
    * Shortcut to {@link CoreUiCtx#getCanvasRootHost()}
    * @return null if none
    */
   public ICanvasAppli getCanvasRoot();

}
