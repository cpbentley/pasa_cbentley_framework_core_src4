package pasa.cbentley.framework.core.src4.i8n;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IStaticIDs;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.i8n.I8nString;
import pasa.cbentley.core.src4.i8n.IStringMapper;
import pasa.cbentley.core.src4.i8n.IStringProducer;
import pasa.cbentley.core.src4.i8n.LString;
import pasa.cbentley.core.src4.i8n.LocaleID;
import pasa.cbentley.core.src4.i8n.StringProducerAbstract;
import pasa.cbentley.core.src4.io.FileLineReader;
import pasa.cbentley.core.src4.io.FileReader;
import pasa.cbentley.core.src4.io.LineReaderIntToStrings;
import pasa.cbentley.core.src4.io.XString;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.structs.IntToStrings;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;

/**
 * The default implementation of {@link IStringProducer}.
 * <br>
 * It takes {@link IntToStrings} mappings from files. Everytime there is a request,
 * the String key is mapped to the right module, and then a simple adressing is done simply.
 * 
 * Very basic. No edit. No Db connection. Just plain text file.
 * 
 * @author Charles Bentley
 *
 */
public class StringProducerBasic extends StringProducerAbstract implements IStringProducer {

   private IntToStrings[]           data       = new IntToStrings[2];

   /**
    * Used to store Default strings. English.
    */
   private IntToStrings[]           dataBackup = new IntToStrings[2];

   protected final CoreFrameworkCtx cfc;

   private IntToObjects             ictxs;

   private Hashtable                paths      = new Hashtable();

   public StringProducerBasic(CoreFrameworkCtx cfc) {
      super(cfc.getUCtx(), new LocaleID[] { new LocaleID(cfc.getUCtx(), "English", "en") });
      this.cfc = cfc;
      ictxs = new IntToObjects(uc);
   }

   public LocaleID addLocaleID(String name, String suffix) {
      LocaleID lid = new LocaleID(uc, name, suffix);
      LocaleID[] old = lids;
      LocaleID[] ns = new LocaleID[old.length + 1];
      for (int i = 0; i < old.length; i++) {
         ns[i] = old[i];
      }
      ns[ns.length - 1] = lid;
      lids = ns;
      return lid;
   }

   private void addMap(ICtx cl, IntToStrings values) {
      data = uc.getAU().ensureCapacity(data, cl.getRegistrationID());
      data[cl.getRegistrationID()] = values;
   }

