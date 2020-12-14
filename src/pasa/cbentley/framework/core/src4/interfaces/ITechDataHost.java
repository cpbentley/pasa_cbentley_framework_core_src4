package pasa.cbentley.framework.core.src4.interfaces;

public interface ITechDataHost {

   int DATA_ID_00                            = 0;

   /**
    * ms time out before long event pointer
    */
   int DATA_ID_01_POINTER_LONG_TIMEOUT       = 1;

   /**
    * pixels difference for a double pointer
    */
   int DATA_ID_02_POINTER_NUPLE_SLOP         = 2;

   /**
    * ms time out for a double click
    */
   int DATA_ID_03_POINTER_NUPLE_TIMEOUT      = 3;

   int DATA_ID_04_POINTER_DRAG_SLOP          = 4;

   int DATA_ID_06_POINTER_FAST_TYPE_TIMEOUT  = 6;

   /**
    * Usual maximum number of pointers used. Desktop is 1.
    * Android will be 3.
    */
   int DATA_ID_07_NUM_START_POINTERS         = 7;

   int DATA_ID_09_SLIDE_MIN_AMPLITUDE        = 9;

   int DATA_ID_10_SIMULTANEOUS_TIMEOUT       = 10;

   int DATA_ID_11_FLING_SPEED_MAX            = 11;

   int DATA_ID_12_FLING_SPEED_MIN            = 12;

   int DATA_ID_14_ALLER_RETOUR_SLOP          = 14;

   int DATA_ID_15_ALLER_RETOUR_MIN_AMPLITUDE = 15;

   int DATA_ID_17_NUMBER_OF_SCREENS          = 17;

   /**
    * <li>{@link ITechCanvas#SCREEN_0_TOP_NORMAL}
    * <li>{@link ITechCanvas#SCREEN_1_BOT_UPSIDEDOWN}
    * <li>{@link ITechCanvas#SCREEN_2_LEFT_ROTATED} 
    * <li>{@link ITechCanvas#SCREEN_3_RIGHT_ROTATED}
    * 
    */
   int DATA_ID_18_SCREEN_ORIENTATION         = 18;

   /**
    * Tells whether the host can deal with long paint times and drag events 
    */
   int DATA_ID_19_DRAG_CONTROLLED            = 20;

   int DATA_ID_20_DPI                        = 20;

   int DATA_ID_23_KEYBOARD_TYPE              = 23;

   int DATA_ID_24_KEY_REPEAT_TIMEOUT         = 24;

   int DATA_ID_25_KEY_REPEAT_DELAY           = 25;

   int DATA_ID_26_KEY_FAST_TYPE_TIMEOUT      = 26;

   int DATA_ID_27_KEY_NUPLE_TIMEOUT          = 27;

   /**
    * returns an array of {@link GestureArea} defining the available screens.
    * <li> x = -
    */
   int DATA_ID_OBJ_01_SCREENS                = 1;

   /**
    * Returns a ByteObject of type literal that defines.
    * <br>
    * Return null if irrevelant because only 1 screen is assumed (J2ME).
    */
   int DATA_ID_OBJ_02_SCREENCONFIG           = 2;

   int DATA_ID_STR_01_MANUFACTURER           = 1;

   int DATA_ID_STR_02_IMEI                   = 2;

   int DATA_ID_STR_03_DEVICE_MODEL           = 3;

   int DATA_ID_STR_04_PLATFORM               = 4;

   int DATA_ID_STR_05_HOSTNAME               = 5;

   int DATA_ID_STR_06_ENCODING               = 6;

   int DATA_ID_STR_07_FILE_SEPARATOR         = 7;

}
