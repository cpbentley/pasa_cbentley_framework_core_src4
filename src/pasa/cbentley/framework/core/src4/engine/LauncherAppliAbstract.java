package pasa.cbentley.framework.core.src4.engine;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.src4.app.IConfigApp;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherAppli;

/**
 * Simple encapulsates an {@link IConfigApp} and the method for creating the Appli
 * 
 * <li> {@link ILauncherAppli#createAppOnFramework(CoreFrameworkCtx)}
 * @author Charles Bentley
 *
 */
public abstract class LauncherAppliAbstract extends ObjectU implements ILauncherAppli {

   protected final IConfigApp configApp;

   public LauncherAppliAbstract(UCtx uc) {
      this(null,uc);
   }
   
   public LauncherAppliAbstract(IConfigApp configApp, UCtx uc) {
      super(uc);
      this.configApp = configApp;
   }

   public IConfigApp getConfigApp() {
      return configApp;
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
