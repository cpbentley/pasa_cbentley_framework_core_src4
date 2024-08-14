package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.data.src4.ctx.IConfigCoreData;
import pasa.cbentley.framework.core.framework.src4.ctx.IConfigCoreFramework;
import pasa.cbentley.framework.core.io.src4.ctx.IConfigCoreIO;
import pasa.cbentley.framework.core.ui.src4.ctx.IConfigCoreUi;
import pasa.cbentley.framework.coredraw.src4.ctx.IConfigCoreDraw;

/**
 * When we want host configuration to be part of the App configuration
 * 
 * <li> {@link IConfigCoreUi}
 * <li> {@link IConfigCoreDraw}
 * <li> {@link IConfigCoreFramework}
 * <li> {@link IConfigCoreData}
 * <li> {@link IConfigCoreIO}
 * 
 * @author Charles Bentley
 *
 */
public abstract class ConfigAppFlatAbstract extends ConfigAppAbstract implements IStringable, IConfigCoreUi, IConfigCoreIO, IConfigCoreFramework, IConfigCoreData, IConfigCoreDraw {


   public ConfigAppFlatAbstract(UCtx uc, String name) {
      super(uc, name);
   }

   public IConfigCoreUi getConfigUI() {
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


   public boolean isFullscreen() {
      // TODO Auto-generated method stub
      return false;
   }



   public IConfigApp cloneMe(UCtx uc, String name) {
      // TODO Auto-generated method stub
      return null;
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigAppFlatAbstract.class, 80);
      toStringPrivate(dc);
      super.toString(dc.sup());
      
      dc.appendVarWithNewLine("IConfigCoreDraw", "this");
      dc.appendVarWithNewLine("IConfigCoreUi", "this");
      dc.appendVarWithNewLine("IConfigCoreFramework", "this");
      dc.appendVarWithNewLine("IConfigCoreIO", "this");
      dc.appendVarWithNewLine("IConfigCoreData", "this");
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigAppFlatAbstract.class, 80);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      
   }
   //#enddebug
   

}
