package Adapters;

import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colormatrix.Cell;
import com.example.colormatrix.DBHelpers.CellsDBHelper;
import Interfaces.OnCellClickListener;
import com.example.colormatrix.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DrawingGridRecyclerAdapter extends RecyclerView.Adapter<DrawingGridRecyclerAdapter.DrawingTileViewHolder> {
    private List<Cell> cells;
    private OnCellClickListener listener;
    private ArrayList<Integer> id_arr, red_arr, green_arr, blue_arr;
    private DatabaseReference databaseReference;
    private CellsDBHelper cellsDBHelper;

    public DrawingGridRecyclerAdapter(List<Cell> cells, OnCellClickListener listener) {
        this.cells = cells;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DrawingTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);

        cellsDBHelper = new CellsDBHelper(parent.getContext());
        id_arr = new ArrayList<>();
        red_arr = new ArrayList<>();
        green_arr = new ArrayList<>();
        blue_arr = new ArrayList<>();
        readDataFromDatabase();
        return new DrawingTileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawingTileViewHolder holder, int position) {
        holder.bind(cells.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    class DrawingTileViewHolder extends RecyclerView.ViewHolder {
          LinearLayout item_cell;

        public DrawingTileViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cell = itemView.findViewById(R.id.item_cell);
        }

        public void bind(final Cell cell) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("device_1");
            databaseReference = databaseReference.child(Integer.toString(cell.getIndex()));
            int r = red_arr.get(cell.getIndex());
            int g = green_arr.get(cell.getIndex());
            int b = blue_arr.get(cell.getIndex());
            cell.setRed(r);
            cell.setGreen(g);
            cell.setBlue(b);
            databaseReference.setValue(stringToPass(r, g, b));
            itemView.setBackgroundColor(Color.argb(255, r, g, b));
            itemView.setOnClickListener(view -> listener.cellClick(cell, databaseReference));
        }
    }

    public void readDataFromDatabase() {
        Cursor cursor = cellsDBHelper.readAllData();

        if (cursor.getCount() == 0) {
            cellsDBHelper.createFirstData();
        } else {
            while (cursor.moveToNext()) {
                id_arr.add(Integer.parseInt(cursor.getString(0)));
                red_arr.add(Integer.parseInt(cursor.getString(1)));
                green_arr.add(Integer.parseInt(cursor.getString(2)));
                blue_arr.add(Integer.parseInt(cursor.getString(3)));
            }
        }
    }

    public String stringToPass(int r, int g, int b) {
        String res = "";
        if (r < 100) {
            if (r < 10) {
                res = res + "00" + Integer.toString(r);
            } else {
                res = res + "0" + Integer.toString(r);
            }
        } else res = res + Integer.toString(r);

        if (g < 100) {
            if (g < 10) {
                res = res + "00" + Integer.toString(g);
            } else {
                res = res + "0" + Integer.toString(g);
            }
        } else res = res + Integer.toString(g);

        if (b < 100) {
            if (b < 10) {
                res = res + "00" + Integer.toString(b);
            } else {
                res = res + "0" + Integer.toString(b);
            }
        } else res = res + Integer.toString(b);
        return res;
    }

    }
