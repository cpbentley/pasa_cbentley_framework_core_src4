package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.byteobjects.src4.core.interfaces.IBOCtxSettings;
import pasa.cbentley.framework.core.src4.interfaces.IBOHost;

/**
 * Settings of an {@link IAppli}.
 * <br>
 * <br>
 * {@link IBOCtxSettingsAppli} business object is profile dependant
 * <br>
 * <br>
 * Each profile has one or several application settings.
 * <br>
 * 
 * When a user changes applications settings that may impact the Host choices,
 * A new event Property change is fired.
 * <br>
 * <br>
 * <li>{@link IEventsBentleyFw#PID_01_DEVICE_05_UPDATE}
 * 
 * The host registered on the Device ProducerID .
 * <br>
 * <br>
 * 
 * @author Charles-Philip Bentley
 *
 */
public interface IBOCtxSettingsAppli extends IBOCtxSettings {

   public static final int CTX_APP_ALIAS_0_BEST                 = 0;

   public static final int CTX_APP_ALIAS_1_ON                   = 1;

   public static final int CTX_APP_ALIAS_2_OFF                  = 2;

   public static final int CTX_APP_BASIC_SIZE                   = CTX_BASIC_SIZE + 69;

   /**
    * 
    */
   public static final int CTX_APP_TYPE_SUB                     = 1;

   public static final int CTX_APP_FLAG_1_HEADLESS              = 1 << 0;

   /**
    * Flag to enable disable Drag And Drop capabilities
    */
   public static final int CTX_APP_FLAGX_2_DRAG_DROP            = 1 << 1;

   /**
    * When appli wants 
    */
   public static final int CTX_APP_FLAGX_8_ONE_THUMB            = 1 << 7;

   public static final int CTX_APP_OFFSET_01_FLAG               = CTX_BASIC_SIZE;

   public static final int CTX_APP_OFFSET_02_FLAGX              = CTX_BASIC_SIZE + 1;

   public static final int CTX_APP_OFFSET_03_FLAGY              = CTX_BASIC_SIZE + 2;

   /**
    * Number of times the application is started.
    */
   public static final int CTX_APP_OFFSET_04_STARTS2            = CTX_BASIC_SIZE + 3;

   /**
    * Total Running in seconds. For all user profiles
    */
   public static final int CTX_APP_OFFSET_05_RUNNING_TIME4      = CTX_BASIC_SIZE + 5;

   /**
    * Total Stand by in seconds
    */
   public static final int CTX_APP_OFFSET_06_STAND_BY_TIME4     = CTX_BASIC_SIZE + 9;

   public static final int CTX_APP_OFFSET_07_PAUSES2            = CTX_BASIC_SIZE + 13;

   /**
    * User Request for Mode for anti aliasing in graphics
    * <li> {@link IBOCtxSettingsAppli#CTX_APP_ALIAS_0_BEST} is best decision
    * <li> {@link IBOCtxSettingsAppli#CTX_APP_ALIAS_1_ON} is on
    * <li> {@link IBOCtxSettingsAppli#CTX_APP_ALIAS_2_OFF} is off
    * <br>
    * Appli settings for default anti alias. When request was successful ....
    * see {@link IBOHost#HOST_FLAGX_1_ANTI_ALIAS}
    * and
    * {@link IBOHost#HOST_FLAGX_2_ANTI_ALIAS_USER}
    */
   public static final int CTX_APP_OFFSET_08_ANTI_ALIAS1        = CTX_BASIC_SIZE + 15;

   /**
    * Language ID to be used for localized strings.
    * <br>
    * <br>
    * How does that relates to applications dealing with languages? No relation.
    */
   public static final int CTX_APP_OFFSET_09_VARCHAR_LANSUFFIX2 = CTX_BASIC_SIZE + 16;

   /**
    * Key in user settings for sound playing
    * <br>
    * 0 for both, 3 for none, 2 for music only, 2 for sound only
    */
   public final static int CTX_APP_OFFSET_10_SOUND1             = CTX_BASIC_SIZE + 18;

   /**
    * Full VarChar name of 25 characters.
    */
   public static final int CTX_APP_OFFSET_11_NAME50             = CTX_BASIC_SIZE + 19;

   /**
    * The version of this app settings. Used to run installer and updaters
    */
   public static final int CTX_APP_OFFSET_12_VARCHAR_VERSION10  = CTX_BASIC_SIZE + 21;

   public final static int CTX_APP_SOUND_0_NONE                 = 0;

   public final static int CTX_APP_SOUND_1_SOUND_AND_MUSIC      = 1;

   public final static int CTX_APP_SOUND_2_SOUND_ONLY           = 2;

   public final static int CTX_APP_SOUND_3_MUSIC_ONLY           = 3;

}
