package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colormatrix.Device;
import Interfaces.OnDeviceClickListener;
import com.example.colormatrix.R;
import com.example.colormatrix.Singletons.DeviceInUse;

import java.util.ArrayList;

public class DevicesRecyclerAdapter extends RecyclerView.Adapter<DevicesRecyclerAdapter.DevicesViewHolder> {

    private OnDeviceClickListener listener;
    private ArrayList<Device> devices;
    DeviceInUse deviceInUse;
    private Context context;
    private String daviceFormSP;

    public DevicesRecyclerAdapter(Context context, ArrayList<Device> devices, OnDeviceClickListener listener) {
        this.devices = devices;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public DevicesRecyclerAdapter.DevicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.avilable_devices, parent, false);
        return new DevicesRecyclerAdapter.DevicesViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DevicesRecyclerAdapter.DevicesViewHolder holder, int position) {
        holder.bind(devices.get(position));
    }

    @Override
    public int getItemCount() {return devices.size();}

    public class DevicesViewHolder extends RecyclerView.ViewHolder{
        TextView deviceName;
        ImageView imageView;
        public DevicesViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.device_name);
            imageView = itemView.findViewById(R.id.imageView);

            SharedPreferences sharedPreferences = context.getSharedPreferences("Devices", Context.MODE_PRIVATE);
            daviceFormSP = sharedPreferences.getString("device","");
            deviceInUse = DeviceInUse.getInstance();
            deviceInUse.setDeviceName(daviceFormSP);
        }

        void bind(final Device device){
            deviceName.setText(device.getName());
            if(deviceInUse.getDeviceName().equals(device.getName())){
                deviceName.setTextColor(Color.parseColor("#0FF000"));
                imageView.setColorFilter(Color.parseColor("#0FF000"));
            }
            else{
                deviceName.setTextColor(Color.parseColor("#616161"));
                imageView.setColorFilter(Color.parseColor("#616161"));
            }
            itemView.setOnClickListener(v->{
                listener.deviceClicked(device.getName());
                notifyDataSetChanged();
            });
        }
    }
}
