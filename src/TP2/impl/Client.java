package TP2.impl;

import TP2.autogen.Counter;
import TP2.autogen.CounterHelper;
import org.omg.CORBA.ORB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

import static java.lang.System.*;

public class Client extends JPanel {
    ORB orb;
    Counter c;


    public Client(String[] args, String refFile) {
        initORB(args);
        org.omg.CORBA.Object obj = getRef(refFile);
        try {
            c = CounterHelper.narrow(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        createGUI();
    }

    private void initORB(String[] args) {
        Properties props = getProperties();
        orb = ORB.init(args, props);
    }

    private org.omg.CORBA.Object getRef(String refFile) {
        String ref = null;
        try {
            Scanner reader = new Scanner(new File(refFile));
            ref = reader.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        org.omg.CORBA.Object obj = orb.string_to_object(ref);
        if (obj == null) {
            out.println("INVALID IOR");
            exit(4);
        }
        return obj;
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

    public static void main(String[] args) {
        try{
            String reffile = "Counter.ref";
            JFrame f = new JFrame("Counter client");
            f.getContentPane().add( new Client(args,reffile) );
            f.pack();
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setVisible(true);

        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }
}
