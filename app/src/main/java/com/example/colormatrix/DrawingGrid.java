package com.example.colormatrix;

import java.util.ArrayList;
import java.util.List;

public class DrawingGrid {

  static int gridSize = 64;
  private List<Cell> cells;

  public DrawingGrid(){
    cells = new ArrayList<>();
    for( int i=0; i<gridSize; i++){
      cells.add(new Cell(i,122,122,122));
    }
  }

  public List<Cell> getCells() {
    return cells;
  }
}
