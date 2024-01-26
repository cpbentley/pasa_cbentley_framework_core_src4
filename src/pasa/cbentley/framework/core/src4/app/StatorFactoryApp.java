package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.framework.coreui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;

/**
 * 
 * @author Charles Bentley
 *
 */
public class StatorFactoryApp implements IStatorFactory, ITechStatorableApp {

   protected final AppCtx apc;

   public StatorFactoryApp(AppCtx apc) {
      this.apc = apc;
   }

   public Object[] createArray(int classID, int size) {
      // TODO Auto-generated method stub
      return null;
   }

   public boolean isSupported(IStatorable statorable) {
      // TODO Auto-generated method stub
      return false;
   }

   public ICtx getCtx() {
      return apc;
   }

   public Object createObject(StatorReader reader, int classID) {
      // TODO Auto-generated method stub
      return null;
   }

}
