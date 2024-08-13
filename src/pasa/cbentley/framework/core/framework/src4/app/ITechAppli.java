package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.core.src4.interfaces.ITech;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface ITechAppli extends ITech {
   
   public static final int STATE_0_CREATED   = 0;

   public static final int STATE_1_LOADED    = 1;

   public static final int STATE_2_STARTED   = 2;

   public static final int STATE_3_PAUSED    = 3;

   public static final int STATE_4_DESTROYED = 4;
   
}
