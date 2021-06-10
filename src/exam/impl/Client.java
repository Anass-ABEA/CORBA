package exam.impl;

import exam.autogen.Horloge;
import exam.autogen.HorlogeHelper;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import static java.lang.System.*;
import static java.lang.System.out;

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
            Horloge c = null;
            try{
                c  = HorlogeHelper.narrow(obj);
            }catch (BAD_PARAM ex){
                out.println("Narrowing failed ");
                exit(3);
            }
            int inp = -1;
            Scanner sc = new Scanner(System.in);
            out.println("ENTRER l'heure format HH:MM");
            String line  = sc.nextLine();
            int h = Integer.parseInt(line.split(":")[0]);
            int m = Integer.parseInt(line.split(":")[1]);
            c.setTime(h,m);

            do{
                out.println("Horloge  GMT / GMT+1 \n Action (0/1/e)?");
                out.flush();
                do{
                    try{
                        inp = in.read();
                    } catch (IOException e) {
                    }

                }while (inp!='0' && inp!='1' && inp!='e');
                if(inp=='0') out.println(c.GMT());
                if(inp=='1') out.println(c.GMT_PLUS_1());

            }while (inp!='e');

        }catch (Exception e){
            out.println("Exception "+e.getMessage());
        }
    }
}
