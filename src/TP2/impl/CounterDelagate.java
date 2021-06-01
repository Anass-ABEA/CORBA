package TP2.impl;

import TP2.autogen.CounterOperations;

import javax.swing.*;

public class CounterDelagate extends JPanel implements CounterOperations {

    private int count;
    private JTextField value;

    public CounterDelagate(){
        count = 0;
        add(new JLabel("Counter value: ",JLabel.RIGHT));
        add(value = new JTextField((String.valueOf(count)),10));
        value.setEditable(false);
    }

    @Override
    public int value() {
        return count;
    }

    @Override
    public void inc() {
        value.setText(""+(++count));
    }

    @Override
    public void dec() {
        value.setText(""+(--count));
    }
}
