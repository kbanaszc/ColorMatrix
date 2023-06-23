package com.example.colormatrix.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.colormatrix.DBHelpers.ColorPaletteDBHelper;
import com.example.colormatrix.R;
import com.example.colormatrix.Singletons.MyColor;
import com.example.colormatrix.Singletons.SmilingCircle;


public class ColorPickerFragment extends Fragment {

    SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    TextView textViewRed, textViewGreen, textViewBlue, textViewRedHex, textViewGreenHex, textViewBlueHex ;
    View centerColor;
    MyColor myColor;
    View colorToChange;
    Button setColorButton;
    ColorPaletteDBHelper colorPaletteDBHelper;
    String colorToChangeId;
    SmilingCircle smilingCircle;

    public ColorPickerFragment() {
    }

    public ColorPickerFragment(View colorToChange, String colorToChangeId) {
        this.colorToChange = colorToChange;
        this.colorToChangeId = colorToChangeId;
    }
        // Required empty public constructor

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_color_picker, container, false);
        colorPaletteDBHelper = new ColorPaletteDBHelper(getActivity());
        textViewRedHex = rootView.findViewById(R.id.textViewRedHex);
        textViewGreenHex = rootView.findViewById(R.id.textViewGreenHex);
        textViewBlueHex = rootView.findViewById(R.id.textViewBlueHex);
        seekBarRed = rootView.findViewById(R.id.seekBarRed);
        seekBarGreen = rootView.findViewById(R.id.seekBarGreen);
        seekBarBlue = rootView.findViewById(R.id.seekBarBlue);
        textViewRed = rootView.findViewById(R.id.textViewRed);
        textViewGreen = rootView.findViewById(R.id.textViewGreen);
        textViewBlue = rootView.findViewById(R.id.textViewBlue);
        centerColor = rootView.findViewById(R.id.centerColor);
        myColor = MyColor.getInstance();
        textViewRedHex.setText(Integer.toString(myColor.getRed()));
        textViewGreenHex.setText(Integer.toString(myColor.getGreen()));
        textViewBlueHex.setText(Integer.toString(myColor.getBlue()));
        setColorButton = rootView.findViewById(R.id.set_color_button);


        seekBarRed.setProgress(myColor.getRed());
        seekBarGreen.setProgress(myColor.getGreen());
        seekBarBlue.setProgress(myColor.getBlue());


        setColorVisualization();

        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewRedHex.setText(Integer.toString(i));
                myColor.setRed(i);
                setColorVisualization();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewGreenHex.setText(Integer.toString(i));
                myColor.setGreen(i);
                setColorVisualization();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewBlueHex.setText(Integer.toString(i));
                myColor.setBlue(i);
                setColorVisualization();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        setColorButton.setOnClickListener(v-> setPickedColor());

        return rootView;
    }

public void setColorVisualization(){
       centerColor.setBackgroundColor(Color.argb(255,myColor.getRed(), myColor.getGreen(),myColor.getBlue()));
}

public void setPickedColor(){
        myColor = MyColor.getInstance();
        colorPaletteDBHelper.updateData(colorToChangeId,myColor.getRed(),myColor.getGreen(), myColor.getBlue());
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DrawingGridFragment()).commit();
        smilingCircle = SmilingCircle.getInstance();
        smilingCircle.setChosenCircle(Integer.parseInt(colorToChangeId)-1);

    }
}