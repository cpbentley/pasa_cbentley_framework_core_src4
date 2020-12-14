package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.src4.ctx.IConfigCoreFramework;
import pasa.cbentley.framework.coredata.src4.ctx.IConfigCoreData;
import pasa.cbentley.framework.coredraw.src4.ctx.IConfigCoreDraw;
import pasa.cbentley.framework.coreio.src4.ctx.IConfigCoreIO;
import pasa.cbentley.framework.coreui.src4.ctx.IConfigCoreUI;

public abstract class ConfigAppFlatAbstract extends ConfigAppAbstract implements IStringable, IConfigCoreUI, IConfigCoreIO, IConfigCoreFramework, IConfigCoreData, IConfigCoreDraw {

   protected String appIcon;

   public ConfigAppFlatAbstract(UCtx uc, String name) {
      super(uc, name);
   }

   public String getAppIcon() {
      return appIcon;
   }

   public String getIconPathDefault() {
      return appIcon;
   }

   public void setAppIcon(String path) {
      this.appIcon = path;
   }

   public IConfigCoreUI getConfigUI() {
      return this;
   }

   public IConfigCoreData getConfigData() {
      return this;
   }

   public IConfigCoreIO getConfigIO() {
      return this;
   }

   public IConfigCoreFramework getConfigFramework() {
      return this;
   }

   public IConfigCoreDraw getConfigDraw() {
      return this;
   }

   public int[] getFontPoints() {
      return null;
   }

   public boolean isFullscreen() {
      // TODO Auto-generated method stub
      return false;
   }

   public boolean isPlayingMusic() {
      // TODO Auto-generated method stub
      return false;
   }

   public boolean isPlayingFx() {
      // TODO Auto-generated method stub
      return false;
   }

   public IConfigApp cloneMe(UCtx uc, String name) {
      // TODO Auto-generated method stub
      return null;
   }


}
