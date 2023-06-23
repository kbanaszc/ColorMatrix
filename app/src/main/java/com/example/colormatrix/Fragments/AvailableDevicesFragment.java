package com.example.colormatrix.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.colormatrix.Device;
import Interfaces.OnDeviceClickListener;
import com.example.colormatrix.R;
import com.example.colormatrix.Singletons.DeviceInUse;

import java.util.ArrayList;

import Adapters.DevicesRecyclerAdapter;


public class AvailableDevicesFragment extends Fragment implements OnDeviceClickListener {

    RecyclerView recyclerView;
    TextView avilableDevicesText;
    DevicesRecyclerAdapter devicesRecyclerAdapter;
    ArrayList<Device> devices;
    DeviceInUse deviceInUse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_available_devices, container, false);
        devices = new ArrayList<>();
        devices.add(new Device(false,"Frame_1"));
        devices.add(new Device(false,"Frame_2"));
        recyclerView = rootView.findViewById(R.id.recycler_view_devices);
        avilableDevicesText = rootView.findViewById(R.id.avilable_devices_text);
        devicesRecyclerAdapter = new DevicesRecyclerAdapter(getContext(),devices,this);
        recyclerView.setAdapter(devicesRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void deviceClicked(String deviceName) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Devices", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("device", deviceName);
        myEdit.apply();
        deviceInUse = DeviceInUse.getInstance();
        deviceInUse.setDeviceName(deviceName);
    }
}