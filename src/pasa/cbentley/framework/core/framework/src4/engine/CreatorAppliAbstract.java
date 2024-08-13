package pasa.cbentley.framework.core.framework.src4.engine;

import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.framework.src4.app.IConfigApp;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.interfaces.ICreatorAppli;

/**
 * Simple encapulsates an {@link IConfigApp} and the method for creating the Appli
 * 
 * <li> {@link ICreatorAppli#createAppOnFramework(CoreFrameworkCtx)}
 * @author Charles Bentley
 *
 */
public abstract class CreatorAppliAbstract extends ObjectU implements ICreatorAppli {

   protected final IConfigApp configApp;

   public CreatorAppliAbstract(UCtx uc) {
      this(uc,null);
   }
   
   public CreatorAppliAbstract(UCtx uc, IConfigApp configApp) {
      super(uc);
      this.configApp = configApp;
   }

   public IConfigApp getConfigApp() {
      return configApp;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CreatorAppliAbstract.class, "@line5");
      toStringPrivate(dc);
      dc.nlLvl(configApp, "configApp");
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CreatorAppliAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
