package pasa.cbentley.framework.core.src4.ctx;

import pasa.cbentley.core.src4.ctx.ACtx;

class ServiceCtx {
   ACtx   context;

   Object service;

   public ServiceCtx(Object service, ACtx context) {
      this.service = service;
      this.context = context;

   }
}