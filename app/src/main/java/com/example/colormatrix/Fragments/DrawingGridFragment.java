package com.example.colormatrix.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.colormatrix.Cell;
import com.example.colormatrix.DBHelpers.CellsDBHelper;
import com.example.colormatrix.DrawingGridLogic;
import Adapters.DrawingGridRecyclerAdapter;
import Interfaces.OnCellClickListener;
import com.example.colormatrix.R;
import com.example.colormatrix.Singletons.DeviceInUse;
import com.example.colormatrix.Singletons.MyColor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DrawingGridFragment extends Fragment implements OnCellClickListener {
    RecyclerView drawingGridRecyclerView;
    DrawingGridRecyclerAdapter drawingGridRecyclerViewAdapter;
    DrawingGridLogic drawingGridLogic;
    MyColor myColor;
    CellsDBHelper cellsDBHelper;
    Button sendYourArtwork;
    View settingsButton;
    String daviceFormSP;
    DeviceInUse deviceInUse;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public DrawingGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawing_grid, container, false);

        settingsButton = rootView.findViewById(R.id.settings);
        settingsButton.setOnClickListener(v->openSettings());
        sendYourArtwork = rootView.findViewById(R.id.sendYourArtwork);
        sendYourArtwork.setOnClickListener(v->{sendYourArtwork();});
        cellsDBHelper = new CellsDBHelper(getActivity());
        drawingGridRecyclerView = rootView.findViewById(R.id.drawingGrid);
        drawingGridRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),8));
        drawingGridLogic = new DrawingGridLogic();
        List<Cell> cells = drawingGridLogic.getDrawingGrid().getCells();
        drawingGridRecyclerViewAdapter = new DrawingGridRecyclerAdapter(cells, this);
        drawingGridRecyclerView.setAdapter(drawingGridRecyclerViewAdapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Devices", Context.MODE_PRIVATE);
        daviceFormSP = sharedPreferences.getString("device","");
        deviceInUse = DeviceInUse.getInstance();
        deviceInUse.setDeviceName(daviceFormSP);

        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void cellClick(Cell cell, DatabaseReference databaseReference) {

        myColor = MyColor.getInstance();
        cell.setRed(myColor.getRed());
        cell.setGreen(myColor.getGreen());
        cell.setBlue(myColor.getBlue());
        cellsDBHelper.updateData(Integer.toString(cell.getIndex()+1), myColor.getRed(), myColor.getGreen(), myColor.getBlue());
        drawingGridRecyclerViewAdapter.notifyItemChanged(cell.getIndex());
        drawingGridRecyclerViewAdapter.notifyDataSetChanged();

        //This code sends new data each time single pixel changes
       // databaseReference.setValue(stringToPass(myColor.getRed(), myColor.getGreen(), myColor.getBlue()));

    }

    public void openSettings(){
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new OptionsFragment()).commit();
    }

    public void sendYourArtwork(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(deviceInUse.getDeviceName()+"_SET");
        databaseReference.setValue(true);
  //      databaseReference = firebaseDatabase.getReference().child(deviceInUse.getDeviceName());
        String x = wholeMatrixString();
//        databaseReference.setValue(x);

        for(int layer = 1 ; layer <=8 ; layer++){
            String qwerty = "";
            for(int q = 72*(layer-1); q < 72*layer; q++){
                qwerty=qwerty+x.charAt(q);
            }
            databaseReference = firebaseDatabase.getReference().child(deviceInUse.getDeviceName()).child("layer_"+Integer.toString(layer));
            databaseReference.setValue(qwerty);
        }
        }


    public String stringToPass(int r, int g, int b){
        String res="";
        if(r<100){
            if(r<10){
                res =res+"00"+Integer.toString(r);
            }
            else {
                res = res + "0" + Integer.toString(r);
            }
        }
        else res = res + Integer.toString(r);

        if(g<100){
            if(g<10){
                res =res+"00"+Integer.toString(g);
            }
            else {
                res = res + "0" + Integer.toString(g);
            }
        }
        else res = res + Integer.toString(g);

        if(b<100){
            if(b<10){
                res =res+"00"+Integer.toString(b);
            }
            else {
                res = res + "0" + Integer.toString(b);
            }
        }
        else res = res + Integer.toString(b);
        return res;
    }

    public String wholeMatrixString() {
        Cursor cursor = cellsDBHelper.readAllData();
        int r,g,b;
        String whole_matrix_in_string = "";

        if (cursor.getCount() == 0) {
            cellsDBHelper.createFirstData();
        } else {
            while (cursor.moveToNext()) {
                r= Integer.parseInt(cursor.getString(1));
                g=Integer.parseInt(cursor.getString(2));
                b=Integer.parseInt(cursor.getString(3));
                whole_matrix_in_string = whole_matrix_in_string + stringToPass(r,g,b);
            }
        }
        return whole_matrix_in_string;
    }


}