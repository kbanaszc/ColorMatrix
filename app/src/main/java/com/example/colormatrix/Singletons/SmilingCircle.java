package com.example.colormatrix.Singletons;

public class SmilingCircle {

    private static SmilingCircle instance;
    private int chosenCircle = 0;

    public int getChosenCircle() {
        return chosenCircle;
    }

    public void setChosenCircle(int chosenCircle) {
        this.chosenCircle = chosenCircle;
    }

    public static SmilingCircle getInstance(){
        if(instance==null){
            instance = new SmilingCircle();
        }
        return instance;
    }
}
