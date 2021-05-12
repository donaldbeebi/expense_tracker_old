package edu.cuhk.csci3310_finaciallogger.game;

import android.content.res.Resources;

import java.util.ArrayList;

import edu.cuhk.csci3310_finaciallogger.BuildConfig;

public class BackgroundManager {
    private ArrayList<Background> m_Backgrounds;

    /*
    public void loadBackgrounds(ArrayList<ArrayList<Integer>> data, Resources res) {
        int backgroundPosition = 0;
        for (ArrayList<Integer> integers: data) {
            int animalCount = integers.get(2);
            while(animalCount > 0) {
                m_Backgrounds.add(new Background(integers.get(0), Background.BACKGROUND_WIDTH * backgroundPosition, 0, res));
                backgroundPosition++;
                animalCount -= 10;
            }
        }
    }
    */

    public void loadBackgrounds(int[] data, Resources res) {
        int backgroundPosition = 0;
        m_Backgrounds = new ArrayList<>();
        for(int i = 0; i < data.length; i++) {
            int animalCount = data[i];
            while(animalCount > 0) {
                m_Backgrounds.add(new Background(0, Background.BACKGROUND_WIDTH * backgroundPosition, 0, res));
                backgroundPosition++;
                animalCount -= 10;
            }
        }
    }

    public ArrayList<Background> getBackgrounds() {
        return m_Backgrounds;
    }
}
