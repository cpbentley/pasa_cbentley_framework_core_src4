package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coredraw.src4.ctx.IConfigCoreDraw;
import pasa.cbentley.framework.coreui.src4.ctx.IConfigCoreUi;

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

   public IConfigCoreUi getConfigUI() {
      return null;
   }

   public IConfigCoreDraw getConfigDraw() {
      // TODO Auto-generated method stub
      return null;
   }

   public IConfigApp cloneMe(UCtx uc, String name) {
      return new ConfigAppDefault(uc, name);
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigAppDefault.class, 50);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigAppDefault.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   


}
