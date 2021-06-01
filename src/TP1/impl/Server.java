package TP1.impl;

import TP1.autogen.Counter;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import static java.lang.System.*;

public class Server{
    public static void main(String[] args) {
        try{
            Properties props = getProperties();
            ORB orb = ORB.init(args,props);
            org.omg.CORBA.Object obj = null;
            POA rootPOA = null;

            try{
                obj = orb.resolve_initial_references("RootPOA");
                rootPOA = POAHelper.narrow(obj);
            } catch (InvalidName invalidName) {
                invalidName.printStackTrace();
            }
            Counter_Impl c_impl = new Counter_Impl();
            Counter c = c_impl._this(orb);

            try{
                FileOutputStream file = new FileOutputStream("Counter.ref");
                PrintWriter writer = new PrintWriter(file);
                String ref = orb.object_to_string(c);
                writer.println(ref);
                writer.flush();
                file.close();
                out.println("Server started." + "\nStop : Ctr-C");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                out.println("File Error: "+e.getMessage());
                exit(2);
            }
            rootPOA.the_POAManager().activate();
            orb.run();
        } catch (AdapterInactive adapterInactive) {
            out.println("Exception "+adapterInactive.getMessage());
            exit(1);
        }
    }
}
