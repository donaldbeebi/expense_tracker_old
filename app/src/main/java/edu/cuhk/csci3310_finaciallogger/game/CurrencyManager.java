package edu.cuhk.csci3310_finaciallogger.game;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.nanoTime;

public class CurrencyManager {
    private static final int TOTAL_NUMBER_OF_TYPES = 1;
    private static final int GIRAFFE_INDEX = 0;

    private float m_TotalNumberOfCoins;

    private int[] m_AnimalNumberList = new int[TOTAL_NUMBER_OF_TYPES];
    private float[] m_AnimalRateList = new float[] {
            //per minute
            10.0f    //giraffe
    };

    CurrencyManager(float currentCoins, ArrayList<ArrayList<Integer>> data) {
        m_TotalNumberOfCoins = currentCoins;
        Arrays.fill(m_AnimalNumberList, 0);
        for(int i = 0; i < data.size(); i++) {
            m_AnimalNumberList[data.get(i).get(1)] += data.get(i).get(2);
        }
    }

    public void update(float dt) {
        for(int i = 0; i < TOTAL_NUMBER_OF_TYPES; i++) {
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
