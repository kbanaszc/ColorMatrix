package com.example.colormatrix.Singletons;

public final class MyColor {
    private static MyColor instance;
    private int red;
    private int green;
    private int blue ;

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

    public String colorInHex(){
        String hex = String.format("%02x%02x%02x", this.red, this.green, this.blue);
        return hex;
    }

    public static MyColor getInstance(){
        if(instance==null){
            instance = new MyColor();
        }
        return instance;
    }

}
