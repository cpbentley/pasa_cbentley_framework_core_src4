package pasa.cbentley.framework.core.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.ctx.IBOTypesCoreFramework;
import pasa.cbentley.framework.core.src4.ctx.ObjectCFC;
import pasa.cbentley.framework.core.src4.interfaces.IHostCore;
import pasa.cbentley.framework.core.src4.interfaces.ITechDataHost;
import pasa.cbentley.framework.core.src4.interfaces.ITechHostCore;
import pasa.cbentley.framework.core.src4.interfaces.IBOHost;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.event.GestureArea;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostUI;

public abstract class CoreHostAbstract extends ObjectCFC implements IHostCore, IBOHost, ITechHostCore, IStringable {

   /**
    * {@link IBOHost}.
    * Fields filled by the specific framework driver
    */
   protected ByteObject techHost;

   public CoreHostAbstract(CoreFrameworkCtx cfc) {
      super(cfc);
      techHost = cfc.getBOC().getByteObjectFactory().createByteObject(IBOTypesCoreFramework.FTYPE_2_HOST, IBOHost.HOST_BASIC_SIZE);
   }

   public ByteObject getTechHost() {
      return techHost;
   }

   public CoreUiCtx getCUC() {
      return cfc.getCUC();
   }

   public IHostUI getHostUI() {
      return getCUC().getHostUI();
   }

   public ByteObject getBOHostUI() {
      return getCUC().getHostUI().getBOHostUI();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CoreHostAbstract.class, 70);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoreHostAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
