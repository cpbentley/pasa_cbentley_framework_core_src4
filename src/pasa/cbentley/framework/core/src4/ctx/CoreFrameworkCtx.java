package pasa.cbentley.framework.core.src4.ctx;

import java.io.IOException;
import java.io.InputStream;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.core.src4.api.ApiManager;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IStaticIDs;
import pasa.cbentley.core.src4.event.EventBusArray;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.event.ILifeContext;
import pasa.cbentley.core.src4.event.ILifeListener;
import pasa.cbentley.core.src4.i8n.IStringsKernel;
import pasa.cbentley.core.src4.interfaces.ITimeCtrl;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.src4.app.AppCtx;
import pasa.cbentley.framework.core.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.src4.interfaces.IDependencies;
import pasa.cbentley.framework.core.src4.interfaces.IHostCore;
import pasa.cbentley.framework.core.src4.interfaces.IHostCoreTools;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherAppli;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.coredata.src4.ctx.CoreDataCtx;
import pasa.cbentley.framework.coredraw.src4.ctx.CoreDrawCtx;
import pasa.cbentley.framework.coreio.src4.ctx.CoreIOCtx;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;

/**
 * Implemented by Swing/J2ME/Android
 * 
 * Configurated by {@link IConfigCoreFramework}
 * 
 * Requires 
 * <li>{@link CoreUiCtx} for laying windows which has {@link CoreDrawCtx} for drawing on a canvas
 * <li>{@link CoreDataCtx}
 * <li>{@link CoreIOCtx} for io in the framework
 * 
 * <br>
 * <br>
 * 
 * {@link ILauncherHost} takes a reference to the first object that was created.
 * 
 * @author Charles Bentley
 *
 */
public abstract class CoreFrameworkCtx extends ABOCtx implements IEventsCoreFramework, ILifeListener {

   private BOModuleCoreFramework boModule;

   private IConfigCoreFramework  configCoreFramework;

   protected final CoreUiCtx     cuc;

   protected final CoreDataCtx   dac;

   private EventBusArray         eventBus;

   protected final CoreIOCtx     ioc;

   protected final ILauncherHost launcher;

   public CoreFrameworkCtx(IConfigCoreFramework config, CoreUiCtx cuc, CoreDataCtx dac, CoreIOCtx ioc, ILauncherHost launcher) {
      super(config, cuc.getBOC());
      this.configCoreFramework = config;
      this.cuc = cuc;
      this.dac = dac;
      this.ioc = ioc;
      this.launcher = launcher;
      eventBus = new EventBusArray(uc, this, getEventBaseTopology());

      boModule = new BOModuleCoreFramework(this);

   }

   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {

   }

   public ApiManager getApiManager() {
      return getUCtx().getApiManager();
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

   public IConfigCoreFramework getConfigCoreFramework() {
      return configCoreFramework;
   }

   public CoordinatorAbstract getCoordinator() {
      return launcher.getCoordinator();
   }

   public CoreDataCtx getCoreDataCtx() {
      return dac;
   }
   
   public CoreDrawCtx getCDC() {
      return cuc.getCDC();
   }

   public CoreIOCtx getCoreIOCtx() {
      return ioc;
   }

   public ICtx[] getCtxSub() {
      return new ICtx[] { cuc, ioc, dac };
   }

   public CoreUiCtx getCUC() {
      return cuc;
   }

   /**
    * Called by {@link ILauncherAppli#createAppOnFramework(CoreFrameworkCtx)}.
    * 
    * It needs app dependencies to create the {@link AppCtx}
    * @return
    */
   public IDependencies getDependenciesFromLauncher() {
      return launcher.getDependencies();
   }

   public int[] getEventBaseTopology() {
      int[] events = new int[IEventsCoreFramework.BASE_EVENTS];
      events[IEventsCoreFramework.PID_0_ANY] = IEventsCoreFramework.PID_0_ANY;
      return events;
   }

   public IEventBus getEventBus() {
      return eventBus;
   }

   public abstract IHostCore getHostCore();

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

   /**
    * Time controller for this framework
    * @return
    */
   public abstract ITimeCtrl getTimeCtrl();

   public void lifePaused(ILifeContext context) {
   }

   public void lifeResumed(ILifeContext context) {
   }

   public void lifeStarted(ILifeContext context) {

   }

   public void lifeStopped(ILifeContext context) {
   }

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
