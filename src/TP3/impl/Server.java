package TP3.impl;

import TP3.autogen.Counter;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

import java.util.Properties;

import static java.lang.System.getProperties;

public class Server {
    private ORB orb;
    private POA rootPOA;

    private void initializeORB(String[] args) {
        Properties props = getProperties();
        orb = ORB.init(args, props);
        try {
            rootPOA = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA"));
        } catch (InvalidName invalidName) {
            invalidName.printStackTrace();
        }
    }

    public Server(String[] args) {
        try {
            initializeORB(args);
            NamingContext nc = NamingContextHelper.narrow(
                    orb.resolve_initial_references("NameService"));
            Counter_Impl c_impl = new Counter_Impl();
            Counter c = c_impl._this(orb);
            NameComponent[] name = new NameComponent[1];
            name[0] = new NameComponent();
            name[0].id = "Counter";
            name[0].kind = "IIOP";
            nc.rebind(name, c);
            System.out.println("Server started. Stop : Ctrl-C");
            rootPOA.the_POAManager().activate();
            orb.run();
        } catch (InvalidName | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound | AdapterInactive invalidName) {
            invalidName.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(args);
    }
}
