package exam.nomage;

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
        String msg = "";
        if(heure<10){
            msg+="0"+heure;
        }else{
            msg+=heure;
        }
        msg+=":";
        if(minute<10){
            msg+="0"+minute;
        }else {
            msg+=minute;
        }
        return msg;
    }

    @Override
    public String GMT_PLUS_1() {
        String msg = "";
        if(heure<10){
            msg+="0"+((heure+1)%24);
        }else{
            msg+=((heure+1)%24);
        }
        msg+=":";
        if(minute<10){
            msg+="0"+minute;
        }else {
            msg+=minute;
        }
        return msg;
    }

    @Override
    public void setTime(int h, int m){
        heure = h;
        minute = m;
    }

    @Override
    public String toString() {
        return "HEURE GMT\t"+GMT()+"\nHEURE GMT+1\t"+GMT_PLUS_1();
    }
}
