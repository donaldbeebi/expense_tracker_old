package edu.cuhk.csci3310_finaciallogger.game;

import android.content.res.Resources;

import java.util.ArrayList;

/**
 * optimize object later
 */

public class GameObjectManager {
    public static final int TOTAL_NUMBER_OF_TYPES = 1;
    public static final int GIRAFFE_INDEX = 0;

    private ArrayList<ArrayList<GameObject>> m_GameObjectArray;

    GameObjectManager() {
        m_GameObjectArray = new ArrayList<>();
    }

    public void loadGameObjects(ArrayList<ArrayList<Integer>> data, Resources res) {
        for(int i = 0; i < data.size(); i++) {
            ArrayList<Integer> integers = data.get(i);
            ArrayList<GameObject> gameObjects = new ArrayList<>();
            int type = integers.get(1);
            int count = integers.get(2);
            for(int j = 0; j < count; j++) {
                FloatRect boundary = new FloatRect(Background.getBoundary(i));
                gameObjects.add(new GameObject(type, boundary, res));
            }
            m_GameObjectArray.add(gameObjects);
        }
    }

    public ArrayList<ArrayList<GameObject>> getGameObjectArray() {
        return m_GameObjectArray;
    }
}
