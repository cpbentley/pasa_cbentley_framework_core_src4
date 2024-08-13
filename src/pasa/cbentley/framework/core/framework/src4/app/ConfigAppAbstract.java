package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.byteobjects.src4.ctx.ConfigAbstractBO;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechGraphics;

public abstract class ConfigAppAbstract extends ConfigAbstractBO implements IConfigApp {

   protected int          aliasMode;

   protected String       appIcon;

   protected boolean      isAppStatorRead  = true;

   protected boolean      isAppStatorWrite = true;

   protected boolean      isVolatile;

   protected final String name;

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
      aliasMode = ITechGraphics.MODSET_APP_ALIAS_0_BEST;
   }

   protected void cloneMeSet(ConfigAppAbstract config) {
      config.isVolatile = this.isVolatile;
   }

   public int getAliasMode() {
      return aliasMode;
   }

   public String getAppIcon() {
      return appIcon;
   }

   public String getAppName() {
      return name;
   }

   public String getIconPathDefault() {
      return appIcon;
   }

   public String getProfileNameDef() {
      return "default";
   }

   public boolean isAppStatorRead() {
      return isAppStatorRead;
   }

   public boolean isAppStatorWrite() {
      return isAppStatorWrite;
   }

   public boolean isVolatileData() {
      return isVolatile;
   }

   public void setAppIcon(String path) {
      this.appIcon = path;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigAppAbstract.class, 60);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.appendVarWithNewLine("isAppStatorRead", isAppStatorRead);
      dc.appendVarWithSpace("isAppStatorWrite", isAppStatorWrite);
      dc.appendVarWithSpace("isVolatile", isVolatile);

      dc.appendVarWithNewLine("aliasMode", aliasMode);

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigAppAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("name", name);

   }

   //#enddebug

}
