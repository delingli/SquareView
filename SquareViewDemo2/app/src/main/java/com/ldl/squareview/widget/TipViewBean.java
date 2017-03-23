package com.ldl.squareview.widget;

/**
 * Created by ldl on 2017/3/2.
 */

public class TipViewBean {

    private boolean rose;//涨或者跌

    private  String nickName;
    private int l;
    private int t;
    private int r;
    private int b;

    public boolean isRose() {
        return rose;
    }

    public void setRose(boolean rose) {
        this.rose = rose;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
