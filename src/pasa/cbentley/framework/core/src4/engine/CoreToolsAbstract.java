package pasa.cbentley.framework.core.src4.engine;

import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.interfaces.IHostUITools;

public abstract class CoreToolsAbstract implements IHostUITools {

   protected final CoreFrameworkCtx cfc;

   public CoreToolsAbstract(CoreFrameworkCtx cfc) {
      this.cfc = cfc;
   }
}
