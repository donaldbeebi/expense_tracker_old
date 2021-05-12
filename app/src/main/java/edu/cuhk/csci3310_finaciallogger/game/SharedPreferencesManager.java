package edu.cuhk.csci3310_finaciallogger.game;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class SharedPreferencesManager {
    //private static final SharedPreferencesManager m_Instance = new SharedPreferencesManager();
    private  SharedPreferences m_SharedPreferences;

    //public static SharedPreferencesManager getInstance() { return m_Instance; }

    //public void setSharedPreferences(SharedPreferences sharedPreferences) {
    //    m_SharedPreferences = sharedPreferences;
    //}

    SharedPreferencesManager(SharedPreferences sharedPreferences) {
        m_SharedPreferences = sharedPreferences;
    }

    /*
    public void initialize() {
        //if shared preferences has not been initialized
        if (!m_SharedPreferences.getBoolean("shared_preferences_initialized", false)) {
            m_SharedPreferences.edit().putBoolean("shared_preferences_initialized", true).apply();

            SharedPreferences.Editor editor = m_SharedPreferences.edit();

            int listSize = GameObject.TOTAL_NUMBER_OF_TYPES;
            for(int i = 0; i < listSize; i++) {
                editor.putInt(String.valueOf(i), 0);
            }
            editor.apply();
        }
    }
    */

    //has the shared preferences been used before?
    /*
    public boolean isInitialized() {
        return m_SharedPreferences.getBoolean("shared_preferences_initialized", false);
    }

    */

    /*
    //save the list of string arrays into the shared preferences
    public void saveGameObjectData(ArrayList<String[]> list) {
        SharedPreferences.Editor editor = m_SharedPreferences.edit();

        int listSize = list.size();
        int stringSize = list.get(0).length;
        for(int i = 0; i < listSize; i++) {
            String row = "";
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < stringSize; j++) {
                row = sb.append(row).append(list.get(i)[j]).toString();
                if (j != stringSize - 1) row = row + ",";
            }
            editor.putString(String.valueOf(i), row);
        }
        //storing the list size in order to loop through the list when calling "getData()"
        editor.putInt("list_size", listSize);
        editor.apply();
    }
    */

    public void saveGameObjectData(int[] data) {
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        int listSize = GameObject.TOTAL_NUMBER_OF_TYPES;
        for(int i = 0; i < listSize; i++) {
            editor.putInt(String.valueOf(i), data[i]);
        }
        editor.apply();
    }

    public int[] getGameObjectData() {
        int listSize = GameObject.TOTAL_NUMBER_OF_TYPES;
        int[] gameObjectData = new int[listSize];
        for(int i = 0; i < listSize; i++) {
            gameObjectData[i] = m_SharedPreferences.getInt(String.valueOf(i), 0);
        }
        return gameObjectData;
    }

    public void addGameObjectData(int[] data) {
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        int listSize = GameObject.TOTAL_NUMBER_OF_TYPES;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > 0) {
                int currentCount = m_SharedPreferences.getInt(String.valueOf(i), 0);
                editor.putInt(String.valueOf(i), currentCount + data[i]);
            }
        }
        editor.apply();
    }

    public void addGameObjectData(int type, int count) {
        int currentCount = m_SharedPreferences.getInt(String.valueOf(type), 0);
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putInt(String.valueOf(type), currentCount + count);
        editor.apply();
    }
    //adding game data
    //index corresponds to the type
    //value at index corresponds to the number of animals of index type
    /*
    public void addGameObject(ArrayList<Integer> gameObjectData) {
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        for(int i = 0; i < gameObjectData.size(); i++) {
            int addCount = gameObjectData.get(i);
            if(addCount != 0) {
                String[] row = m_SharedPreferences.getString(String.valueOf(i), "").split(",");
                int currentCount = Integer.parseInt(row[2]);
                row[2] = String.valueOf(currentCount + addCount);
                //turning the string array row back into one line
                String line = "";
                StringBuilder sb = new StringBuilder();
                for(int j = 0; j < row.length; j++) {
                    line = sb.append(line).append(row[j]).toString();
                    if (j != row.length - 1) line = line + ",";
                }
                editor.putString(String.valueOf(i), line);
            }
        }
        editor.apply();
    }
    */

    //obtain a list of string arrays from the shared preferences
    /*
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
    */
    public void addBucks(int amount) {
        int currentAmount = m_SharedPreferences.getInt("bucks", 0);

        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putInt("bucks", currentAmount + amount).apply();
    }

    public boolean deductBucks(int amount) {
        int currentAmount = m_SharedPreferences.getInt("bucks", 0);
        if(amount > currentAmount) {
            return false;
        }
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putInt("bucks", currentAmount - amount).apply();
        return true;
    }

    public void saveCoinInfo(float coins, long timeLastOpened) {
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putFloat("coins", coins);
        editor.putLong("time_last_opened", timeLastOpened);
        editor.apply();
    }

    public float getCoins() {
        return m_SharedPreferences.getFloat("coins", 0);
    }

    public int getBucks() {
        return m_SharedPreferences.getInt("bucks", 0);
    }

    public long getTimeLastOpened() {
        return m_SharedPreferences.getLong("time_last_opened", System.nanoTime());
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
