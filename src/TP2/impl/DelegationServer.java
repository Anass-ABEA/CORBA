package TP2.impl;


import TP2.autogen.Counter;
import TP2.autogen.CounterPOATie;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import static java.lang.System.getProperties;
import static java.lang.System.out;

public class DelegationServer {
    public static void main(String[] args) {
        try {
            CounterDelagate cd ;
            JFrame f = new JFrame("Counter server");
            f.getContentPane().add(cd = new CounterDelagate());

            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
            Properties props = getProperties();
            ORB orb = ORB.init(args,props);
            org.omg.CORBA.Object obj = null;

            POA rootPOA = null;

            try {
                obj = orb.resolve_initial_references("RootPOA");
                rootPOA = POAHelper.narrow(obj);
            } catch (InvalidName invalidName) {
                invalidName.printStackTrace();
            }
            CounterPOATie c_impl = new CounterPOATie(cd);
            Counter c = c_impl._this(orb);
            try {
                FileOutputStream file = new FileOutputStream("Counter.ref");
                PrintWriter printWriter = new PrintWriter(file);
                String ref = orb.object_to_string(c);
                printWriter.println(ref);
                printWriter.flush();
                file.close();
                out.println("SERVER STARTED \n\tSTOP : Close-button");
            } catch (IOException e) {
                e.printStackTrace();
            }
            rootPOA.the_POAManager().activate();
            orb.run();


        } catch (AdapterInactive adapterInactive) {
            adapterInactive.printStackTrace();
        }
    }
}
