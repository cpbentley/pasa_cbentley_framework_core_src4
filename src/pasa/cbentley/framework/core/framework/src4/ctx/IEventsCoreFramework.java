package pasa.cbentley.framework.core.framework.src4.ctx;

import pasa.cbentley.core.src4.interfaces.IEvents;

public interface IEventsCoreFramework extends IEvents {

   public static final int A_SID_FRAMEWORK_EVENT_A    = 21;

   public static final int A_SID_FRAMEWORK_EVENT_Z    = 25;

   public static final int FRAMEWORK_NUM_EVENTS       = 2;

   public static final int PID_00                     = 0;

   public static final int PID_00_ANY                 = A_SID_FRAMEWORK_EVENT_A + PID_00;

   public static final int PID_00_XX                  = 1;

   public static final int PID_01                     = 1;

   public static final int PID_01_LIFE                = A_SID_FRAMEWORK_EVENT_A + PID_01;

   public static final int PID_01_LIFE_00_ANY         = 0;

   /**
    * Event that Request the current Virtual Keyboard to open.
    * <br>
    * Might be Host
    */
   public static final int PID_01_LIFE_01_APP_STARTED = 1;

   public static final int PID_01_LIFE_02_APP_PAUSED  = 2;

   public static final int PID_01_LIFE_03_APP_RESUMED = 3;

   public static final int PID_01_LIFE_04_APP_STOPPED = 4;

   /**
    * Value includes the 0 ANY event
    */
   public static final int PID_01_XX                  = 5; 

   public static final int PID_02                     = 1;

   public static final int PID_02_FILE                = A_SID_FRAMEWORK_EVENT_A + PID_02;

   public static final int PID_02_FILE_00_ANY         = 0;

   /**
    * This event is generated when the Host detects a file drag and dropped.
    * <br>
    * If the Appli or other objects are listening, they will have a chance
    * to process the file.
    * <br>
    * <br>
    * Listener may query position of mouse
    */
   public static final int PID_02_FILE_01_DRAG_DROP   = 1;

   public static final int PID_02_LIFE_02_CHOSEN      = 2;

   public static final int PID_02_XX                  = 3;

}
