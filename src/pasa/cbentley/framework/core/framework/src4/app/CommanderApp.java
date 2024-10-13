package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.ObjectU;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.ctx.ObjectCFC;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.exec.ExecutionContext;
import pasa.cbentley.framework.core.ui.src4.tech.ITechFeaturesCanvas;

public class CommanderApp extends ObjectU {

   protected final AppliAbstract app;

   public CommanderApp(AppliAbstract app) {
      super(app.getAppCtx().getUC());
      this.app = app;
   }

   /**
    * Change the global settings and every canvas
    */
   public void cmdToggleAlias(ExecutionContext ex) {
      ByteObject appliTech = app.getCtxSettingsAppli();

      int alias = appliTech.setToggle1(IBOCtxSettingsAppli.CTX_APP_OFFSET_08_ANTI_ALIAS1, IBOCtxSettingsAppli.CTX_APP_ALIAS_1_ON, IBOCtxSettingsAppli.CTX_APP_ALIAS_2_OFF);

      //appli tech changed.. apply them to each canvas ?
      boolean isSetAlias = alias == IBOCtxSettingsAppli.CTX_APP_ALIAS_1_ON;
      CanvasHostAbstract[] canvases = app.getCFC().getCUC().getCanvases();
      for (int i = 0; i < canvases.length; i++) {
         CanvasHostAbstract canvas = canvases[i];
         canvas.setCanvasFeature(ITechFeaturesCanvas.SUP_ID_04_ALIAS, isSetAlias);
      }
   }

   public void cmdStateLoad(ExecutionContext ec) {
      app.amsAppLoad();
   }

   /**
    * Delete exsiting settings data on disk 
    */
   public void cmdDeleteStatorData(ExecutionContext ec) {
      app.getStator().deleteDataAll();
      app.stator = null;
      app.getUC().getCtxManager().deleteStartData();
   }

   public void cmdStateSave(ExecutionContext ec) {
      app.amsAppExitWriteStator();
   }

}
