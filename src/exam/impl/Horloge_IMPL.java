package exam.impl;

import exam.autogen.HorlogePOA;

public class Horloge_IMPL extends HorlogePOA {
    private int heure;
    private int minute;
    @Override
    public int heure() {
        return heure;
    }

    @Override
    public int minute() {
        return minute;
    }

    @Override
    public String GMT() {
        return ""+heure+":"+minute;
    }

    @Override
    public String GMT_PLUS_1() {
        return ""+((heure+1)%24)+":"+minute;
    }

    @Override
    public void setTime(int h, int m){
        heure = h;
        minute = m;
    }


}
