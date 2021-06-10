package exam.nomage;

import exam.autogen.Horloge;
import exam.autogen.HorlogeHelper;
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
    private Horloge horloge;
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
        p.add(value = new JLabel("00:00"));
        add(p);

        JPanel entree = new JPanel();
        JTextField heureInput = new JTextField(2);
        JTextField minuteInput = new JTextField(2);

        entree.add(heureInput);
        entree.add(minuteInput);

        add(entree);
        JButton setTime;
        setTime = new JButton("CHOISIR DATE");
        setTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int h = Integer.parseInt(heureInput.getText());
                int m = Integer.parseInt(minuteInput.getText());
                horloge.setTime(h,m);
                value.setText(horloge.GMT());

            }
        });

        p = new JPanel();
        JButton gmt, gmtplus;
        p.add(gmt = new JButton("GMT"));
        p.add(gmtplus = new JButton("GMT+1"));
        p.add(setTime);
        add(p);
        gmt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value.setText("" + horloge.GMT());
            }
        });

        gmtplus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value.setText("" + horloge.GMT_PLUS_1());
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
            name[0].id = "Horloge";
            name[0].kind = "Object";
            org.omg.CORBA.Object obj = nc.resolve(name);
            horloge = HorlogeHelper.narrow(obj);
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
