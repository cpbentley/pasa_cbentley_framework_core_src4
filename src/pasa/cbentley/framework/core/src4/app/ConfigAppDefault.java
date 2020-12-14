package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.framework.coredraw.src4.ctx.IConfigCoreDraw;
import pasa.cbentley.framework.coreui.src4.ctx.IConfigCoreUI;

public class ConfigAppDefault extends ConfigAppAbstract {

   public ConfigAppDefault(UCtx uc, String name) {
      super(uc, name);
   }

   public String getAppIcon() {
      // TODO Auto-generated method stub
      return null;
   }

   public void setAppIcon(String path) {
      // TODO Auto-generated method stub

   }

   public void setLogFlags(int flags, boolean b) {
      // TODO Auto-generated method stub

   }

   public boolean isAppDragDropEnabled() {
      return true;
   }

   public IConfigCoreUI getConfigUI() {
      return null;
   }

   public IConfigCoreDraw getConfigDraw() {
      // TODO Auto-generated method stub
      return null;
   }

   public IConfigApp cloneMe(UCtx uc, String name) {
      return new ConfigAppDefault(uc, name);
   }

}
