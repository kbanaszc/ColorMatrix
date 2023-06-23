package com.example.colormatrix.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colormatrix.DBHelpers.SavesDBHelper;
import com.example.colormatrix.JSONHandler;
import com.example.colormatrix.R;
import com.example.colormatrix.Singletons.DeviceInUse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class OptionsFragment extends Fragment {

    EditText nameInput;
    SeekBar seekBar;
    Button saveButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference  databaseReference;
    JSONHandler jsonHandler;
    SavesDBHelper savesDBHelper;
    DeviceInUse deviceInUse;

    public OptionsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_options, container, false);
        nameInput = rootView.findViewById(R.id.nameInput);
        seekBar = rootView.findViewById(R.id.seekBar);
        saveButton = rootView.findViewById(R.id.saveButton);
        savesDBHelper = new SavesDBHelper(getContext());


        saveButton.setOnClickListener(v-> {
            try {
                saveButtonClicked();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jsonHandler = new JSONHandler(getContext());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                deviceInUse = DeviceInUse.getInstance();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference().child(deviceInUse.getDeviceName()+"_brightness");
                databaseReference.setValue(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        return rootView;
    }

    public void saveButtonClicked() throws JSONException, IOException {
        String saveName = nameInput.getText().toString();
        if(checkIfNameIsTaken(saveName))
        {
            savesDBHelper.addPixelArt(saveName);
        }
        jsonHandler.writeToJsonFile(saveName, getContext());
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DrawingGridFragment()).commit();
    }

    public boolean checkIfNameIsTaken(String saveName){
        Cursor cursor = savesDBHelper.readAllData();
        boolean flag = true;
        if (cursor.getCount() == 0) {
        } else {
            while (cursor.moveToNext()) {
                if(cursor.getString(1).equals(saveName)){
                    flag = false;
                }
            }
        }
        return flag;
    }
}