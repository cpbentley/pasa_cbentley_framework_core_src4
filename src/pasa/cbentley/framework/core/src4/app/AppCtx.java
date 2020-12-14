package pasa.cbentley.framework.core.src4.app;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.i8n.IStringProducer;
import pasa.cbentley.core.src4.interfaces.IAInitable;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.thread.WorkerThread;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.src4.i8n.StringProducerBasic;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.coredata.src4.ctx.CoreDataCtx;
import pasa.cbentley.framework.coredraw.src4.interfaces.IFontFactory;
import pasa.cbentley.framework.coredraw.src4.interfaces.IImageFactory;
import pasa.cbentley.framework.coreio.src4.ctx.CoreIOCtx;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ITechCtxSettingsCoreUI;

/**
 * Stub context for applications that want to live and die inside the {@link CoreFrameworkCtx}
 * 
 * The {@link HostCtx} and {@link CanvasCtx} is given by the {@link ILauncherHost}
 * 
 * @author Charles Bentley
 *
 */
public abstract class AppCtx extends ABOCtx implements IAInitable, ITechCtxSettingsAppli {

   protected final CoreFrameworkCtx cfc;

   protected final IConfigApp       configApp;

   protected final IStringProducer  stringProducer;

   private WorkerThread             workerThread;

   /**
    * Update this field in the constructor when updating to a new version
    */
   protected String                 version = "1.0";

   public AppCtx(IConfigApp configApp, CoreFrameworkCtx cfc) {
      super(configApp, cfc.getBOC());
      if (configApp == null) {
         throw new NullPointerException();
      }
      this.configApp = configApp;
      this.cfc = cfc;
      this.stringProducer = new StringProducerBasic(cfc);
   }

   public void a_Init() {
      super.a_Init();
   }
   
   public AppCtx(IConfigApp configApp, CoreFrameworkCtx cfc, IStringProducer stringProducer) {
      super(configApp, cfc.getBOC());
      if (configApp == null) {
         throw new NullPointerException();
      }
      this.configApp = configApp;
      this.cfc = cfc;
      this.stringProducer = stringProducer;
   }

   protected void matchConfig(IConfigBO config, ByteObject settings) {
      IConfigApp configApp = (IConfigApp)config;
      settings.setFlag(CTX_APP_OFFSET_02_FLAGX, CTX_APP_FLAGX_2_DRAG_DROP, configApp.isAppDragDropEnabled());

   }

   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {
      //forward drag setting to the coreui
      boolean appDrag = settingsNew.hasFlag(CTX_APP_OFFSET_02_FLAGX, CTX_APP_FLAGX_2_DRAG_DROP);
      
      ByteObject settingsBO = cfc.getCUC().getSettingsBOForModification();
      settingsBO.setFlag(ITechCtxSettingsCoreUI.CTX_COREUI_OFFSET_01_FLAG1, ITechCtxSettingsCoreUI.CTX_COREUI_FLAG_2_DRAG_DROP, appDrag);
      cfc.getCUC().applyChanges(settingsBO);
   }

   public ICtx[] getCtxSub() {
      return new ICtx[] { cfc };
   }
   
   /**
    * Returns a String of this App version.
    * 
    * Used for running updaters
    * 
    * @return
    */
   public String getVersion() {
      return version;
   }

   public IAppli getAppli() {
      return getCoordinator().getAppli();
   }

   public int getBOCtxSettingSize() {
      return ITechCtxSettingsAppli.CTX_APP_BASIC_SIZE;
   }

   public CoreFrameworkCtx getCFC() {
      return cfc;
   }

   public IConfigApp getConfigApp() {
      return configApp;
   }

   public CoordinatorAbstract getCoordinator() {
      return cfc.getCoordinator();
   }

   public CoreDataCtx getCoreDataCtx() {
      return cfc.getCoreDataCtx();
   }

   public CoreIOCtx getCoreIOCtx() {
      return cfc.getCoreIOCtx();
   }

   public CoreUiCtx getCUC() {
      return cfc.getCUC();
   }

   public IFontFactory getFontFactory() {
      return cfc.getCUC().getFontFactory();
   }

   /**
    * Application context dependant. Implementation knows which launchers are available
    * @param class1
    * @return
    */
   public ILauncherHost[] getHostLaunchers(Class class1) {
      // TODO Auto-generated method stub
      return null;
   }

   public IImageFactory getImageFactory() {
      return cfc.getCUC().getImageFactory();
   }

   public IStringProducer getStrings() {
      return stringProducer;
   }

   public WorkerThread getWorkerThreadApp() {
      if (workerThread == null) {
         workerThread = new WorkerThread(getUCtx());
      }
      return workerThread;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, AppCtx.class);
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(configApp, "configApp");
      dc.nlLvl(stringProducer, "stringProducer");
      dc.nlLvl(cfc, CoreFrameworkCtx.class);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, AppCtx.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("version", version);
   }

   //#enddebug

}
