package com.example.colormatrix.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.colormatrix.DBHelpers.SavesDBHelper;
import com.example.colormatrix.Fragments.DrawingGridFragment;
import com.example.colormatrix.Fragments.YourWorksFragment;
import com.example.colormatrix.R;

public class DeleteSaveFragment extends Fragment {

    Button yesButton, noButton;
    SavesDBHelper savesDBHelper;
    String name;

    public DeleteSaveFragment(String name) {
        this.name = name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_delete_save, container, false);
        noButton = rootView.findViewById(R.id.noButton);
        yesButton = rootView.findViewById(R.id.yesButton);
        savesDBHelper = new SavesDBHelper(getContext());

        noButton.setOnClickListener(v->noButtonClickd());
        yesButton.setOnClickListener(v->yesButtonClickd(name));
        return rootView;
    }

    public void noButtonClickd(){
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DrawingGridFragment()).commit();
    }

    public void yesButtonClickd(String name){
        savesDBHelper.deletePixelArt(name);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new YourWorksFragment()).commit();
    }
}