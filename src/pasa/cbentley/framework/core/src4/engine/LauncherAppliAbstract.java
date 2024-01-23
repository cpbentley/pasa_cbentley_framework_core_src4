package pasa.cbentley.framework.core.src4.engine;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherAppli;

public abstract class LauncherAppliAbstract extends ObjectU implements ILauncherAppli {


   public LauncherAppliAbstract(UCtx uc) {
      super(uc);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, LauncherAppliAbstract.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LauncherAppliAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   


}
