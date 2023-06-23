package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colormatrix.R;
import Interfaces.OnSavedPixelartClickListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class SavedPixelartsRecyclerAdapter extends RecyclerView.Adapter<SavedPixelartsRecyclerAdapter.SavedPixelartsViewHolder> {

    private ArrayList savedPixelarts;
    private OnSavedPixelartClickListener listener;

    public SavedPixelartsRecyclerAdapter(ArrayList savedPixelarts, OnSavedPixelartClickListener listener) {
        this.savedPixelarts = savedPixelarts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedPixelartsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_pixelart, parent, false);
        return new SavedPixelartsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPixelartsViewHolder holder, int position) {
        holder.savedPixelartName.setText(String.valueOf(savedPixelarts.get(position)));
        holder.saved_pixelart.setOnClickListener(view -> {
            try {
                listener.saveClicked(String.valueOf(savedPixelarts.get(position)));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        holder.saved_pixelart.setOnLongClickListener(v->{listener.saveHold(String.valueOf(savedPixelarts.get(position))); return true;});
    }

    @Override
    public int getItemCount() {
        return savedPixelarts.size();
    }

    class SavedPixelartsViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout saved_pixelart;
        TextView savedPixelartName;
        public SavedPixelartsViewHolder(@NonNull View itemView) {
            super(itemView);
            saved_pixelart = itemView.findViewById(R.id.saved_pixelart);
            savedPixelartName = itemView.findViewById(R.id.savedPixelartName);
        }
    }
 }
