package pasa.cbentley.framework.core.framework.src4.ctx;

import java.io.IOException;
import java.io.InputStream;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.byteobjects.src4.ctx.IStaticIDsBO;
import pasa.cbentley.core.src4.api.ApiManager;
import pasa.cbentley.core.src4.ctx.CtxManager;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IStaticIDs;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.i8n.IStringsKernel;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.interfaces.IHost;
import pasa.cbentley.core.src4.interfaces.IHostFeature;
import pasa.cbentley.core.src4.interfaces.ITimeCtrl;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.data.src4.ctx.CoreDataCtx;
import pasa.cbentley.framework.core.framework.src4.app.AppCtx;
import pasa.cbentley.framework.core.framework.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.framework.src4.interfaces.ICreatorAppli;
import pasa.cbentley.framework.core.framework.src4.interfaces.IDependencies;
import pasa.cbentley.framework.core.framework.src4.interfaces.IHostCoreTools;
import pasa.cbentley.framework.core.framework.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.core.io.src4.ctx.CoreIOCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coredraw.src4.ctx.CoreDrawCtx;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;

/**
 * 5 Core Context which is the required minimum for a working App
 * 
 * <li>{@link CoreDrawCtx} managing host fonts, images and {@link IGraphics} implementations
 * <li>{@link CoreUiCtx} for laying windows which has {@link CoreDrawCtx} for drawing on a canvas
 * <li>{@link CoreDataCtx} to save data on disk
 * <li>{@link CoreIOCtx} for basic Input/Output operations 
 * 
 * 
 * Implemented by Swing/J2ME/Android
 * 
 * Configurated by {@link IConfigCoreFramework}
 * 
 * 
 * <p>
 * {@link ILauncherHost} takes a reference to the first object that was created.
 * </p>
 * 
 * @author Charles Bentley
 *
 */
public abstract class CoreFrameworkCtx extends ABOCtx implements IEventsCoreFramework {

   private BOModuleCoreFramework boModule;

   private IConfigCoreFramework  configCoreFramework;

   protected final CoreUiCtx     cuc;

   protected final CoreDataCtx   dac;

   private IEventBus             eventBus;

   private IHost                 host;

   protected final CoreIOCtx     ioc;

   protected final ILauncherHost launcher;

   /**
    * 5 Dependencies:
    * @param config {@link IConfigCoreFramework}
    * @param cuc
    * @param dac
    * @param ioc
    * @param launcher {@link ILauncherHost} that creates this {@link CoreFrameworkCtx}
    */
   public CoreFrameworkCtx(IConfigCoreFramework config, CoreUiCtx cuc, CoreDataCtx dac, CoreIOCtx ioc, ILauncherHost launcher) {
      super(config, cuc.getBOC());
      this.configCoreFramework = config;

      this.cuc = cuc;
      this.dac = dac;
      this.ioc = ioc;
      this.launcher = launcher;

      boModule = new BOModuleCoreFramework(this);

      CtxManager cm = uc.getCtxManager();

      cm.registerStaticRange(this, IStaticIDsBO.SID_BYTEOBJECT_TYPES, IBOTypesCoreFramework.AZ_BOTYPE_FW_A, IBOTypesCoreFramework.AZ_BOTYPE_FW_Z);
      cm.registerStaticRange(this, IStaticIDsBO.SID_EVENTS, IEventsCoreFramework.A_SID_FRAMEWORK_EVENT_A, IEventsCoreFramework.A_SID_FRAMEWORK_EVENT_Z);

      int[] topo = getEventBaseTopology();
      eventBus = uc.getOrCreateEventBus(this, IEventsCoreFramework.A_SID_FRAMEWORK_EVENT_A, IEventsCoreFramework.A_SID_FRAMEWORK_EVENT_Z, topo);

   }

   public void a_Init() {
      super.a_Init();

      //we cannot call abstract method in the constructor. so we moved it here
      host = createHost();
      //we need to set it up on draw
      cuc.getCDC().setHost(host);
      uc.setHost(host);
   }

   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {

   }

   public ITimeCtrl getTimeCtrl() {
      return host.getTimeCtrl();
   }

   protected abstract IHost createHost();

   public ApiManager getApiManager() {
      return getUC().getApiManager();
   }

   public BOCtx getBOC() {
      return cuc.getBOC();
   }

