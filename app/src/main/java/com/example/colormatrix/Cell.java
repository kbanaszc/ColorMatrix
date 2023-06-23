package com.example.colormatrix;

public class Cell {

    private int index;
    private int red;
    private int green;
    private int blue;

    public Cell (int index, int red, int green, int blue){
        this.index = index;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getIndex() {return index; }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
