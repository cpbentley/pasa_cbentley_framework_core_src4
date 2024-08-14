package pasa.cbentley.framework.core.framework.src4.interfaces;

import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.framework.core.ui.src4.event.GestureArea;

public interface ITechDataHost extends ITech {

   public static final int DATA_ID_00                            = 0;

   /**
    * ms time out before long event pointer
    */
   public static final int DATA_ID_01_POINTER_LONG_TIMEOUT       = 1;

   /**
    * pixels difference for a double pointer
    */
   public static final int DATA_ID_02_POINTER_NUPLE_SLOP         = 2;

   /**
    * ms time out for a double click
    */
   public static final int DATA_ID_03_POINTER_NUPLE_TIMEOUT      = 3;

   public static final int DATA_ID_04_POINTER_DRAG_SLOP          = 4;

   public static final int DATA_ID_06_POINTER_FAST_TYPE_TIMEOUT  = 6;

   /**
    * Usual maximum number of pointers used. Desktop is 1.
    * Android will be 3.
    */
   public static final int DATA_ID_07_NUM_START_POINTERS         = 7;

   public static final int DATA_ID_09_SLIDE_MIN_AMPLITUDE        = 9;

   public static final int DATA_ID_10_SIMULTANEOUS_TIMEOUT       = 10;

   public static final int DATA_ID_11_FLING_SPEED_MAX            = 11;

   public static final int DATA_ID_12_FLING_SPEED_MIN            = 12;

   public static final int DATA_ID_14_ALLER_RETOUR_SLOP          = 14;

   public static final int DATA_ID_15_ALLER_RETOUR_MIN_AMPLITUDE = 15;

   public static final int DATA_ID_17_NUMBER_OF_SCREENS          = 17;

   /**
    * <li>{@link ITechCanvas#SCREEN_0_TOP_NORMAL}
    * <li>{@link ITechCanvas#SCREEN_1_BOT_UPSIDEDOWN}
    * <li>{@link ITechCanvas#SCREEN_2_LEFT_ROTATED} 
    * <li>{@link ITechCanvas#SCREEN_3_RIGHT_ROTATED}
    * 
    */
   public static final int DATA_ID_18_SCREEN_ORIENTATION         = 18;

   /**
    * Tells whether the host can deal with long paint times and drag events 
    */
   public static final int DATA_ID_19_DRAG_CONTROLLED            = 20;

   public static final int DATA_ID_20_DPI                        = 20;

   public static final int DATA_ID_23_KEYBOARD_TYPE              = 23;

   public static final int DATA_ID_24_KEY_REPEAT_TIMEOUT         = 24;

   public static final int DATA_ID_25_KEY_REPEAT_DELAY           = 25;

   public static final int DATA_ID_26_KEY_FAST_TYPE_TIMEOUT      = 26;

   public static final int DATA_ID_27_KEY_NUPLE_TIMEOUT          = 27;

   /**
    * returns an array of {@link GestureArea} defining the available screens.
    * <li> x = -
    */
   public static final int DATA_ID_OBJ_01_SCREENS                = 1;

   /**
    * Returns a ByteObject of type literal that defines.
    * <br>
    * Return null if irrevelant because only 1 screen is assumed (J2ME).
    */
   public static final int DATA_ID_OBJ_02_SCREENCONFIG           = 2;

   public static final int DATA_ID_STR_01_MANUFACTURER           = 1;

   public static final int DATA_ID_STR_02_IMEI                   = 2;

   public static final int DATA_ID_STR_03_DEVICE_MODEL           = 3;

   public static final int DATA_ID_STR_04_PLATFORM               = 4;

   public static final int DATA_ID_STR_05_HOSTNAME               = 5;

   public static final int DATA_ID_STR_06_ENCODING               = 6;

   public static final int DATA_ID_STR_07_FILE_SEPARATOR         = 7;

}