   /**
    * Implementation may increase it by extending {@link ITechCtxSettingsCoreFramework}
    */
   public int getBOCtxSettingSize() {
      return ITechCtxSettingsCoreFramework.CTX_COREFW_BASIC_SIZE;
   }

   public CoreDrawCtx getCDC() {
      return cuc.getCDC();
   }

   public IConfigCoreFramework getConfigCoreFramework() {
      return configCoreFramework;
   }

   /**
    * 
    * @return
    */
   public CoordinatorAbstract getCoordinator() {
      return launcher.getCoordinator();
   }

   public CoreDataCtx getCoreDataCtx() {
      return dac;
   }

   public CoreDataCtx getDAC() {
      return dac;
   }

   public CoreIOCtx getCoreIOCtx() {
      return ioc;
   }

   public ICtx[] getCtxSub() {
      return new ICtx[] { cuc, ioc, dac };
   }

   public IExecutor getExecutor() {
      return getHost().getExecutor();
   }

   public CoreUiCtx getCUC() {
      return cuc;
   }

   /**
    * Called by {@link ICreatorAppli#createAppOnFramework(CoreFrameworkCtx)}.
    * 
    * It needs app dependencies to create the {@link AppCtx}
    * Some dependencies are host dep
    * 
    * Some dependencies are not host. just library code
    * @return
    */
   public IDependencies getDependenciesFromLauncher() {
      return launcher.getDependencies();
   }

   public int[] getEventBaseTopology() {
      int[] events = new int[IEventsCoreFramework.FRAMEWORK_NUM_EVENTS];
      events[IEventsCoreFramework.PID_00] = IEventsCoreFramework.PID_00_XX;
      events[IEventsCoreFramework.PID_01] = IEventsCoreFramework.PID_01_XX;
      events[IEventsCoreFramework.PID_02] = IEventsCoreFramework.PID_02_XX;
      return events;
   }

   public IEventBus getEventBus() {
      return eventBus;
   }

   public IHost getHost() {
      return host;
   }

   public IHostFeature getHostFeature() {
      return host.getHostFeature();
   }

   /**
    * Possible launchers for.
    * 
    * Framed, Panel.. It will depends on the calling launcher and the app.
    * 
    * In 
    * @param cl
    * @return
    */
   public ILauncherHost[] getHostLaunchers(Class cl) {
      // TODO Auto-generated method stub
      return null;
   }

   public ILauncherHost[] getHostLaunchers(String cl) {
      // TODO Auto-generated method stub
      return null;
   }

   public abstract IHostCoreTools getHostTools();

   /**
    * The launcher that created this context. 
    * @return
    */
   public ILauncherHost getLauncherHost() {
      return launcher;
   }

   /**
    * Default implementation just calls {@link Class#getResourceAsStream(String)}
    * <br>
    * Checks if it starts with a /
    * @param name
    * @return
    */
   public InputStream getResourceAsStream(String name) throws IOException {
      //TODO remove and use IOUTILS
      String m = getResourcePath(name);
      InputStream is = getClass().getResourceAsStream(m);
      return is;
   }

   public String getResourcePath(String name) throws IOException {
      String m = name;
      if (name.charAt(0) != '/')
         m = '/' + name;
      return m;
   }

   /**
    * Returns a stack of String for the stack trace.
    * <br>
    * empty array if host could not print the stack 
    * @return not null
    */
   public abstract String[] getStackTrace(Throwable e);

   /**
    * <li> {@link IStringsKernel#SID_STRINGS}
    * @param type
    * @param key
    * @return -1 if not found
    */
   public int getStaticKeyRegistrationID(int type, int key) {
      if (type == IStaticIDs.SID_STRINGS) {
         if (key >= IStringsCoreFramework.ACORE_F_STR_A && key <= IStringsCoreFramework.ACORE_F_STR_Z) {
            return key - IStringsCoreFramework.ACORE_F_STR_A;
         }
      }
      return -1;
   }

   /**
    * String ID identifying the Host.
    * <br>
    * @return
    */
   public abstract String getStringIDReal();

   protected void matchConfig(IConfigBO config, ByteObject settings) {

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CoreFrameworkCtx.class);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvlCtx(cuc, CoreUiCtx.class);
      dc.nlLvlCtx(dac, CoreDataCtx.class);
      dc.nlLvlCtx(ioc, CoreIOCtx.class);

      dc.nlLvl(eventBus, "eventBus");
      dc.nlLvl(launcher, "launcher");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoreFrameworkCtx.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
