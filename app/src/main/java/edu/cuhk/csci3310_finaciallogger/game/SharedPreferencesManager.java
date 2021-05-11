package edu.cuhk.csci3310_finaciallogger.game;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesManager {
    private final SharedPreferences m_SharedPreferences;

    SharedPreferencesManager(SharedPreferences sharedPreferences) {
        m_SharedPreferences = sharedPreferences;
    }

    //has the shared preferences been used before?
    public boolean hasGameData() {
        return !m_SharedPreferences.getBoolean("hasGameData", false);
    }

    //save the list of string arrays into the shared preferences
    public void saveData(ArrayList<String[]> list) {
        SharedPreferences.Editor editor = m_SharedPreferences.edit();

        int listSize = list.size();
        int stringSize = list.get(0).length;
        for(int i = 0; i < listSize; i++) {
            String row = "";
            for(int j = 0; j < stringSize; j++) {
                StringBuilder sb = new StringBuilder();
                row = sb.append(row).append(list.get(i)[j]).toString();
                if (j != stringSize - 1) row = row + ",";
            }
            editor.putString(String.valueOf(i), row);
        }
        //storing the list size in order to loop through the list when calling "getData()"
        editor.putInt("listSize", listSize);
        if(hasGameData()) {
            //marking that shared preferences has been used
            editor.putBoolean("hasGameData", true);
        }
        editor.apply();
    }

    //obtain a list of string arrays from the shared preferences
    public ArrayList<String[]> getData() {
        if(!hasGameData()) {
            ArrayList<String[]> resultList = new ArrayList<>();
            int listSize = m_SharedPreferences.getInt("listSize", 0);
            for (int i = 0; i < listSize; i++) {
                String[] row = m_SharedPreferences.getString(String.valueOf(i), "").split(",");
                resultList.add(row);
            }
            return resultList;
        }
        return new ArrayList<>();
    }

    public static ArrayList<ArrayList<Integer>> convertToInt(ArrayList<String[]> data) {
        ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
        for(String[] strings: data) {
            ArrayList<Integer> integers = new ArrayList<>();
            for(String string: strings) {
                integers.add(Integer.valueOf(string));
            }
            resultList.add(integers);
        }
        return resultList;
    }

    public static ArrayList<String[]> convertToString(ArrayList<ArrayList<Integer>> data) {
        ArrayList<String[]> resultList = new ArrayList<>();
        for(ArrayList<Integer> integers: data) {
            String[] strings = new String[integers.size()];
            for(int i = 0; i < integers.size(); i++) {
                strings[i] = String.valueOf(integers.get(i));
            }
            resultList.add(strings);
        }
        return resultList;
    }
}
