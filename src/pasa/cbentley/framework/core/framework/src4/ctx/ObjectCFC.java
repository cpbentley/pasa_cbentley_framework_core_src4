package pasa.cbentley.framework.core.framework.src4.ctx;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.LogParameters;

public class ObjectCFC implements IStringable {

   protected final CoreFrameworkCtx cfc;

   public ObjectCFC(CoreFrameworkCtx cfc) {
      this.cfc = cfc;
   }

   public CoreFrameworkCtx getCFC() {
      return cfc;
   }

   public ICtx getCtxOwner() {
      return cfc;
   }

   public UCtx getUC() {
      return cfc.getUC();
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ObjectCFC.class, 30);
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ObjectCFC.class);
      toStringPrivate(dc);
   }

   public LogParameters toStringGetLine(Class cl, String method, int value) {
      return toStringGetUCtx().toStringGetLine(cl, method, value);
   }

   public String toStringGetLine(int value) {
      return toStringGetUCtx().toStringGetLine(value);
   }

   public UCtx toStringGetUCtx() {
      return cfc.getUC();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
