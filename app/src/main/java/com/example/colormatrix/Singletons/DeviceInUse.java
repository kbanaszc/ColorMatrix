package com.example.colormatrix.Singletons;

public class DeviceInUse {

    private static DeviceInUse instance;
    private String deviceName = "";

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public static DeviceInUse getInstance(){
        if(instance==null){
            instance = new DeviceInUse();
        }
        return instance;
    }
}
