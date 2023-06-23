package com.example.colormatrix;

import android.content.Context;
import android.database.Cursor;

import com.example.colormatrix.DBHelpers.CellsDBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONHandler {
    CellsDBHelper cellsDBHelper;
    ArrayList<String> id_arr, red_arr, green_arr, blue_arr;
    Context context;

    public JSONHandler(Context context) {
        cellsDBHelper = new CellsDBHelper(context);

    }

    public void writeToJsonFile(String fileName, Context context) throws JSONException, IOException {
        id_arr = new ArrayList<>();
        red_arr = new ArrayList<>();
        green_arr = new ArrayList<>();
        blue_arr = new ArrayList<>();
        readDataFromDatabase();
        JSONObject jsonObject = new JSONObject();
        for(int i=0 ; i<id_arr.size();i++){
            jsonObject.put(id_arr.get(i)+"red", red_arr.get(i));
            jsonObject.put(id_arr.get(i)+"green", green_arr.get(i));
            jsonObject.put(id_arr.get(i)+"blue", blue_arr.get(i));
        }
        String dataToSave = jsonObject.toString();
        try {
            File file = new File(context.getFilesDir(), fileName+".json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataToSave);
            bufferedWriter.close();
        }
        catch (Exception e){
            System.out.println("Something went wrong.");
        }
    }

    public void readDataFromJson(String fileName, Context context ) throws IOException, JSONException {
        File file = new File(context.getFilesDir(),fileName+".json");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        // This responce will have Json Format String
        String responce = stringBuilder.toString();

        JSONObject jsonObject  = new JSONObject(responce);

        String red = "" , green = "", blue = "";
        for(int i=1; i<=64; i++){
            red = jsonObject.get(Integer.toString(i)+"red").toString();
            green = jsonObject.get(Integer.toString(i)+"green").toString();
            blue = jsonObject.get(Integer.toString(i)+"blue").toString();
            cellsDBHelper.updateData(Integer.toString(i),Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
        }
    }

    public void readDataFromDatabase() {
        Cursor cursor = cellsDBHelper.readAllData();

        if (cursor.getCount() == 0) {
            cellsDBHelper.createFirstData();
        } else {
            while (cursor.moveToNext()) {
                id_arr.add(cursor.getString(0));
                red_arr.add(cursor.getString(1));
                green_arr.add(cursor.getString(2));
                blue_arr.add(cursor.getString(3));
            }
        }
    }

}
