package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.interfaces.StatorReaderBO;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.framework.coreui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;

public class AppStatorFactory implements IStatorFactory {

   protected final AppCtx apc;

   public AppStatorFactory(AppCtx apc) {
      this.apc = apc;
   }

   public Object createObject(StatorReader state, Class type) {
      StatorReaderBO reader = (StatorReaderBO) state;
      int objectID = reader.getDataReader().readInt();
      //is object already in the pool?
      Object o = state.getObject(objectID);
      if (o != null) {
         return o;
      } else {
         int canvasID = reader.getDataReader().readInt();
         ByteObject techCanvasHost = reader.readByteObject();
         CanvasAppliAbstract canvasAppli = (CanvasAppliAbstract) apc.getAppli().getCanvas(canvasID, techCanvasHost);
         state.setObject(o, objectID);
         return canvasAppli;
      }

   }

   public boolean isTypeSupported(Class cl) {
      if (cl == CanvasAppliAbstract.class || cl == ICanvasAppli.class) {
         return true;
      }
      return false;
   }

   public Object[] createArray(Class cl, int size) {
      if (cl == CanvasAppliAbstract.class) {
         return new CanvasAppliAbstract[size];
      }
      if (cl == ICanvasAppli.class) {
         return new ICanvasAppli[size];
      }
      return null;
   }

}
