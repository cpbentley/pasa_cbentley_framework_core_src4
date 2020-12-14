package pasa.cbentley.framework.core.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.ctx.IBOTypesCoreFramework;
import pasa.cbentley.framework.core.src4.interfaces.IHost;
import pasa.cbentley.framework.core.src4.interfaces.ITechDataHost;
import pasa.cbentley.framework.core.src4.interfaces.ITechHost;
import pasa.cbentley.framework.coreui.src4.event.GestureArea;

public abstract class CoreHostAbstract implements IHost, ITechHost, IStringable {

   /**
    * {@link ITechHost}.
    * Fields filled by the specific framework driver
    */
   protected ByteObject             techHost;

   protected final CoreFrameworkCtx cfc;

   public CoreHostAbstract(CoreFrameworkCtx cfc) {
      this.cfc = cfc;
      techHost = cfc.getBOC().getByteObjectFactory().createByteObject(IBOTypesCoreFramework.FTYPE_2_HOST, ITechHost.HOST_BASIC_SIZE);
   }

   public ByteObject getTechHost() {
      return techHost;
   }

   public CoreFrameworkCtx getCFC() {
      return cfc;
   }

   /**
    * Returns actual live screen config creates a new object.
    * <br>
    * Type is {@link ITypesCore#TYPE_007_LIT_ARRAY_INT}.
    * The first value being number of screens.
    * and then w,h couples for each screens.
    * <br>
    * Thus. This config object does not represent screen positioning relative to each other.
    * @return
    */
   public ByteObject getScreenConfigLive() {
      GestureArea[] val = (GestureArea[]) this.getHostObject(ITechDataHost.DATA_ID_OBJ_01_SCREENS);

      int[] ar = new int[1 + (val.length * 2)];
      ar[0] = val.length;
      int index = 1;
      for (int i = 0; i < val.length; i++) {
         ar[index++] = val[i].w;
         ar[index++] = val[i].h;
      }
      ByteObject bo = cfc.getBOC().getLitteralIntFactory().getIntArrayBO(ar);
      return bo;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "AbstractHost");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AbstractHost");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return cfc.getUCtx();
   }

   //#enddebug

}
