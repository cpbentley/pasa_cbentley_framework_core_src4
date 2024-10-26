package pasa.cbentley.framework.core.framework.src4.interfaces;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.data.src4.ctx.CoreDataCtx;
import pasa.cbentley.framework.core.framework.src4.app.AppCtx;
import pasa.cbentley.framework.core.framework.src4.app.IAppli;
import pasa.cbentley.framework.core.framework.src4.app.IConfigApp;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.io.src4.ctx.CoreIOCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coredraw.src4.ctx.CoreDrawCtx;

/**
 * One time use call of {@link ICreatorAppli#createAppOnFramework(CoreFrameworkCtx)}.
 * <p>
 * In a multi application setup, this class allows to pass around the creator of an application until it is needed.
 * </p>
 * @see IAppli
 * 
 * @author Charles Bentley
 */
public interface ICreatorAppli extends IStringable {

   /**
    * Creates the {@link IAppli} in the given framework context {@link CoreFrameworkCtx}.
    * <br>
    * 
    * <p>
    * Creates the {@link AppCtx} returned by {@link IAppli#getAppCtx()}.
    * </p>
    *  * 
    * 
    * <p>
    * <b>What about {@link IConfigApp} </b> ? :<br>
    * It is fetched from {@link IDependencies}
    * 
    * 
    * </p>
   * 
   * <b>Objectifies</b> the configuration of an Application, irrespective of the configuration of the host,
   * 
   * It allows to list different appli configurations, save them, show them to the user.
   * 
   * {@link ICreatorAppli} is kinda the Android manifest.
   * It provides hook ups and configuration about the application.
    * <p>
    * The host has been initialized along with
    * 
    * <li> {@link CoreDrawCtx}
    * <li> {@link CoreUiCtx}
    * <li> {@link CoreIOCtx}
    * <li> {@link CoreDataCtx}
    * <li> {@link CoreFrameworkCtx}
    * <br>
    * </p>
    * 
    * <p>
    * It can only be called once per instance of {@link CoreFrameworkCtx}.
    * </p>
    * 
    * @param cfc {@link CoreFrameworkCtx}
    * @return {@link IAppli}
    * @throw {@link IllegalStateException} when called a 
    */
   public IAppli createAppOnFramework(CoreFrameworkCtx cfc);

   public IConfigApp getConfigApp();

   /**
    * 
    * @return
    */
   public UCtx getUC();

}
