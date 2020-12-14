package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.byteobjects.src4.ctx.ConfigAbstractBO;
import pasa.cbentley.core.src4.ctx.ConfigAbstract;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechDrawer;

public abstract class ConfigAppAbstract extends ConfigAbstractBO implements IConfigApp {

   protected final String name;

   protected boolean      isVolatile;

   protected int          aliasMode;

   /**
    * ID that will be used to identify data stores and other unique
    * 
    * The same app can be used with different ids.. apps would reside in different namespaces even though
    * they offer the exact same functionality
    * @param name
    */
   public ConfigAppAbstract(UCtx uc, String name) {
      super(uc);
      this.name = name;
      isVolatile = false; //by default
      aliasMode = ITechDrawer.MODSET_APP_ALIAS_0_BEST;
   }

   public String getAppName() {
      return name;
   }

   public int getAliasMode() {
      return aliasMode;
   }

   public boolean isVolatileData() {
      return isVolatile;
   }

   protected void cloneMeSet(ConfigAppAbstract config) {
      config.isVolatile = this.isVolatile;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "ConfigAppAbstract");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ConfigAppAbstract");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}