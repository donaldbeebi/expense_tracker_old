package edu.cuhk.csci3310_finaciallogger.game;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.nanoTime;

public class CoinManager {
    private static final CoinManager m_Instance = new CoinManager();

    public static final float[] HABITAT_PRICES = new float[] {
            0.0f, //savanna
            1_000.0f, //safari
            1_000_000.0f //wetland
    };

    private float m_TotalNumberOfCoins;
    private int[] m_AnimalNumberList;
    private static final float[] m_AnimalRateList = new float[] {
            //per minute
            10.0f    //giraffe
    };

    //CoinManager(float currentCoins, int[] data) {
    //    m_TotalNumberOfCoins = currentCoins;
    //    m_AnimalNumberList = data;
    //}

    public static CoinManager getInstance() {
        return m_Instance;
    }

    public void initialize(float currentCoins, int[] data) {
        m_TotalNumberOfCoins = currentCoins;
        m_AnimalNumberList = data;
    }

    public void updateAnimalNumberList(int[] data) {
        m_AnimalNumberList = data;
    }

    public void update(float dt) {
        for(int i = 0; i < GameObject.TOTAL_NUMBER_OF_TYPES; i++) {
            m_TotalNumberOfCoins += (float) m_AnimalNumberList[i] * ((m_AnimalRateList[i] / 60.0f) * dt);
        }
    }

    public void compensate(long timeLastPaused) {
        double dt = (System.nanoTime() - timeLastPaused) / 1000000000.0d;
        update((float) dt);
    }

    public boolean deduct(float amount) {
        if(amount > m_TotalNumberOfCoins) {
            return false;
        }
        m_TotalNumberOfCoins -= amount;
        return true;
    }

    public float getTotalNumberOfCoins() {
        return m_TotalNumberOfCoins;
    }
}
