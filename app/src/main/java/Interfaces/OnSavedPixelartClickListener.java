package Interfaces;

import org.json.JSONException;

import java.io.IOException;

public interface OnSavedPixelartClickListener {
    public void saveClicked(String s) throws JSONException, IOException;
    public void saveHold(String name);
}
