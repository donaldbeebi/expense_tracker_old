package edu.cuhk.csci3310_finaciallogger.game;

import android.content.res.Resources;

import java.util.ArrayList;

import edu.cuhk.csci3310_finaciallogger.BuildConfig;

public class BackgroundManager {
    private boolean m_Initialized;
    private ArrayList<Background> m_Backgrounds;
    private int m_CurrentBackground;

    BackgroundManager() {
        m_Initialized = false;
        m_Backgrounds = new ArrayList<>();
        m_CurrentBackground = 0;
    }

    public void loadBackgrounds(int[] backgrounds, Resources res) {
        int count = 0;
        for (int background : backgrounds) {
            m_Backgrounds.add(new Background(background, Background.BACKGROUND_WIDTH * count, 0, res));
            count++;
        }
    }
    /*
    for (ArrayList<Integer> integers: data) {
        for (int integer: integers) {
            m_Backgrounds.add(new Background(integer, Background.BACKGROUND_WIDTH * count, 0, res));
            count++;
        }
    }

     */

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
