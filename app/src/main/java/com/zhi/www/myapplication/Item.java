package com.zhi.www.myapplication;

/**
 * Created by Administrator on 2016/9/9.
 */
public class Item {

    private double value;
    private int type;

    public Item() {
    }

    public Item(double value, int type) {
        this.value = value;
        this.type = type;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }
}