package com.example.colormatrix.Fragments;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.colormatrix.DBHelpers.ColorPaletteDBHelper;
import com.example.colormatrix.R;
import com.example.colormatrix.Singletons.MyColor;
import com.example.colormatrix.Singletons.SmilingCircle;

import java.util.ArrayList;

public class ColorPaletteFragment extends Fragment {

        MyColor myColor;
        View c2p_1, c2p_2, c2p_3, c2p_4, c2p_5;
        ColorPaletteDBHelper colorPaletteDBHelper;
        ArrayList<Integer> red_arr, green_arr, blue_arr;
        SmilingCircle smilingCircle;
        ArrayList<View> c2p_array;

        public ColorPaletteFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_color_palette, container, false);
            colorPaletteDBHelper = new ColorPaletteDBHelper(getActivity());
            c2p_1 = rootView.findViewById(R.id.color_to_pick_1);
            c2p_2 = rootView.findViewById(R.id.color_to_pick_2);
            c2p_3 = rootView.findViewById(R.id.color_to_pick_3);
            c2p_4 = rootView.findViewById(R.id.color_to_pick_4);
            c2p_5 = rootView.findViewById(R.id.color_to_pick_5);
            red_arr = new ArrayList<>();
            green_arr = new ArrayList<>();
            blue_arr = new ArrayList<>();
            c2p_array = new ArrayList<>();
            c2p_array.add(c2p_1);
            c2p_array.add(c2p_2);
            c2p_array.add(c2p_3);
            c2p_array.add(c2p_4);
            c2p_array.add(c2p_5);


            reedColorsFromDB(c2p_1, c2p_2, c2p_3, c2p_4, c2p_5);

            smilingCircle = SmilingCircle.getInstance();
            focusOnColor(c2p_array.get(smilingCircle.getChosenCircle()));


            c2p_1.setOnLongClickListener(v -> {colorToPickHeld(v,"1"); return true;});
            c2p_2.setOnLongClickListener(v -> {colorToPickHeld(v,"2"); return true;});
            c2p_3.setOnLongClickListener(v -> {colorToPickHeld(v,"3"); return true;});
            c2p_4.setOnLongClickListener(v -> {colorToPickHeld(v,"4"); return true;});
            c2p_5.setOnLongClickListener(v -> {colorToPickHeld(v,"5"); return true;});

            c2p_1.setOnClickListener(this::colorToPickClicked);
            c2p_2.setOnClickListener(this::colorToPickClicked);
            c2p_3.setOnClickListener(this::colorToPickClicked);
            c2p_4.setOnClickListener(this::colorToPickClicked);
            c2p_5.setOnClickListener(this::colorToPickClicked);

            return rootView;
        }


        public void colorToPickHeld(View view, String id){
            myColorHelper(view);
            navigateToColorPicker(view, id);
        }

        public void colorToPickClicked(View view){
            focusOnColor(view);
        }

        void reedColorsFromDB(View c2p_1, View c2p_2, View c2p_3, View c2p_4, View c2p_5){
            Cursor cursor = colorPaletteDBHelper.readAllData();

            if(cursor.getCount()==0){
                colorPaletteDBHelper.createFirstData();
            }else {
                while (cursor.moveToNext()) {
                    red_arr.add(Integer.parseInt(cursor.getString(1)));
                    green_arr.add(Integer.parseInt(cursor.getString(2)));
                    blue_arr.add(Integer.parseInt(cursor.getString(3)));
                }
            }
            c2p_1.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,red_arr.get(0),green_arr.get(0),blue_arr.get(0))));
            c2p_2.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,red_arr.get(1),green_arr.get(1),blue_arr.get(1))));
            c2p_3.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,red_arr.get(2),green_arr.get(2),blue_arr.get(2))));
            c2p_4.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,red_arr.get(3),green_arr.get(3),blue_arr.get(3))));
            c2p_5.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255,red_arr.get(4),green_arr.get(4),blue_arr.get(4))));

        }

        public void navigateToColorPicker(View view,String id){
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ColorPickerFragment(view, id)).commit();
        }

        public void focusOnColor(View view){
            c2p_1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_circle_24));
            c2p_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_circle_24));
            c2p_3.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_circle_24));
            c2p_4.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_circle_24));
            c2p_5.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_circle_24));
            view.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_emoji_emotions_24));
            myColorHelper(view);
        }

        public void myColorHelper(View view){
            try {
                myColor = MyColor.getInstance();
                Color color = Color.valueOf(view.getBackgroundTintList().getDefaultColor());
                myColor.setRed((int)(color.red()*255));
               // Toast.makeText(getContext(), Integer.toString((int)color.red()), Toast.LENGTH_SHORT).show();
                myColor.setGreen((int)(color.green()*255));
                myColor.setBlue((int)(color.blue()*255));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


