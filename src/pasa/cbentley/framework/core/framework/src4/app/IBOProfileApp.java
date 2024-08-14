package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;
import pasa.cbentley.framework.core.data.src4.stator.StatorCoreData;

/**
 * Stores user related settings. Shareable between midlets and applications. (this user de
 * 
 * The ID is often used as a key for storing profile based preferences and application states.
 * 
 * Basically the whole {@link StatorCoreData} with its parametrized store name for reading persistant data 
 * 
 * @see IBOCtxSettingsAppli
 * @author Charles Bentley
 *
 */
public interface IBOProfileApp extends IByteObject {

   public static final int PROFILE_BASIC_SIZE            = A_OBJECT_BASIC_SIZE + 44;

   public static final int PROFILE_FLAG_1_               = 1;

   public static final int PROFILE_FLAG_2_               = 2;

   public static final int PROFILE_FLAG_3_               = 4;

   public static final int PROFILE_OFFSET_01_FLAG        = A_OBJECT_BASIC_SIZE;

   public static final int PROFILE_OFFSET_02_NOT_USED2   = A_OBJECT_BASIC_SIZE + 1;

   public static final int PROFILE_OFFSET_03_NOT_USED2   = A_OBJECT_BASIC_SIZE + 3;

   /**
    * 
    */
   public static final int PROFILE_OFFSET_04_TYPE1       = A_OBJECT_BASIC_SIZE + 5;

   public static final int PROFILE_OFFSET_05_NOT_USED4   = A_OBJECT_BASIC_SIZE + 6;

   /**
    * Number of times the profile is activated.
    */
   public static final int PROFILE_OFFSET_06_STARTS2     = A_OBJECT_BASIC_SIZE + 10;

   /**
    * Language ID to be used for localized strings.
    * <br>
    * <br>
    * How does that relates to applications dealing with languages? No relation.
    */
   public static final int PROFILE_OFFSET_07_LANGUAGEID2 = A_OBJECT_BASIC_SIZE + 12;

   /**
    * Name is stored inside. 30 a 15 fixed char number. 2 bytes. VarChar(15)
    * <br>
    * <br>
    * Unless Guest which is a string pointer.
    * <br>
    * <br>
    * 
    */
   public static final int PROFILE_OFFSET_08_NAME15      = A_OBJECT_BASIC_SIZE + 14;

}
