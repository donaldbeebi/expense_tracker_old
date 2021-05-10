package edu.cuhk.csci3310_finaciallogger.game;

import android.graphics.Canvas;

import java.util.ArrayList;

public class DrawableObjectManager {
    private ArrayList<Background> m_Backgrounds;
    private ArrayList<ArrayList<GameObject>> m_GameObjectArray;

    public void setBackgrounds(ArrayList<Background> backgrounds) {
        m_Backgrounds = backgrounds;
    }

    public void setGameObjects(ArrayList<ArrayList<GameObject>> gameObjectArray) {
        m_GameObjectArray = gameObjectArray;
    }

    public void draw(Canvas canvas) {
        /*
         * Drawing the backgrounds
         */
        for(Background background: m_Backgrounds) {
            background.draw(canvas);
        }
        /*
         * Drawing the objects
         */
        for(ArrayList<GameObject> array: m_GameObjectArray) {
            for(GameObject gameObject: array) {
                gameObject.draw(canvas);
            }
        }
    }
}
