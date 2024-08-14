package pasa.cbentley.framework.core.framework.src4.interfaces;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.data.src4.ctx.CoreDataCtx;
import pasa.cbentley.framework.core.framework.src4.app.AppCtx;
import pasa.cbentley.framework.core.framework.src4.app.AppliAbstract;
import pasa.cbentley.framework.core.framework.src4.app.IAppli;
import pasa.cbentley.framework.core.framework.src4.app.IConfigApp;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.io.src4.ctx.CoreIOCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coredraw.src4.ctx.CoreDrawCtx;

/**
 * 
 * Creates instance of {@link IAppli} an application given a {@link CoreFrameworkCtx}.
 * 
 * What about {@link IConfigApp} ?
 * 
 * Objectifies the configuration of an Application, irrespective of the configuration of the host,
 * 
 * It allows to list different appli configurations, save them, show them to the user.
 * 
 * {@link ICreatorAppli} is kinda the Android manifest.
 * It provides hook ups and configuration about the application.
 * @author Charles Bentley
 *
 */
public interface ICreatorAppli extends IStringable {

   /**
    * Create the {@link IAppli} in the given framework context {@link CoreFrameworkCtx}.
    * <br>
    * Its the first time the Appli gets a reference to the Host.
    * <br>
    * Creates the appli context {@link AppCtx} and the {@link AppliAbstract}
    * 
    * The host has been initialized along with
    * 
    * <li> {@link CoreDrawCtx}
    * <li> {@link CoreUiCtx}
    * <li> {@link CoreIOCtx}
    * <li> {@link CoreDataCtx}
    * <li> {@link CoreFrameworkCtx}
    * 
    * @param cfc
    * @return
    */
   public IAppli createAppOnFramework(CoreFrameworkCtx cfc);

   /**
    * 
    * @return
    */
   public UCtx getUC();

}
