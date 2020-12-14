package pasa.cbentley.framework.core.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.tech.ITechByteObject;

/**
 * 
 * Allows host settings to be saved to a {@link ByteObject}
 * 
 * @author Charles-Philip
 *
 */
public interface ITechHost extends ITechByteObject {

   public static final int HOST_BASIC_SIZE                       = A_OBJECT_BASIC_SIZE + 30;

   /**
    * Does the framework support drag and drop of files?
    */
   public static final int HOST_FLAG_1_FILEDROP                  = 1;

   public static final int HOST_FLAG_2                           = 1 << 1;

   public static final int HOST_FLAG_3_                          = 1 << 2;

   public static final int HOST_FLAG_4_                          = 1 << 3;

   public static final int HOST_FLAGK_1_LONGPRESS                = 1 << 0;

   public static final int HOST_FLAGK_2_                         = 1 << 1;

   public static final int HOST_FLAGK_3_                         = 1 << 2;

   public static final int HOST_FLAGK_4_                         = 1 << 3;

   public static final int HOST_FLAGK_5_                         = 1 << 4;

   public static final int HOST_FLAGK_6_                         = 1 << 5;

   public static final int HOST_FLAGK_7_                         = 1 << 6;

   public static final int HOST_FLAGK_8_                         = 1 << 7;

   /**
    * Set by host framework as read only.
    * When true, Host is able to handle anti alias
    * <br>
    * J2ME draw ctx does not support.
    */
   public static final int HOST_FLAGX_1_ANTI_ALIAS               = 1 << 0;

   /**
    * Set by the user. Initially set to same as above
    */
   public static final int HOST_FLAGX_2_ANTI_ALIAS_USER          = 1 << 1;

   /**
    * Flag set when Host is capable is generating long press event. The duration is not configurable.
    * 
    */
   public static final int HOST_FLAGY_1_LONGPRESS                = 1 << 0;

   public static final int HOST_FLAGY_2_                         = 1 << 1;

   public static final int HOST_FLAGY_3_                         = 1 << 2;

   public static final int HOST_FLAGY_4_                         = 1 << 3;

   public static final int HOST_FLAGY_5_                         = 1 << 4;

   public static final int HOST_FLAGY_6_                         = 1 << 5;

   public static final int HOST_FLAGY_7_                         = 1 << 6;

   public static final int HOST_FLAGY_8_                         = 1 << 7;

   public static final int HOST_FLAGZ_1_POINTER_EVENTS           = 1;

   public static final int HOST_FLAGZ_2_WHEEL_EVENTS             = 1 << 1;

   public static final int HOST_FLAGZ_3_REPEAT_EVENTS            = 1 << 2;

   public static final int HOST_FLAGZ_4_DRAG_CONTROLLED          = 1 << 3;

   public static final int HOST_FLAGZ_6_OPEN_GL                  = 1 << 5;

   public static final int HOST_OFFSET_01_FLAG                   = A_OBJECT_BASIC_SIZE;

   public static final int HOST_OFFSET_02_FLAGX                  = A_OBJECT_BASIC_SIZE + 1;

   public static final int HOST_OFFSET_03_FLAGZ                  = A_OBJECT_BASIC_SIZE + 2;

   /**
    * Genetics of the Host.
    */
   public static final int HOST_OFFSET_04_FLAGY                  = A_OBJECT_BASIC_SIZE + 3;

   public static final int HOST_OFFSET_05_FLAGK                  = A_OBJECT_BASIC_SIZE + 4;

   /**
    * Config to choosing different canvas types (opengl or ...)
    */
   public static final int HOST_OFFSET_06_CANVAS_TYPE1           = A_OBJECT_BASIC_SIZE + 5;

   public static final int HOST_OFFSET_07_KEYBOARD_TYPE1         = A_OBJECT_BASIC_SIZE + 6;

   public static final int HOST_OFFSET_08_Y_SCREEN_OFFSET2       = A_OBJECT_BASIC_SIZE + 7;

   public static final int HOST_OFFSET_09_DRAG_THRESHOLD2        = A_OBJECT_BASIC_SIZE + 9;

   /**
    * Though Android doesn't use a direct pixel mapping, it uses a handful of quantized Density Independent Pixel values then scales to the actual screen size.
    * 
    *  So the metrics.densityDpi property will be one of the DENSITY_??? constants (120, 160, 213, 240, 320, 480 or 640 dpi)
    */
   public static final int HOST_OFFSET_10_DPI1                   = A_OBJECT_BASIC_SIZE + 11;

   public static final int HOST_OFFSET_11_PINCH_KEY2             = A_OBJECT_BASIC_SIZE + 12;

   /**
    * Maximum number of pointers.
    * Android will maybe allow 5 fingers.
    * PC will be 3 to 4 buttons.
    * This is a safeguard against system errors.
    */
   public static final int HOST_OFFSET_12_MAX_NUM_POINTERS1      = A_OBJECT_BASIC_SIZE + 14;

   public static final int HOST_OFFSET_13_FAST_TYPE_KEY2         = A_OBJECT_BASIC_SIZE + 16;

   public static final int HOST_OFFSET_14_DOUBLE_KEY2            = A_OBJECT_BASIC_SIZE + 18;

   public static final int HOST_OFFSET_15_DOUBLE_POINTER2        = A_OBJECT_BASIC_SIZE + 20;

   public static final int HOST_OFFSET_16_LONG_PRESS_TIMEOUT2    = A_OBJECT_BASIC_SIZE + 22;

   public static final int HOST_OFFSET_17_TOUCH_SLOP2            = A_OBJECT_BASIC_SIZE + 24;

   int                     KB_TYPE_0_PHONE                       = 0;

   int                     KB_TYPE_1_FULL                        = 1;

   int                     KB_TYPE_2_FULL_VIRTUAL                = 2;

}
