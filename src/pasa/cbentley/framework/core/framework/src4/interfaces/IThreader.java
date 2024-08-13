package pasa.cbentley.framework.core.framework.src4.interfaces;

import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.thread.IBRunnable;
import pasa.cbentley.core.src4.thread.ITechRunnable;

/**
 * That's our framework way to encapsulates the context of the thread
 * <br>
 * Provides services for running tasks
 * Each host may have its own limitations.
 * Create a ThreadContext? Passed around every method need it.
 * ThreadContext is local to its thread and 
 * On a single thread host.
 * In Bentley, you create a Thread from IThread which creates
 * a ThreadContext interface.
 * TODO
 * For example, you can create a 
 * Java 5 Swing Host
 * Java 8 Swing Host
 * 
 * @author Charles Bentley
 *
 */
public interface IThreader {

   /**
    * Asks the Host to run this in the GUI thread.
    * <br>
    * @param run
    */
   public void callSerially(Runnable run);

   /**
    * if {@link IBRunnable} is {@link ITechRunnable#FLAG_07_UI_THREAD}
    * it will run in the main UI thread
    * @param r
    */
   public void processRunnable(IBRunnable r);

   /**
    * How can you map to host the thread local issue?
    * J2ME will need to implement it ? How
    * .
    * Create a ThreadContext? Passed around every method need it.
    * ThreadContext is local to its thread and 
    * 
    * Semantically threadlocal maps a thread to a value.
    * So threads can all have their own values.
    * For example a {@link StringBBuilder} can reuse an internal buffer to avoid
    * creating a new one. This allow several threads to use a static private variable
    * safely.
    * 
    * @param key
    * @return
    */
   public Object getThreadLocal(String key);
}
