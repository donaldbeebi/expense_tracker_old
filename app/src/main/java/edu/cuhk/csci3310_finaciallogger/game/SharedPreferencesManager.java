package edu.cuhk.csci3310_finaciallogger.game;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class SharedPreferencesManager {
    private static final SharedPreferencesManager m_Instance = new SharedPreferencesManager();
    private  SharedPreferences m_SharedPreferences;

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        m_SharedPreferences = sharedPreferences;
    }

    public static SharedPreferencesManager getInstance() {
        return m_Instance;
    }

    //public SharedPreferencesManager(SharedPreferences sharedPreferences) {
    //    m_SharedPreferences = sharedPreferences;
    //}

    public int[] getGameObjectData() {
        int listSize = GameObject.TOTAL_NUMBER_OF_TYPES;
        int[] gameObjectData = new int[listSize];
        for(int i = 0; i < listSize; i++) {
            gameObjectData[i] = m_SharedPreferences.getInt(String.valueOf(i), 0);
        }
        return gameObjectData;
    }

    public void addGameObjectData(int type, int count) {
        int currentCount = m_SharedPreferences.getInt(String.valueOf(type), 0);
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putInt(String.valueOf(type), currentCount + count);
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

    public boolean[] getWheelPurchaseStates() {
        int listSize = Background.HABITAT_TYPES.length;
        boolean[] wheelPurchaseStates = new boolean[listSize];
        for(int i = 0; i < listSize; i++) {
            boolean defaultState = false;
            if(i == 0) defaultState = true;
            wheelPurchaseStates[i] = m_SharedPreferences.getBoolean("wheel_" + i, defaultState);
        }
        return wheelPurchaseStates;
    }

    public void updateWheelPurchaseState(int wheelToBuy) {
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putBoolean("wheel_" + wheelToBuy, true);
        editor.apply();
    }

    public void saveWheelPurchaseState(boolean[] wheelPurchaseStates) {
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        for(int i = 0; i < wheelPurchaseStates.length; i++) {
            editor.putBoolean("wheel_" + i, wheelPurchaseStates[i]);
        }
        editor.apply();
    }

}
