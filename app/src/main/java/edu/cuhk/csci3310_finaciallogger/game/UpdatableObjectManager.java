package edu.cuhk.csci3310_finaciallogger.game;

import java.util.ArrayList;

public class UpdatableObjectManager {
    private ArrayList<ArrayList<GameObject>> m_GameObjectArray;

    UpdatableObjectManager() {

    }

    public void setGameObjects(ArrayList<ArrayList<GameObject>> gameObjectArray) {
        m_GameObjectArray = gameObjectArray;
    }

    public void update(float dt) {
        for(ArrayList<GameObject> array: m_GameObjectArray) {
            for(GameObject gameObject: array) {
                gameObject.update(dt);
            }
        }
    }
}
