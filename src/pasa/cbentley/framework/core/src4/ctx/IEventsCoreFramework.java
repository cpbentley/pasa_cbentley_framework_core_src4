package pasa.cbentley.framework.core.src4.ctx;

import pasa.cbentley.core.src4.interfaces.IEvents;

public interface IEventsCoreFramework extends IEvents {

   int                     BASE_EVENTS            = 2;

   int                     PID_0_ANY              = 0;

   /**
    * This event is generated when the Host detects a file drag and dropped.
    * <br>
    * If the Appli or other objects are listening, they will have a chance
    * to process the file.
    * <br>
    * <br>
    * Listener may query position of mouse
    */
   public static final int EVENT_ID_04_DRAG_DROP  = 4;
   public static final int EVENT_ID_04_FILE_DRAG_DROP  = 4;
   public static final int EVENT_ID_05_FILE_CHOSEN  = 5;
   public static final int PID_04_FILE  = 4;

   public static final int EVENT_ID_06_APP_PAUSED = 6;

   public static final int PID_01_APP_LIFECYCLE   = 1;
}
