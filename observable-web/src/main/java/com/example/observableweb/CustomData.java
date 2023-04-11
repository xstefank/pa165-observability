package com.example.observableweb;

public class CustomData {


    private String text;
    private int counter;

    public CustomData() {
    }

    public CustomData(String text, int counter) {

        this.text = text;
        this.counter = counter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
