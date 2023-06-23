package com.example.colormatrix.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.colormatrix.DBHelpers.SavesDBHelper;
import com.example.colormatrix.JSONHandler;
import Interfaces.OnSavedPixelartClickListener;
import com.example.colormatrix.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import Adapters.SavedPixelartsRecyclerAdapter;

public class YourWorksFragment extends Fragment implements OnSavedPixelartClickListener {

    RecyclerView recyclerView;
    TextView yourSavedWorks;
    SavedPixelartsRecyclerAdapter savedPixelartsRecyclerAdapter;
    SavesDBHelper savesDBHelper;
    ArrayList<String> savedPixelarts;
    JSONHandler jsonHandler;


    public YourWorksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_your_works, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        yourSavedWorks = rootView.findViewById(R.id.yourSavedWorks);
        savesDBHelper = new SavesDBHelper(getContext());
        jsonHandler = new JSONHandler(getContext());
        savedPixelarts = new ArrayList<>();
        readDataFromDB();
        savedPixelartsRecyclerAdapter = new SavedPixelartsRecyclerAdapter(savedPixelarts, this);
        recyclerView.setAdapter(savedPixelartsRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    public void readDataFromDB(){
        Cursor cursor = savesDBHelper.readAllData();
        if (cursor.getCount() == 0) {
        } else {
            while (cursor.moveToNext()) {
                savedPixelarts.add(cursor.getString(1));
            }
        }
    }

    @Override
    public void saveClicked(String s) throws JSONException, IOException {
        jsonHandler.readDataFromJson(s, getContext());
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DrawingGridFragment()).commit();
    }

    @Override
    public void saveHold(String name) {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DeleteSaveFragment(name)).commit();
    }
}


