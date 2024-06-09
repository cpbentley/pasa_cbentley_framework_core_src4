package pasa.cbentley.framework.core.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.byteobjects.src4.stator.ITechStatorBO;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.byteobjects.src4.stator.StatorWriterBO;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.stator.IStatorOwner;
import pasa.cbentley.core.src4.stator.ITechStator;
import pasa.cbentley.core.src4.stator.Stator;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.core.src4.app.AppliAbstract;
import pasa.cbentley.framework.core.src4.app.IAppli;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.ctx.ObjectCFC;
import pasa.cbentley.framework.core.src4.interfaces.ITechHostCore;
import pasa.cbentley.framework.coredata.src4.stator.StatorCoreData;
import pasa.cbentley.framework.coredata.src4.stator.StatorReaderCoreData;
import pasa.cbentley.framework.coredata.src4.stator.StatorWriterCoreData;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.IBOTypesCoreUI;
import pasa.cbentley.framework.coreui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.coreui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.IBOFramePos;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CoreAppView extends ObjectCFC implements IStatorOwner {

   public CoreAppView(CoreFrameworkCtx cfc) {
      super(cfc);
   }

   public void stateOwnerRead(Stator stator) {
      StatorReaderBO state = (StatorReaderBO) stator.getReader(ITechStatorBO.TYPE_1_VIEW);

      if (state == null) {
         return;
      }

      //save stuff here when u add some
      stateOwnerCanvasHostsReading(state);

      stateReadFromSub(state);
   }

   private void stateOwnerCanvasHostsReading(StatorReaderBO statorReader) {
      //is the ui state driving the model? or is the model state driving the ui state ?
      //in single screen, ui state/model state always match
      //apc.getCFC().stateReadAppUi((StatorReaderBO) state);
      //
      //once canvases have been raised from their graves
      IAppli appli = cfc.getCoordinator().getAppli();

      try {
         //read the different available
         int numCanvases = statorReader.readInt();

         //#debug
         toDLog().pInit("Reading state for " + numCanvases + " canvas", null, CoreAppView.class, "stateOwnerCanvasHostsReading", LVL_05_FINE, true);

         for (int i = 0; i < numCanvases; i++) {

            //int canvasID = statorReader.readInt();
            //ByteObject techCanvasHost = statorReader.readByteObject();
            //            //#debug
            //            techCanvasHost.checkType(IBOTypesCoreUI.TYPE_5_TECH_CANVAS_HOST);

            statorReader.checkInt(123456);
            CanvasAppliAbstract canvasAppli = (CanvasAppliAbstract) statorReader.readObject();

            //#debug
            toDLog().pInit("UnWrapped", canvasAppli, CoreAppView.class, "stateOwnerCanvasHostsReading@82", LVL_05_FINE, true);
            //statorReader.readerToStatorable(canvasAppli);

         }
      } catch (Exception e) {
         //#debug
         toDLog().pEx("", this, CoreAppView.class, "stateOwnerCanvasHostsReading", e);
         e.printStackTrace();
         statorReader.setFlag(ITechStatorBO.FLAG_1_FAILED, true);
      }
      //check if we have at least one canvas
      if (cfc.getCUC().getCanvasRootHost() == null) {
         statorReader.setFlag(ITechStatorBO.FLAG_1_FAILED, true);
      }

   }

   private void stateOwnerCanvasHostsWriting(StatorWriterBO statorWriter) {

      try {
         CanvasHostAbstract[] canvases = cfc.getCUC().getCanvases();
         int numCanvases = canvases.length;
         //#debug
         toDLog().pInit("Writing state for " + numCanvases + " canvas", null, CoreAppView.class, "stateOwnerCanvasHostsReading", LVL_05_FINE, true);

         //Now write our canvas in the main StatorWriter
         statorWriter.getWriter().writeInt(numCanvases);
         for (int i = 0; i < numCanvases; i++) {
            CanvasHostAbstract ch = canvases[i];

            //            int id = ch.getCanvasID();
            //            statorWriter.getWriter().writeInt(id);
            //            ByteObject boCanvas = ch.getBOCanvasHost();
            //            boCanvas.checkType(IBOTypesCoreUI.TYPE_5_TECH_CANVAS_HOST);
            //            statorWriter.writeByteObject(boCanvas);

            statorWriter.getWriter().writeInt(123456);
            ICanvasAppli canvasAppli = ch.getCanvasAppli();
            statorWriter.writerToStatorable(canvasAppli);
         }
      } catch (Exception e) {
         e.printStackTrace();
         statorWriter.setFlag(ITechStatorBO.FLAG_1_FAILED, true);
      }

   }

   public void stateOwnerWrite(Stator stator) {

      StatorWriterBO w = (StatorWriterBO) stator.getWriter(ITechStatorBO.TYPE_1_VIEW);

      //save stuff here when u add some
      stateOwnerCanvasHostsWriting(w);

      stateWriteToSub(w);
   }

   protected void stateReadFromSub(StatorReader state) {

   }

   protected void stateWriteToSub(StatorWriter state) {

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CoreAppView.class, 100);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoreAppView.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
