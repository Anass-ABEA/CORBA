package TP3.impl;

import TP3.autogen.CounterPOA;

public class Counter_Impl extends CounterPOA {

    private int count ;
    @Override
    public int value() {
        return count;
    }

    @Override
    public void inc() {
        count++;
    }

    @Override
    public void dec() {
        count--;
    }
}
