package pasa.cbentley.framework.core.framework.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBOC;
import pasa.cbentley.core.src4.ctx.ConfigAbstract;
import pasa.cbentley.core.src4.ctx.IConfigU;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.utils.interfaces.IColors;
import pasa.cbentley.framework.core.data.src4.ctx.IConfigCoreData;
import pasa.cbentley.framework.core.framework.src4.ctx.IConfigCoreFramework;
import pasa.cbentley.framework.core.io.src4.ctx.IConfigCoreIO;
import pasa.cbentley.framework.core.ui.src4.ctx.IConfigCoreUi;
import pasa.cbentley.framework.coredraw.src4.ctx.IConfigCoreDraw;

public abstract class ConfigFrameworkFlat extends ConfigAbstract implements IConfigCoreDraw, IConfigCoreUi, IConfigCoreFramework, IConfigCoreIO, IConfigCoreData, IConfigU, IConfigBOC {

   public ConfigFrameworkFlat() {
      super();
   }

   public int getAllerRetourMinAmplitudePixel() {
      return 50;
   }

   public int getColorImageBackgroundDefault() {
      return IColors.FULLY_OPAQUE_WHITE;
   }

   public String getConfigIODirectory() {
      // TODO Auto-generated method stub
      return null;
   }

   public boolean isFullscreen() {
      return true;
   }

   public void postProcessing(ByteObject settings, ABOCtx ctx) {

   }
   

   public IConfigBOC getConfigBOC() {
      return this;
   }

   public IConfigU getConfigU() {
      return this;
   }

   public IConfigCoreIO getConfigIO() {
      return this;
   }

   public IConfigCoreData getConfigData() {
      return this;
   }

   public IConfigCoreFramework getConfigFramework() {
      return this;
   }

   public IConfigCoreUi getConfigUi() {
      return this;
   }

   public IConfigCoreDraw getConfigDraw() {
      return this;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigFrameworkFlat.class, 60);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigFrameworkFlat.class, 60);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
