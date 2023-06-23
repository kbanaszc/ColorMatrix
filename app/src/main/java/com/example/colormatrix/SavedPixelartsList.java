package com.example.colormatrix;

import java.util.ArrayList;
import java.util.List;

public class SavedPixelartsList {

    private List<SavedPixelart> savedPixelarts;

    public SavedPixelartsList(){
        savedPixelarts = new ArrayList<>();
    }

    public List<SavedPixelart> getSavedPixelarts() {
        return savedPixelarts;
    }
}
