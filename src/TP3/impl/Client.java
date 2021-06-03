package TP3.impl;

import TP3.autogen.Counter;
import TP3.autogen.CounterHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import static java.lang.System.getProperties;

public class Client extends JPanel {
    private Counter c;
    private ORB orb;

    private void initializeORB(String[] args) {
        Properties props = getProperties();
        orb = ORB.init(args, props);
    }


    public void createGUI() {
        setLayout(new GridLayout(2, 1));
        JPanel p = new JPanel();
        final JLabel value;
        p.add(new JLabel("Counter Value : ", JLabel.RIGHT));
        p.add(value = new JLabel("" + c.value()));
        add(p);
        p = new JPanel();
        JButton inc, dec;
        p.add(inc = new JButton("INCREMENT"));
        p.add(dec = new JButton("DECREMENT"));
        add(p);
        inc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.inc();
                value.setText("" + c.value());
            }
        });

        dec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.dec();
                value.setText("" + c.value());
            }
        });
    }


    public Client(String[] args){
        try{
            initializeORB(args);
            NamingContext nc = NamingContextHelper.narrow(
                    orb.resolve_initial_references("NameService")
            );
            NameComponent[] name = new NameComponent[1];
            name[0] = new NameComponent();
            name[0].id = "Counter";
            name[0].kind = "IIOP";
            org.omg.CORBA.Object obj = nc.resolve(name);
            c = CounterHelper.narrow(obj);
            createGUI();
        } catch (InvalidName | NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName invalidName) {
            invalidName.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Counter Client");
        f.getContentPane().add(new Client(args));
        f.pack();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
    }
}
