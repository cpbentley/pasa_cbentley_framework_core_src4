package pasa.cbentley.framework.core.framework.src4.engine;

import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.interfaces.IHostCoreTools;

public abstract class CoreToolsAbstract implements IHostCoreTools {

   protected final CoreFrameworkCtx cfc;

   public CoreToolsAbstract(CoreFrameworkCtx cfc) {
      this.cfc = cfc;
   }
}
