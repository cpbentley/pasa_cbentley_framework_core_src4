package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;

/**
 * Stores user related settings. Shareable between midlets and applications. (this user de
 * <br>
 * <br>
 * Saved game states 
 * <br>
 * <br>
 * 
 * Guest profile is on by default
 * <br>
 * <br>
 * <li> saves specific sort settings of TableViews {@link MByteObjectEnum}.
 * 
 * <br>
 * <br>
 * The active profile is fetched by the code when needed from the application control.
 * <br>
 * <br>
 * The MProfile can possibly be shared.
 * <br>
 * <br>
 * 
 * @author Mordan
 *
 */
public interface ITechProfile extends IByteObject {

   public static final int PROFILE_BASIC_SIZE              = A_OBJECT_BASIC_SIZE + 35;

   public static final int PROFILE_FLAG_1_                 = 1;

   public static final int PROFILE_FLAG_2_                 = 2;

   public static final int PROFILE_FLAG_3_                 = 4;

   public static final int PROFILE_OFFSET_01_FLAG          = A_OBJECT_BASIC_SIZE;

   public static final int PROFILE_OFFSET_02_2             = A_OBJECT_BASIC_SIZE + 1;

   public static final int PROFILE_OFFSET_03_2             = A_OBJECT_BASIC_SIZE + 3;

   /**
    * Name is stored inside. ? a 15 fixed char number. 2 bytes. VarChar(15)
    * <br>
    * <br>
    * Unless Guest which is a string pointer.
    * <br>
    * <br>
    * 
    */
   public static final int PROFILE_OFFSET_04_NAME30        = A_OBJECT_BASIC_SIZE + 5;

   /**
    * Language ID to be used for localized strings.
    * <br>
    * <br>
    * How does that relates to applications dealing with languages? No relation.
    */
   public static final int PROFILE_OFFSET_07_LANGUAGEID1   = A_OBJECT_BASIC_SIZE + 14;

   /**
    * 
    */
   public static final int PROFILE_OFFSET_03_TYPE1         = A_OBJECT_BASIC_SIZE + 2;

   /**
    * Number of times the application is started.
    */
   public static final int PROFILE_OFFSET_04_STARTS2       = A_OBJECT_BASIC_SIZE + 4;

   /**
    * Total Running in seconds. For all user profiles
    */
   public static final int PROFILE_OFFSET_05_RUNNING_TIME4 = A_OBJECT_BASIC_SIZE + 6;
}
