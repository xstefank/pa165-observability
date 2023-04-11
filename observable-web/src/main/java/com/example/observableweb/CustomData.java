package com.example.observableweb;

public class CustomData {


    private String whatever;
    private int i;

    public CustomData() {
    }

    public CustomData(String whatever, int i) {

        this.whatever = whatever;
        this.i = i;
    }

    public String getWhatever() {
        return whatever;
    }

    public void setWhatever(String whatever) {
        this.whatever = whatever;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
