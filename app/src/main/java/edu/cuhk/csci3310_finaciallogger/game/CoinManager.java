package edu.cuhk.csci3310_finaciallogger.game;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.nanoTime;

public class CoinManager {
    private float m_TotalNumberOfCoins;

    private int[] m_AnimalNumberList;
    private float[] m_AnimalRateList = new float[] {
            //per minute
            10.0f    //giraffe
    };

    CoinManager(float currentCoins, int[] data) {
        m_TotalNumberOfCoins = currentCoins;
        m_AnimalNumberList = data;
    }

    public void updateData(int[] data) {
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

    public float getTotalNumberOfCoins() {
        return m_TotalNumberOfCoins;
    }
}
