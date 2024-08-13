package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.core.src4.event.ILifeContext;
import pasa.cbentley.core.src4.event.ILifeListener;

public class LifeContext implements ILifeContext {

   private AppCtx apc;

   public LifeContext(AppCtx apc) {
      this.apc = apc;
   }

   public void addFailure(ILifeListener lis) {
      // TODO Auto-generated method stub

   }

}
