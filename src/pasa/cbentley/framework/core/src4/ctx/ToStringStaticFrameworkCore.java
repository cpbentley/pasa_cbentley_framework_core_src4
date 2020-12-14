package pasa.cbentley.framework.core.src4.ctx;

import pasa.cbentley.core.src4.logging.ToStringStaticBase;
import pasa.cbentley.framework.core.src4.app.ITechAppli;
import pasa.cbentley.framework.core.src4.interfaces.ITechFeaturesHost;

public class ToStringStaticFrameworkCore extends ToStringStaticBase {

   public static String toStringFeature(int feat) {
      switch (feat) {
         case ITechFeaturesHost.SUP_ID_38_GAMEPADS:
            return "GamePads";
         default:
            return "UnknownFeature"+feat;
      }
   }
   
   public static String toStringState(int state) {
      switch (state) {
         case ITechAppli.STATE_0_CREATED:
            return "Created";
         case ITechAppli.STATE_1_LOADED:
            return "Loaded";
         case ITechAppli.STATE_2_STARTED:
            return "Started";
         case ITechAppli.STATE_3_PAUSED:
            return "Paussed";
         case ITechAppli.STATE_4_DESTROYED:
            return "Destroyed";
         default:
            return "UnknownState " + state;
      }
   }

}
