package pasa.cbentley.framework.core.framework.src4.app;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.i8n.IStringProducer;
import pasa.cbentley.core.src4.interfaces.IAInitable;
import pasa.cbentley.core.src4.interfaces.IHost;
import pasa.cbentley.core.src4.interfaces.IHostData;
import pasa.cbentley.core.src4.interfaces.IHostFeature;
import pasa.cbentley.core.src4.interfaces.IHostService;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.core.src4.thread.WorkerThread;
import pasa.cbentley.framework.core.data.src4.ctx.CoreDataCtx;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.ctx.IBOTypesCoreFramework;
import pasa.cbentley.framework.core.framework.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.framework.src4.i8n.StringProducerBasic;
import pasa.cbentley.framework.core.framework.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.core.io.src4.ctx.CoreIOCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.IBOCtxSettingsCoreUi;
import pasa.cbentley.framework.coredraw.src4.ctx.CoreDrawCtx;
import pasa.cbentley.framework.coredraw.src4.interfaces.IFontFactory;
import pasa.cbentley.framework.coredraw.src4.interfaces.IImageFactory;

/**
 * Stub context for applications that want to live and die inside the {@link CoreFrameworkCtx}
 * 
 * The {@link HostCtx} and {@link CanvasCtx} is given by the {@link ILauncherHost}
 * 
 * <p>
 * Attention SubClasses! : You must class guard call {@link AppCtx#a_Init()} at the end of the constructor.
 * </p>
 * @author Charles Bentley
 *
 */
public abstract class AppCtx extends ABOCtx implements IAInitable, IBOCtxSettingsAppli {

   protected final CoreFrameworkCtx cfc;

   protected final IConfigApp       configApp;

   private IStatorFactory           statorFactory;

   protected final IStringProducer  stringProducer;

   /**
    * Update this field in the constructor when updating to a new version
    */
   protected String                 version = "1.0";


   public AppCtx(IConfigApp configApp, CoreFrameworkCtx cfc) {
      super(configApp, cfc.getBOC());
      if (configApp == null) {
         throw new NullPointerException("Null configApp");
      }
      this.configApp = configApp;
      this.cfc = cfc;
      this.stringProducer = new StringProducerBasic(cfc);
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

   public void a_Init() {
      super.a_Init();
   }

   /**
    * App is able to interpret the {@link ByteObject} values
    */
   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {
      settingsNew.checkType(CTX_BASIC_TYPE);
      if (settingsOld != null) {
         settingsOld.checkType(CTX_BASIC_TYPE);
      }

      //forward drag setting to the coreui
      boolean appDrag = settingsNew.hasFlag(CTX_APP_OFFSET_02_FLAGX, CTX_APP_FLAGX_2_DRAG_DROP);

      ByteObject settingsBO = cfc.getCUC().getSettingsBOForModification();
      settingsBO.setFlag(IBOCtxSettingsCoreUi.CTX_COREUI_OFFSET_01_FLAG1, IBOCtxSettingsCoreUi.CTX_COREUI_FLAG_2_DRAG_DROP, appDrag);
      cfc.getCUC().applyChanges(settingsBO);
   }

   /**
    * {@link IBOProfileApp}
    * 
    * @return
    */
   public ByteObject createEmptyProfile() {
      int type = IBOTypesCoreFramework.FTYPE_3_PROFILE;
      int size = IBOProfileApp.PROFILE_BASIC_SIZE;
      ByteObject bo = getBOC().getByteObjectFactory().createByteObject(type, size);
      return bo;
   }

   public IAppli getAppli() {
      return getCoordinator().getAppli();
   }

   public int getBOCtxSettingSize() {
      return IBOCtxSettingsAppli.CTX_APP_BASIC_SIZE;
   }

   public CoreDrawCtx getCDC() {
      return cfc.getCUC().getCDC();
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

   public ICtx[] getCtxSub() {
      return new ICtx[] { cfc };
   }

   public CoreUiCtx getCUC() {
      return cfc.getCUC();
   }

   public IFontFactory getFontFactory() {
      return cfc.getCUC().getFontFactory();
   }

   public IHost getHost() {
      return cfc.getHost();
   }

   public IHostData getHostData() {
      return cfc.getHost().getHostData();
   }

   public IHostFeature getHostFeature() {
      return cfc.getHost().getHostFeature();
   }

   /**
    * Application context dependant. Implementation knows which launchers are available.
    * 
    * @param class1
    * @return
    */
   public ILauncherHost[] getHostLaunchers(Class class1) {
      // TODO Auto-generated method stub
      return null;
   }

   public IHostService getHostService() {
      return cfc.getHost().getHostService();
   }

   public IImageFactory getImageFactory() {
      return cfc.getCUC().getImageFactory();
   }

   public IStatorFactory getStatorFactory() {
      if (statorFactory == null) {
         statorFactory = new StatorFactoryApp(this);
      }
      return statorFactory;
   }

   public IStringProducer getStringProducer() {
      return stringProducer;
   }

   public IStringProducer getStrings() {
      return stringProducer;
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

   protected void matchConfig(IConfigBO config, ByteObject settings) {
      IConfigApp configApp = (IConfigApp) config;
      settings.setFlag(CTX_APP_OFFSET_02_FLAGX, CTX_APP_FLAGX_2_DRAG_DROP, configApp.isAppDragDropEnabled());
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
