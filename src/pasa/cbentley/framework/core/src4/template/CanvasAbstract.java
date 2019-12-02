package pasa.cbentley.framework.core.src4.template;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.api.app.IAppli;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.engine.AbstractUITemplate;
import pasa.cbentley.framework.coreui.src4.interfaces.IGraphics;
import pasa.cbentley.framework.src4.host.interfaces.IEventsHostCtx;
import pasa.cbentley.framework.ui.ctx.CanvasCtx;
import pasa.cbentley.framework.ui.event.AppliEvent;
import pasa.cbentley.framework.ui.event.BEvent;
import pasa.cbentley.framework.ui.event.CanvasHostEvent;
import pasa.cbentley.framework.ui.event.DeviceEvent;
import pasa.cbentley.framework.ui.event.DeviceEventXY;
import pasa.cbentley.framework.ui.event.DeviceEventXYTouch;
import pasa.cbentley.framework.ui.hostappli.ICanvasAppli;
import pasa.cbentley.framework.ui.hostappli.ICanvasHost;
import pasa.cbentley.framework.ui.interfaces.IHostEvents;
import pasa.cbentley.framework.ui.tech.IBCodes;
import pasa.cbentley.framework.ui.tech.IInput;
import pasa.cbentley.framework.ui.tech.ITechCanvas;

/**
 * Host implementation will use composition to draw to its host.
 * Event canvas.
 * 
 * @author Charles Bentley
 *
 */
public abstract class CanvasAbstract extends AbstractUITemplate implements ICanvasHost, IEventConsumer {

   /** 
    * The Bentley Framework {@link ICanvasAppli}
    */
   protected ICanvasAppli  canvasAppli;


   /**
    * {@link ITechCanvas} definition
    */
   protected ByteObject    tech;

   public CanvasAbstract(CanvasCtx cac) {
      super(cac);
      //register for AppModule events
      cac.getHOC().getEventBus().addConsumer(this, IEventsHostCtx.PID_1_DEVICE, IEventsHostCtx.EVENT_ID_01_DEVICE_UPDATE);

   }

   /**
    * Event generated when the screen window is moved.
    * <br>
    * @param id
    * @param x
    * @param y
    */
   public void canvasPositionChangedBridge(int id, int x, int y) {
      if (canvasAppli != null) {
         CanvasHostEvent de = new CanvasHostEvent(fc, IHostEvents.ACTION_2_MOVED, this);
         de.setX(x);
         de.setY(y);
         canvasAppli.event(de);
      }
   }

   /**
    * @param id screen id where the origin of the canvas is located
    * @param w
    * @param h
    */
   public void canvasSizeChangedBridge(int id, int w, int h) {
      if (canvasAppli != null) {
         CanvasHostEvent de = new CanvasHostEvent(fc, IHostEvents.ACTION_3_RESIZED, this);
         de.setW(w);
         de.setH(h);
         canvasAppli.event(de);
      }
   }

   public void consumeEvent(BusEvent e) {
      // TODO Auto-generated method stub

   }

   public void eventBridge(BEvent g) {
      if (canvasAppli != null) {
         canvasAppli.event(g);
      }
   }

   public void fingerMovedBridge(int x, int y, int screenID, int fingerID) {
      this.fingerMovedBridge(x, y, screenID, fingerID, 0, 0);
   }

