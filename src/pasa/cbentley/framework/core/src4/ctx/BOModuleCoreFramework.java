package pasa.cbentley.framework.core.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.BOModuleAbstract;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.src4.app.ITechCtxSettingsAppli;

public class BOModuleCoreFramework extends BOModuleAbstract {

   protected final CoreFrameworkCtx cfc;

   public BOModuleCoreFramework(CoreFrameworkCtx cfc) {
      super(cfc.getBOC());
      this.cfc = cfc;
   }

   public String toStringGetDIDString(int did, int value) {
      // TODO Auto-generated method stub
      return null;
   }

   public ByteObject getFlagOrderedBO(ByteObject bo, int offset, int flag) {
      // TODO Auto-generated method stub
      return null;
   }

   public ByteObject merge(ByteObject root, ByteObject merge) {
      // TODO Auto-generated method stub
      return null;
   }

   public boolean toString(Dctx dc, ByteObject bo) {
      // TODO Auto-generated method stub
      return false;
   }

   public boolean toStringSubType(Dctx dc, ByteObject bo, int subType) {
      int type = bo.getType();
      switch (type) {
         case IBOTypesBOC.TYPE_012_CTX_SETTINGS:
            if (subType == ITechCtxSettingsCoreFramework.CTX_COREFW_TYPE_SUB) {
               dc.rootN(bo, "ITechCtxSettingsCoreFramework");
            } else if (subType == ITechCtxSettingsAppli.CTX_APP_TYPE_SUB) {
               dc.rootN(bo, "ITechCtxSettingsAppli");
               int appStarts = bo.get2(ITechCtxSettingsAppli.CTX_APP_OFFSET_04_STARTS2);
               dc.appendVarWithSpace("appStarts", appStarts);

               int runningTime = bo.get4(ITechCtxSettingsAppli.CTX_APP_OFFSET_05_RUNNING_TIME4);
               dc.appendVarWithSpace("runningTime", runningTime);

               boolean isHeadLess = bo.hasFlag(ITechCtxSettingsAppli.CTX_APP_OFFSET_01_FLAG, ITechCtxSettingsAppli.CTX_APP_FLAG_1_HEADLESS);
               dc.appendVarWithSpace("isHeadLess", isHeadLess);

               boolean isDragDrop = bo.hasFlag(ITechCtxSettingsAppli.CTX_APP_OFFSET_02_FLAGX, ITechCtxSettingsAppli.CTX_APP_FLAGX_2_DRAG_DROP);
               dc.appendVarWithSpace("isDragDrop", isDragDrop);
               
               boolean isOneThumb = bo.hasFlag(ITechCtxSettingsAppli.CTX_APP_OFFSET_02_FLAGX, ITechCtxSettingsAppli.CTX_APP_FLAGX_8_ONE_THUMB);
               dc.appendVarWithSpace("isOneThumb", isOneThumb);

            }
            break;
         default:
            return false;
      }
      return true;
   }

   public boolean toString1Line(Dctx dc, ByteObject bo) {
      // TODO Auto-generated method stub
      return false;
   }

   public String toStringOffset(ByteObject o, int offset) {
      // TODO Auto-generated method stub
      return null;
   }

   public String toStringType(int type) {
      // TODO Auto-generated method stub
      return null;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, BOModuleCoreFramework.class, "@line88");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, BOModuleCoreFramework.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
