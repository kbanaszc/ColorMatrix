package Interfaces;

import com.example.colormatrix.Cell;
import com.google.firebase.database.DatabaseReference;

public interface OnCellClickListener {

    void cellClick(Cell cell, DatabaseReference databaseReference);

}
