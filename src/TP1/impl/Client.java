package TP1.impl;

import TP1.autogen.Counter;
import TP1.autogen.CounterHelper;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import static java.lang.System.*;

public class Client {
    public static void main(String[] args) {
        try{
            Properties props = getProperties();
            ORB orb = ORB.init(args,props);
            String ref = null;
            org.omg.CORBA.Object obj = null;

            try{
                Scanner reader = new Scanner(new File("Counter.ref"));
                ref = reader.nextLine();
            } catch (FileNotFoundException e) {
                out.println("File error "+e.getMessage());
                exit(2);
            }
            obj = orb.string_to_object(ref);
            if(obj == null){
                out.println("INVALID IOR");
                exit(4);
            }
            Counter c = null;
            try{
                c  = CounterHelper.narrow(obj);
            }catch (BAD_PARAM ex){
                out.println("Narrowing failed ");
                exit(3);
            }
            int inp = -1;
            do{
                out.println("Counter value "+ c.value()+ "\n Action (+/-/e)?");
                out.flush();
                do{
                    try{
                        inp = in.read();
                    } catch (IOException e) {
                    }

                }while (inp!='+' && inp!='-' && inp!='e');
                if(inp=='+') c.inc();
                if(inp=='-') c.dec();

            }while (inp!='e');

        }catch (Exception e){
            out.println("Exception "+e.getMessage());
        }
    }
}
