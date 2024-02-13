package pasa.cbentley.framework.core.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;
import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.framework.core.src4.app.IAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostUI;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;

/**
 * Describe the Host framework capabilities to the App.
 * <br>
 * The Host generates the following events to the {@link IAppli}
 * <li> {@link IEventsBentleyFw#PID_02_CANVAS_02_DRAG_DROP} when content has been dragged and dropped
 * 
 * The UI capabilities are described by {@link ITechHostUI}
 * 
 * <br>
 * <br>
 * The host fills this data when loading
 * 
 * @see ITechHostUI
 * @author Charles-Philip
 *
 */
public interface ITechHostCore extends ITechHostUI {



}