   private void addMap(ICtx cl, String path) {
      //no loading
      try {
         IntToStrings its = readStrings2(path);
         if (its != null) {
            addMap(cl, its);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public I8nString getIStr(String key) {
      LString ls = new LString(this, key, null);
      return ls;
   }

   public I8nString getIString(int key) {
      LString ls = new LString(this, key, null);
      return ls;
   }

   public I8nString getIString(String key, String def) {
      LString ls = new LString(this, key, def);
      return ls;
   }

   public I8nString getIString(String key, String def, String suffix) {
      LString ls = new LString(this, key, def);
      ls.setSuffix(suffix);
      return ls;
   }

   public I8nString getIStringKey(int key, String def) {
      LString ls = new LString(this, key, def);
      return ls;
   }

   public int getProducerID() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * TODO Note.
    * 
    * once the key has been matched once, code can use regid and stringid as keys for later
    * access
    */
   public String getString(int key) {
      //find the right module
      //ask everymodule if that string key belongs to him   
      for (int i = 0; i < ictxs.nextempty; i++) {
         ICtx ctx = (ICtx) ictxs.objects[i];
         int stringID = ctx.getStaticKeyRegistrationID(IStaticIDs.SID_STRINGS, key);
         if (stringID != -1) {
            stringID--; //minus 1 because String ID starts at 1 to match the line number in the text file
            //
            if (stringID < 0) {
               //TODO create an error framework to manage dev errors
               if (uc.getConfigU().isForceExceptions()) {
                  //#debug
                  toDLog().pNull("key=" + key + " stringID=" + stringID, this, StringProducerBasic.class, "getString", LVL_05_FINE, false);
                  throw new RuntimeException("String Definition Must Start at 1");
               } else {
                  return "Bad Key " + key;
               }
            }
            int regid = ctx.getRegistrationID();
            if (regid < 0 || regid >= data.length) {
               throw new IllegalArgumentException("Registration ID " + regid);
            }
            if (stringID >= data[regid].nextempty) {
               return stringID + ":" + regid;
               //Back up plan?
               //               if (stringID >= dataBackup[regid].nextempty) {
               //                  //TODO dev warn return key?
               //                  throw new IllegalArgumentException("String Key does not match DB" + key);
               //               } else {
               //                  dataBackup[regid].getString(stringID);
               //               }
            }
            return data[regid].getString(stringID);
         }
      }
      return "key" + key;
   }

   public String getString(String key) {
      return key;
   }

   public IStringMapper getStringMapper() {
      //no string mapper here
      return null;
   }

   public I8nString getStrSuffixed(String key, String suffix) {
      LString ls = new LString(this, key, null);
      ls.setSuffix(suffix);
      return ls;
   }

   public void setValue(ICtx cl, LocaleID lid, int key, String string) {
      //0 based string id index. we must convert the key from 5001 to 1..
      //
      int stringID = cl.getStaticKeyRegistrationID(IStaticIDs.SID_STRINGS, key);
      if (stringID == -1) {
         throw new IllegalArgumentException("key=" + key + " " + string);
      }
      int regid = cl.getRegistrationID();
      data = uc.getAU().ensureCapacity(data, regid);
      if (data[regid] == null) {
         data[regid] = new IntToStrings(uc);
      }
      stringID--; //minus one because we go from 1based index to 0based index
      data[regid].setSafe(stringID, string);
   }

   /**
    * First modules loaded will have a first look up.
    * <br>
    * if the module has string locale data, load it
    */
   public void loads(ICtx cl, String pathid) {
      //no loading
      if (ictxs.hasObject(cl)) {
         throw new IllegalArgumentException("Module already loaded for " + pathid);
      }
      ictxs.add(cl);
      paths.put(pathid, cl);
      addMap(cl, pathid);
   }

   /**
    * Read Strings from filename
    * @param name
    * @return
    * @throws IOException
    */
   public IntToStrings readStrings2(String name) throws IOException {
      String suffix = "en";
      String file = name + "_" + suffix + ".txt";
      //look up the file with 
      IntToStrings its = new IntToStrings(uc);
      LineReaderIntToStrings lineReader = new LineReaderIntToStrings(its);
      FileLineReader flr = new FileLineReader(uc, lineReader, file);
      flr.setCharAvoid('#');
      flr.read(file);
      return its;
   }

   /**
    * Read Strings from filename
    * @param name
    * @return
    * @throws IOException
    */
   public IntToStrings readStrings(String name) throws IOException {
      String suffix = "en";
      String file = name + "_" + suffix + ".txt";
      //look up the file with 
      InputStream is = cfc.getResourceAsStream(file);
      if (is == null && suffix != "en") {
         //if language file does not exist, fall back in the default Locale
         file = name + "_en.txt";

         //if that one does not exist, throw an exception, missing resource
      }
      IntToStrings its = new IntToStrings(uc);

      FileReader fr = new FileReader(uc);
      XString cp = null;
      if (is != null) {
         fr.open(is, "UTF-8");
         while ((cp = fr.readCharLineIgnore('#')) != null) {
            its.add(cp.getString());
         }
         fr.close();
      } else {
         //dev warn.. not crashing the app
      }
      return its;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, StringProducerBasic.class, 232);
      dc.nlLvl("CurrentLocale", current);
      dc.nlLvlArray1Line(lids, "Locales");
      dc.append("Registered Module Paths");
      Enumeration enu = paths.keys();
      while (enu.hasMoreElements()) {
         String path = (String) enu.nextElement();
         ICtx mod = (ICtx) paths.get(path);
         dc.nl();
         dc.append(path + " : " + mod.getRegistrationID());
         dc.append(" " + mod.getClass().getName());
      }
      dc.nl();
      dc.append("Strings for Each Module (Sorted by Registration ID)");
      for (int i = 0; i < data.length; i++) {
         dc.nlLvlIndentIfNotNull("Registration ID " + i, data[i]);
      }
      for (int i = 0; i < dataBackup.length; i++) {
         dc.nlLvl("Backup Reg ID " + i, dataBackup[i]);
      }

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, StringProducerBasic.class);
      dc.append(" ");
      current.toString1Line(dc);
   }
   //#enddebug

}
