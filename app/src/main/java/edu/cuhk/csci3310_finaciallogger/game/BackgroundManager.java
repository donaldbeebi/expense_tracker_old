package edu.cuhk.csci3310_finaciallogger.game;

import android.content.res.Resources;

import java.util.ArrayList;

import edu.cuhk.csci3310_finaciallogger.BuildConfig;

public class BackgroundManager {
    private boolean m_Initialized;
    private ArrayList<Background> m_Backgrounds;

    BackgroundManager() {
        m_Initialized = false;
        m_Backgrounds = new ArrayList<>();
    }

    public void loadBackgrounds(ArrayList<ArrayList<Integer>> data, Resources res) {
        int count = 0;
        for (ArrayList<Integer> integers: data) {
            m_Backgrounds.add(new Background(integers.get(0), Background.BACKGROUND_WIDTH * count, 0, res));
            count++;
        }
    }

    public void setCurrentBackground(int currentBackground) {
        if (BuildConfig.DEBUG && !m_Initialized) {
            throw new AssertionError("Background has not been initialized!");
        }

    }

    public void addBackground(int background) {
        if (BuildConfig.DEBUG && !m_Initialized) {
            throw new AssertionError("Background has not been initialized!");
        }

    }

    public ArrayList<Background> getBackgrounds() {
        return m_Backgrounds;
    }
}
