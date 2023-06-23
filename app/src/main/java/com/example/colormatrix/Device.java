package com.example.colormatrix;

import java.io.Serializable;

public class Device implements Serializable {

    public boolean isChecked = false;
    private String name;

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Device (boolean isChecked , String name){
        this.isChecked = isChecked;
        this.name = name;
    }

}

