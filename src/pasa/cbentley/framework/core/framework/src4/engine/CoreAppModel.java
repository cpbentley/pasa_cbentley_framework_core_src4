package pasa.cbentley.framework.core.framework.src4.engine;

import pasa.cbentley.byteobjects.src4.stator.ITechStatorBO;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.stator.IStatorOwner;
import pasa.cbentley.core.src4.stator.Stator;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.ctx.ObjectCFC;

/**
 * To be subclassed by Application implementation to get hooked on state saves
 * @author Charles Bentley
 *
 */
public class CoreAppModel extends ObjectCFC implements IStatorOwner {

   public CoreAppModel(CoreFrameworkCtx cfc) {
      super(cfc);
   }

   public void stateOwnerRead(Stator stator) {
      StatorReader state = (StatorReader) stator.getReader(ITechStatorBO.TYPE_2_MODEL);

      //save stuff here when u add some
      
      stateReadFromSub(state);
   }

   public void stateOwnerWrite(Stator stator) {
      
      StatorWriter w = (StatorWriter) stator.getWriter(ITechStatorBO.TYPE_2_MODEL);
      
      //save stuff here when u add some
      
      
      stateWriteToSub(w);
   }


   protected void stateReadFromSub(StatorReader state) {

   }



   protected void stateWriteToSub(StatorWriter state) {
      
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CoreAppModel.class, 100);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoreAppModel.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
