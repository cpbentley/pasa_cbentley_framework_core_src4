package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.core.src4.ctx.IConfig;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.framework.core.data.src4.ctx.IConfigCoreData;
import pasa.cbentley.framework.core.framework.src4.ctx.IConfigCoreFramework;
import pasa.cbentley.framework.core.io.src4.ctx.IConfigCoreIO;
import pasa.cbentley.framework.core.ui.src4.ctx.IConfigCoreUi;
import pasa.cbentley.framework.coredraw.src4.ctx.IConfigCoreDraw;

/**
 * Configuration for the {@link AppCtx} module.
 * 
 * @author Charles Bentley
 *
 */
public interface IConfigApp extends IConfigBO {

   /**
    * Clone the configuration with a different name
    * @param uc
    * @param name
    * @return
    */
   public IConfigApp cloneMe(UCtx uc, String name);

   /**
    * The Application icon is a configuration item.
    * 
    * One reason is that when the application is cloned into a second window, its icon can be configured
    * so that the host frame displays a different icon.
    * 
    * @return
    */
   public String getAppIcon();

   /**
    * The Application name is a configuration item.
    * 
    * One reason is that when the application is cloned into a second window, its name must be configured
    * so that the host frame displays a different name.
    * 
    * @return
    */
   public String getAppName();

   /**
    * {@link IBOCtxSettingsAppli#CTX_APP_FLAGX_2_DRAG_DROP}
    * @return
    */
   public boolean isAppDragDropEnabled();

   /**
    * 
    * @return
    */
   public String getProfileNameDef();
   
   /**
    * Sets to false to stop app from writing its state when exiting 
    * @return
    */
   public boolean isAppStatorWrite();

   /**
    * Sets to false to stop state reading 
    * @return
    */
   public boolean isAppStatorRead();

}
