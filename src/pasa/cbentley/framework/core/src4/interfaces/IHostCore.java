package pasa.cbentley.framework.core.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechFeaturesDraw;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostUI;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;

/**
 * Shortcuts to {@link ITechHostCore} values.
 * <br>
 * 
 * Umbrella for {@link ITechFeaturesDraw}, {@link ITechHostUI} for the underlying {@link CoreFrameworkCtx}.
 * 
 * @author Charles Bentley
 *
 */
public interface IHostCore extends IHostUI, ITechHostCore {

   /**
    * Enable/Disable a feature. 
    * 
    * <li> {@link ITechHostUI#SUP_ID_28_ALWAYS_ON_TOP}
    * 
    * <li> {@link ITechHostCore#SUP_ID_20_CLIPBOARD}
    * 
    * <li> {@link ITechFeaturesDraw#SUP_ID_06_CUSTOM_FONTS}
    * <li> {@link ITechFeaturesDraw#SUP_ID_07_IMAGE_SCALING}
    * 
    * @param featureID
    * @param b
    * @return true if the change was successful
    */
   public boolean enableFeature(int featureID, boolean b);

   /**
    * 
    * @return
    */
   public CoreFrameworkCtx getCFC();

   /**
    * Sets the Feature at the factory settings. overides and forces the reset of all user defined
    * <br>
    * <br>
    * @param featureID
    * @param b
    * @return
    */
   public boolean enableFeatureFactory(int featureID, boolean b);

   /**
    * 
    * @param dataID
    * @return
    */
   public float getHostFloat(int dataID);

   /**
    * Returns a configuration value from host framework context.
    * 
    * <li> {@link ITechHostCore#DATA_ID_11_FLING_SPEED_MAX}
    * <li> {@link ITechHostCore#DATA_ID_12_FLING_SPEED_MIN}
    * 
    * @param dataID
    * @return
    */
   public int getHostInt(int dataID);

   /**
    * Object class is defined by 
    * <li> {@link ITechHostCore#DATA_ID_OBJ_01_SCREENS}
    * @param dataID
    * @return
    */
   public Object getHostObject(int dataID);

   /**
    * Returns the data from the host as a String.
    * 
    * @param dataID
    * @return
    */
   public String getHostString(int dataID);

   /**
    * {@link IBOHost}
    * @return
    */
   public ByteObject getTechHost();

   /**
    * Query Host for support of features
    * <li> {@link ITechHostCore#SUP_ID_02_POINTERS}
    * <li> {@link ITechHostCore#SUP_ID_03_OPEN_GL}
    * <br>
    * <br>
    * 
    * @param supportID
    * @return
    */
   public boolean hasFeatureSupport(int supportID);

   /**
    * Return true when the feature is currently active/enabled.
    * 
    * <li> {@link ITechHostCore#SUP_ID_15_BLUETOOTH}
    * @param featureID
    * @return
    */
   public boolean isFeatureEnabled(int featureID);

}
