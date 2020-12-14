package pasa.cbentley.framework.core.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.framework.coreio.src4.file.IFileConnection;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;

/**
 * Interface to control the tools provided by the host
 * <li> Light
 * <li> Vibrator
 * <li> Screen brightness
 * <li> Volume?
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public interface IHostUITools {

   /**
    * Should it not be a canvas feature?
    */
   public void flashLightToggle();


   /**
    * Host uses its own UI to ask the user for a file.
    * <br>
    * Starting Directory and File requirements are described in.
    * <br>
    * As well as the ContextID which identifies the filechooser (for later settings)
    * <br>
    * Save last used directory.
    * <br>
    * The host might use a blocking or non blocking request. There is no way to know that.
    * <br>
    * if non blocking, an event will be posted event for Canceled. In all cases, an event is generated
    * 
    * 
    * It is not a CoreUI because {@link IFileConnection} is not part of {@link CoreUiCtx}.
    * 
    * @param context
    * @param fcTech 
    * @return
    */
   public abstract IFileConnection getFileChooser(ICanvasHost context, ByteObject fcTech);


}
