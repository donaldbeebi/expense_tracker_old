package edu.cuhk.csci3310_finaciallogger.game;

import android.content.res.Resources;

import java.util.ArrayList;

/**
 * optimize object later
 */

public class GameObjectManager {


    private ArrayList<ArrayList<GameObject>> m_GameObjectArray;

    /*
    public void loadGameObjects(ArrayList<ArrayList<Integer>> data, Resources res) {
        //for every type of animal
        for(int i = 0; i < data.size(); i++) {
            ArrayList<Integer> integers = data.get(i);
            int type = integers.get(1);
            int count = integers.get(2);

            //filling up each sector
            ArrayList<GameObject> gameObjects = new ArrayList<>();
            m_GameObjectArray.add(gameObjects);
            for(int j = 0; j < count ; j++) {
                //if this is the first animal in a new sector
                if(j % GameObject.MAX_NUMBER_OF_ANIMALS_PER_SECTION == 0 && j != 0) {
                    gameObjects = new ArrayList<>();
                    m_GameObjectArray.add(gameObjects);
                }
                FloatRect boundary = new FloatRect(Background.getBoundary(j / GameObject.MAX_NUMBER_OF_ANIMALS_PER_SECTION));
                gameObjects.add(new GameObject(type, boundary, res));
            }
        }
    }
    */

    public void loadGameObjects(int[] data, Resources res) {
        for (int i = 0; i < data.length; i++) {
            m_GameObjectArray = new ArrayList<>();
            ArrayList<GameObject> gameObjects = new ArrayList<>();
            m_GameObjectArray.add(gameObjects);
            for (int j = 0; j < data[i]; j++) {
                if (j % GameObject.MAX_NUMBER_OF_ANIMALS_PER_SECTION == 0 && j != 0) {
                    gameObjects = new ArrayList<>();
                    m_GameObjectArray.add(gameObjects);
                }
                FloatRect boundary = new FloatRect(Background.getBoundary(j / GameObject.MAX_NUMBER_OF_ANIMALS_PER_SECTION));
                gameObjects.add(new GameObject(i, boundary, res));
            }
        }
    }

    public ArrayList<ArrayList<GameObject>> getGameObjectArray() {
        return m_GameObjectArray;
    }
}
