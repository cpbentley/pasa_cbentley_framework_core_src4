package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.core.src4.ctx.IConfig;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.framework.core.src4.ctx.IConfigCoreFramework;
import pasa.cbentley.framework.coredata.src4.ctx.IConfigCoreData;
import pasa.cbentley.framework.coredraw.src4.ctx.IConfigCoreDraw;
import pasa.cbentley.framework.coreio.src4.ctx.IConfigCoreIO;
import pasa.cbentley.framework.coreui.src4.ctx.IConfigCoreUI;

/**
 * Config manifest
 * 
 * @author Charles Bentley
 *
 */
public interface IConfigApp extends IConfigBO {

   public IConfigApp cloneMe(UCtx uc, String name);

   public String getAppIcon();

   public String getAppName();

   /**
    * {@link ITechCtxSettingsAppli#CTX_APP_FLAGX_2_DRAG_DROP}
    * @return
    */
   public boolean isAppDragDropEnabled();

}
