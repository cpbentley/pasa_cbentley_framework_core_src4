package pasa.cbentley.framework.core.src4.ctx;

import java.io.IOException;
import java.io.InputStream;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.byteobjects.src4.stator.StatorWriterBO;
import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IStaticIDs;
import pasa.cbentley.core.src4.event.EventBusArray;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.event.ILifeContext;
import pasa.cbentley.core.src4.event.ILifeListener;
import pasa.cbentley.core.src4.i8n.IStringsKernel;
import pasa.cbentley.core.src4.interfaces.ITimeCtrl;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.core.src4.app.IConfigApp;
import pasa.cbentley.framework.core.src4.app.IStringsCoreFramework;
import pasa.cbentley.framework.core.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.src4.interfaces.IAPIService;
import pasa.cbentley.framework.core.src4.interfaces.IDependencies;
import pasa.cbentley.framework.core.src4.interfaces.IHost;
import pasa.cbentley.framework.core.src4.interfaces.IHostUITools;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.core.src4.interfaces.ITechFeaturesHost;
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

   protected final CoreUiCtx     cuc;

   protected final CoreDataCtx   dac;

   private EventBusArray         eventBus;

   protected final CoreIOCtx     ioc;

   protected final ILauncherHost launcher;

   private IntToObjects          services;

   public CoreFrameworkCtx(IConfigCoreFramework config, CoreUiCtx cuc, CoreDataCtx dac, CoreIOCtx ioc, ILauncherHost launcher) {
      super(config, cuc.getBOC());
      this.cuc = cuc;
      this.dac = dac;
      this.ioc = ioc;
      this.launcher = launcher;
      services = new IntToObjects(uc);
      eventBus = new EventBusArray(uc, this, getEventBaseTopology());

      boModule = new BOModuleCoreFramework(this);

   }

   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {

   }

   /**
    * Returns a class implementing the given API id.
    * <li> {@link ITechHost#SUP_ID_38_GAMEPADS}
    * @param id
    * @return
    */
   /**
    * Retursn null if no APIService can be found or created for this ID.
    * <br>
    * <br>
    * 
    * For registering a service class, {@link AbstractDriver#registerServiceProvider(Object, int)}.
    * <br>
    * TODO unregisters service
    */
   public IAPIService getAPIService(int id) {
      //#debug
      toDLog().pFlow("id=" + id + " services ->", services, CoreFrameworkCtx.class, "getAPIService", LVL_05_FINE, false);

      //look up registered service providers
      int index = services.findInt(id);
      if (index != -1) {
         ServiceCtx o = (ServiceCtx) services.getObjectAtIndex(index);
         if (o.service instanceof String) {
            //try loading the class
            try {
               String className = (String) o.service;
               Class c = Class.forName(className);
               //#debug
               toDLog().pBridge1("Init " + className + " for ID=" + id, this, CoreFrameworkCtx.class, "getAPIService");
               //api services such a GamePAD JInput
               IAPIService apiService = (IAPIService) c.newInstance();
               apiService.setCtx(o.context);
               services.setObject(apiService, index); //set the apiservice to later uses
               return apiService;
            } catch (ClassNotFoundException e) {
               e.printStackTrace();
               return null;
            } catch (InstantiationException e) {
               e.printStackTrace();
            } catch (IllegalAccessException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         } else if (o instanceof IAPIService) {
            return (IAPIService) o;
         }
      }
      return null;
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

   public IConfigApp getConfigApp() {
      return null;
   }

   public CoordinatorAbstract getCoordinator() {
      return launcher.getCoordinator();
   }

   public CoreDataCtx getCoreDataCtx() {
      return dac;
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

   public abstract IHost getHost();

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

   public abstract IHostUITools getHostTools();

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

   /**
    * Does this draw context support the given draw feature id.
    * 
    * @param supportID
    * @return
    */
   public boolean hasFeatureSupport(int supportID) {
      switch (supportID) {
         case ITechFeaturesHost.SUP_ID_01_KEYBOARD:
            return true;
         case ITechFeaturesHost.SUP_ID_02_POINTERS:
            return true;
         case ITechFeaturesHost.SUP_ID_03_OPEN_GL:
            return true;
         case ITechFeaturesHost.SUP_ID_05_SCREEN_ROTATIONS:
            return false;
         case ITechFeaturesHost.SUP_ID_24_MULTIPLE_WINDOWS:
            return true;
         case ITechFeaturesHost.SUP_ID_37_JINPUT:
            //true if platform dependant api is available
            return getAPIService(ITechFeaturesHost.SUP_ID_37_JINPUT) != null;
         case ITechFeaturesHost.SUP_ID_38_GAMEPADS:
            //true if platform dependant api is available
            return getAPIService(ITechFeaturesHost.SUP_ID_38_GAMEPADS) != null;
         default:
            break;
      }
      return false;
   }

   public void lifePaused(ILifeContext context) {
      for (int i = 0; i < services.getSize(); i++) {
         Object o = services.getObjectAtIndex(i);
         if (o instanceof IAPIService) {
            ((IAPIService) o).lifePaused(context);
         }
      }
   }

   public void lifeResumed(ILifeContext context) {
      for (int i = 0; i < services.getSize(); i++) {
         Object o = services.getObjectAtIndex(i);
         if (o instanceof IAPIService) {
            ((IAPIService) o).lifeResumed(context);
         }
      }
   }

   public void lifeStarted(ILifeContext context) {

   }

   public void lifeStopped(ILifeContext context) {
      for (int i = 0; i < services.getSize(); i++) {
         Object o = services.getObjectAtIndex(i);
         if (o instanceof IAPIService) {
            ((IAPIService) o).lifeStopped(context);
         }
      }
   }

   protected void matchConfig(IConfigBO config, ByteObject settings) {

   }

   /**
    * Overrides any object at given id
    */
   public boolean registerServiceProvider(Object service, int id) {
      services.add(id, service);
      return true;
   }

   /**
    * When service is not defined/null, the host will register default service 
    * <br>
    * If a host has gamepad functionalities, it will register the
    * class name with {@link ITechFeaturesHost#SUP_ID_38_GAMEPADS} id
    * @param service force the driver to use the given class
    * @param id 
    * <br>
    * @return true when host managed to find the given service
    */
   public boolean setAPIService(int id, Object service, ACtx serviceContext) {
      services.add(id, new ServiceCtx(service, serviceContext));
      return true;
   }

   public void stateReadAppUi(StatorReaderBO state) {
      cuc.stateReadAppUi(state);
   }

   public void stateWriteAppUi(StatorWriterBO state) {
      cuc.stateWriteAppUi(state);
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
      dc.nlLvl(services, "services");
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
