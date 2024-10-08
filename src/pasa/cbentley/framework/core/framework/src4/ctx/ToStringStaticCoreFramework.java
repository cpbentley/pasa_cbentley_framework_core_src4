package pasa.cbentley.framework.core.framework.src4.ctx;

import pasa.cbentley.core.src4.logging.ToStringStaticBase;
import pasa.cbentley.framework.core.framework.src4.app.ITechAppli;

public class ToStringStaticCoreFramework extends ToStringStaticBase {

   
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
