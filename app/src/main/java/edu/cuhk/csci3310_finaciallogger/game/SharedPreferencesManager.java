package edu.cuhk.csci3310_finaciallogger.game;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class SharedPreferencesManager {
    private static final SharedPreferencesManager m_Instance = new SharedPreferencesManager();
    private  SharedPreferences m_SharedPreferences;

    public static SharedPreferencesManager getInstance() { return m_Instance; }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        m_SharedPreferences = sharedPreferences;
    }

    public void markAsInitialized() {
        m_SharedPreferences.edit().putBoolean("shared_preferences_initialized", true).apply();
    }

    //has the shared preferences been used before?
    public boolean isInitialized() {
        return m_SharedPreferences.getBoolean("shared_preferences_initialized", false);
    }

    //save the list of string arrays into the shared preferences
    public void saveGameObjectData(ArrayList<String[]> list) {
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
        editor.putInt("list_size", listSize);
        editor.apply();
    }

    //obtain a list of string arrays from the shared preferences
    public ArrayList<String[]> getGameObjectData() {
        if(isInitialized()) {
            ArrayList<String[]> resultList = new ArrayList<>();
            int listSize = m_SharedPreferences.getInt("list_size", 0);
            for (int i = 0; i < listSize; i++) {
                String[] row = m_SharedPreferences.getString(String.valueOf(i), "").split(",");
                resultList.add(row);
            }
            return resultList;
        }
        return new ArrayList<>();
    }

    public void saveCurrencyInfo(int coins, int bucks, long timeLastOpened) {
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putInt("coins", coins);
        editor.putInt("bucks", bucks);
        editor.putLong("time_last_opened", timeLastOpened);
        editor.apply();
    }

    public int getCoins() {
        return m_SharedPreferences.getInt("coins", -1);
    }

    public int getBucks() {
        return m_SharedPreferences.getInt("bucks", -1);
    }

    public long getTimeLastOpened() {
        return m_SharedPreferences.getLong("time_last_opened", -1);
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