   /**
    * A Finger is a touch point on a touchscreen.
    * <br>
    * @param x
    * @param y
    * @param pointerID 0 based
    * @param fingerID 0 based
    */
   public void fingerMovedBridge(int x, int y, int screenID, int fingerID, float size, float pressure) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXYTouch(fc, screenID, IInput.MOD_3_MOVED, fingerID, x, y, size, pressure);
         canvasAppli.event(de);
      }
   }

   public void fingerPressedBridge(int x, int y, int screenID, int fingerID) {
      this.fingerPressedBridge(x, y, screenID, fingerID, 0, 0);
   }

   public void fingerPressedBridge(int x, int y, int screenID, int fingerID, float size, float pressure) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXYTouch(fc, screenID, IInput.MOD_0_PRESSED, fingerID, x, y, size, pressure);
         canvasAppli.event(de);
      }
   }

   public void fingerReleasedBridge(int x, int y, int screenID, int fingerID) {
      this.fingerReleasedBridge(x, y, screenID, fingerID, 0, 0);
   }

   public void fingerReleasedBridge(int x, int y, int screenID, int fingerID, float size, float pressure) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXYTouch(fc, screenID, IInput.MOD_1_RELEASED, fingerID, x, y, size, pressure);
         canvasAppli.event(de);
      }
   }

   public void focusGainedBridge() {
      //#debug
      toLog().pBridge("", null, CanvasAbstract.class, "focusGainedBridge");
      if (canvasAppli != null) {
         AppliEvent ge = new AppliEvent(fc, IHostEvents.ACTION_4_FOCUS_GAIN);
         canvasAppli.event(ge);
      }
   }

   /**
    * full appli focus lost.. not just a window
    */
   public void focusLostBridge() {
      //#debug
      toLog().pBridge("", null, CanvasAbstract.class, "focusLostBridge");
      if (canvasAppli != null) {
         AppliEvent ge = new AppliEvent(fc, IHostEvents.ACTION_5_FOCUS_LOSS);
         canvasAppli.event(ge);
      }

   }

   public ICanvasAppli getCurrentDisplayable() {
      return canvasAppli;
   }

   public ByteObject getTech() {
      return tech;
   }

   /**
    * Method from {@link ICanvasHost}
    */
   public void icRepaint() {
      icRepaint(0, 0, getICWidth(), getICHeight());
   }

   public void keyPressedBridge(int keyCode) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEvent(fc, IInput.DEVICE_0_KEYBOARD, 0, IInput.MOD_0_PRESSED, keyCode);
         canvasAppli.event(de);
      }
   }

   /**
    * Caller must make sure there is no repeat
    * @param finalCode
    */
   public void keyReleasedBridge(int keyCode) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEvent(fc, IInput.DEVICE_0_KEYBOARD, 0, IInput.MOD_1_RELEASED, keyCode);
         canvasAppli.event(de);
      }
   }

   public void mouseDraggedBridge(int x, int y, int pointerID) {
      //drags are moves. input module decides if it is a really drag or not.
      this.mouseMovedBridge(x, y, pointerID);
   }

   /**
    * TODO How to listen to keys released outside the frame?
    * <br>
    * Historically, we sent fake release events for every buttons pressed from this class. This is now
    * managed by the Framework input module on event Focus Out. The framework cannot trust consistency of
    * outside events anyways.
    * <br>
    * JInput gets all events even when outside of the frame.
    * <br>
    * @param x
    * @param y
    */
   public void mouseEnteredBridge(int x, int y) {
      if (canvasAppli != null) {
         //#debug
         toLog().pBridge("x=" + x + " y=" + y, null, CanvasAbstract.class, "mouseEnteredBridge");
         DeviceEventXY dex = new DeviceEventXY(fc, IInput.DEVICE_1_MOUSE, 0, IInput.MOD_3_MOVED, IInput.MOVE_1_ENTER, x, y);
         dex.setSource(canvasAppli);
         canvasAppli.event(dex);
      }
   }

   /**
    * Device event of type pointer. Exit and Entering Events.
    * Provides raw x, y and area
    * @param x
    * @param y
    */
   protected void mouseExitedBridge(int x, int y) {
      if (canvasAppli != null) {
         //#debug
         toLog().pBridge("x=" + x + " y=" + y, null, CanvasAbstract.class, "mouseExitedBridge");
         DeviceEventXY dex = new DeviceEventXY(fc, IInput.DEVICE_1_MOUSE, 0, IInput.MOD_3_MOVED, IInput.MOVE_2_EXIT, x, y);
         dex.setSource(canvasAppli);
         canvasAppli.event(dex);
      }
   }

   /**
    * TODO NativeController
    * When controlled by JInput disable if we have a NativeDeviceController. send position to Controller for 
    * NativeController sends BEvents to which Canvas?
    * @param x
    * @param y
    * @param id
    */
   public void mouseMovedBridge(int x, int y, int id) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXY(fc, IInput.DEVICE_1_MOUSE, id, IInput.MOD_3_MOVED, 0, x, y);
         canvasAppli.event(de);
      }
   }

   public void mousePressedBridge(int x, int y, int pointerID, int button) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXY(fc, IInput.DEVICE_1_MOUSE, pointerID, IInput.MOD_0_PRESSED, button, x, y);
         canvasAppli.event(de);
      }
   }

   public void mouseReleasedBridge(int x, int y, int pointerID, int button) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXY(fc, IInput.DEVICE_1_MOUSE, pointerID, IInput.MOD_1_RELEASED, button, x, y);
         canvasAppli.event(de);
      }
   }

   /**
    * Wheel events.
    * @param scrollAmount
    * @param rot
    */
   public void mouseWheeledBridge(int scrollAmount, int rot) {
      if (canvasAppli != null) {
         int code;
         if (rot == -1) {
            //up
            code = IBCodes.PBUTTON_3_WHEEL_UP;
         } else {
            //down
            code = IBCodes.PBUTTON_4_WHEEL_DOWN;
         }
         DeviceEvent de = new DeviceEventXY(fc, IInput.DEVICE_1_MOUSE, 0, IInput.MOD_5_WHEELED, 0, scrollAmount, code);
         canvasAppli.event(de);
      }
   }

   public abstract void onAppModuleChange(IAppli app);

   public void paintBridge(IGraphics g) {
      if (canvasAppli != null) {
         canvasAppli.paint(g);
      }
      //TODO generate an event for paint being finished?
      //driver.paintDone();
   }

   /**
    * Called by Swing AMS when the {@link IAppli} has been started.
    * <br>
    * <br>
    * It is setting
    */
   public void setAppModule(IAppli app) {
      onAppModuleChange(app);
   }

   public void setDisplayable(ICanvasAppli dis) {
      canvasAppli = dis;
   }

   /**
    * Toggle the Alias in Canvas Settings in
    */
   public void toggleAlias() {
      //TODO
   }

   //#mdebug

   public void toString(Dctx dc) {
      dc.root(this, "CanvasAbstract");
      toStringPrivate(dc);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CanvasAbstract");
      toStringPrivate(dc);
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
