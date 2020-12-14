package pasa.cbentley.framework.core.src4.interfaces;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.event.ILifeListener;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public interface IAPIService extends IStringable, ILifeListener {

   /**
    * 
    * @param context
    * @throws IllegalArgumentException if context type is not expected
    */
   public void setCtx(ACtx context) throws IllegalArgumentException;

   /**
    * 
    * @param id ID for the service
    * @return
    */
   public boolean startService(int id);

   public boolean isServiceRunning(int id);

   public boolean stopService(int id);


}
