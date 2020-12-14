package pasa.cbentley.framework.core.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.src4.app.IAppli;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.coreui.src4.ctx.IConfigCoreUI;

/**
 * Created by the {@link ILauncherHost}. The {@link IAppli} can get the reference
 * with {@link IAppManager#}
 * 
 * Handy for creating launch configuration in code. A live user app will store all
 * settings in application settings
 * 
 * Part of the DeviceDriver
 * <br>
 * This is the place where JarName and System properties are loaded.
 * <br>
 * <br>
 * In some frameworks, the launch values are not used
 * <br>
 * <br>
 * The values are not modified.
 * 
 * @author Charles-Philip Bentley
 *
 */
public class LaunchValues implements IStringable {

   private CoreFrameworkCtx hoc;

   public LaunchValues(CoreFrameworkCtx kec) {
      this.hoc = kec;

   }

   /**
    * If value is configurable
    */
   public int              w;

   public int              h;

   /**
    * GxState of an application when it was closed
    */
   public byte[]           appState;

   public int              x                      = Integer.MIN_VALUE;

   public int              y                      = Integer.MIN_VALUE;

   public int              debugMode              = 0;

   public int              debugPrintFlags        = 0;

   /**
    * This is potentially localized
    */
   public String           name;

   public String           iconPath;

   /**
    * TODO migrate this to host properties
    */
   public boolean          severalPointerIDs      = true;

   public String           dataPath;

   /**
    * TODO this will depends on the Host.
    * On Desktop it is configurable. On Android it is not.
    * On Desktop use user.home and appString as path
    * "user.home"
    */
   public String           applicationDirectory;

   /**
    * Displays several instance of the program in the same window
    */
   public boolean          isMultipleDebug;

   public boolean          fullScreen;

   public String           fontNameProportional   = "Trebuchet MS";

   public String           fontNameMonoSpace      = "Trebuchet MS";

   public String           fontNameSystem         = "Trebuchet MS";

   public static final int FONT_VERY_LARGE_POINTS = 24;

   public static final int FONT_VERY_SMALL_POINTS = 8;

   public static final int FONT_LARGE_POINTS      = 20;

   public static final int FONT_MEDIUM_POINTS     = 16;

   public static final int FONT_SMALL_POINTS      = 12;

   public int[]            fontPoints             = new int[] { FONT_SMALL_POINTS, FONT_MEDIUM_POINTS, FONT_LARGE_POINTS, FONT_VERY_SMALL_POINTS, FONT_VERY_LARGE_POINTS };

   /**
    * Positive or negative value 
    */
   public int              fontPointsExtraShift   = 0;

   /**
    * Configuration logs flags for the logger.
    */
   public int              logFlags;

   /**
    * Non localized string identification of appli
    */
   public String           appStringHandle;

   //#debug
   public IDLog            starterLog;

   public boolean          noMusic;

   public boolean          isVolatileData         = false;

   public String getRMSPath() {
      if (dataPath == null || dataPath.equals("")) {
         dataPath = System.getProperty("user.home");
      }
      return dataPath;
   }

   public LaunchValues cloneMe() {
      LaunchValues lv = new LaunchValues(hoc);
      lv.w = w;
      lv.h = h;
      lv.x = x;
      lv.y = y;
      lv.name = name;
      lv.dataPath = dataPath;
      lv.fontNameProportional = fontNameProportional;
      lv.fontNameMonoSpace = fontNameMonoSpace;
      lv.fontNameSystem = fontNameSystem;
      lv.fontPoints = new int[fontPoints.length];
      for (int i = 0; i < fontPoints.length; i++) {
         lv.fontPoints[i] = fontPoints[i];
      }
      lv.debugMode = debugMode;
      lv.appState = appState;
      lv.applicationDirectory = applicationDirectory;
      lv.iconPath = iconPath;
      lv.appStringHandle = appStringHandle;
      return lv;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public UCtx toStringGetUCtx() {
      return hoc.getUCtx();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "LaunchValues");
      dc.nlVar("Name", name);
      dc.nlVar("dataPath", dataPath);
      dc.nlVar("iconPath", iconPath);
      dc.nlVar("applicationDirectory", applicationDirectory);
      dc.nlAppend("[" + x + "," + y + "] " + "[" + w + "," + h + "] ");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LaunchValues");
      String s = "[" + x + "," + y + "] " + "[" + w + "," + h + "] " + name + " data:" + dataPath + "applicationDirectory=" + applicationDirectory + " iconPath=" + iconPath + " " + severalPointerIDs;
      dc.append(s);
   }
   //#enddebug

   public String getAppWorkingDirectory() {
      return applicationDirectory;
   }

   public int getDefaultCanvasW() {
      return w;
   }

   public int getDefaultCanvasH() {
      return h;
   }

   public boolean isFullscreen() {
      return fullScreen;
   }

}
