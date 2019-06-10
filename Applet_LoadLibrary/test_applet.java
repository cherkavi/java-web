import java.awt.*;
import java.io.*;
import java.lang.*;
import java.applet.*;

public class loadLibrary extends Applet {
    String s;

    public void paint(Graphics g) {
	try {
	  // load data from ABSOLUTE PATH
	  Runtime.getRuntime().load("/usr/lib/libnet.so");
	  // load data from java.library.path
	  //Runtime.getRuntime().loadLibrary("/usr/lib/libnet.so");
	
	  g.drawString("Successfully loaded /usr/lib/libnet.so", 10, 10);
	}
	catch (SecurityException e) {
	  g.drawString("Caught security exception trying to load a library", 10, 10);
        }
   }
}

