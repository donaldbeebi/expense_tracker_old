package edu.cuhk.csci3310_finaciallogger.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

public class GameView extends SurfaceView implements Runnable {

    private Thread m_Thread;
    private boolean m_Running;

    private long m_TimeNow;
    private long m_TimeLast;
    private double m_DeltaTime;

    private float m_CanvasScale;
    private int m_CurrentBackground;

    private SharedPreferencesManager m_SPM;
    private CanvasCamera m_CanvasCamera;
    private BackgroundManager m_BackgroundManager;
    private GameObjectManager m_GameObjectManager;
    private UpdatableObjectManager m_UpdatableObjectManager;
    private DrawableObjectManager m_DrawableObjectManager;

    //The constructor is called in onCreate() in GameActivity
    public GameView(Context context, int screenSizeX, int screenSizeY, Button leftButton, Button rightButton, SharedPreferences sharedPreferences) {
        super(context);

        m_CurrentBackground = 0;

        m_SPM = new SharedPreferencesManager(sharedPreferences);
        //DEBUG
        ArrayList<ArrayList<Integer>> dummyData = new ArrayList<>();
        dummyData.add(new ArrayList<>(Arrays.asList(1, 0, 5)));
        dummyData.add(new ArrayList<>(Arrays.asList(1, 0, 2)));
        dummyData.add(new ArrayList<>(Arrays.asList(1, 0, 4)));
        dummyData.add(new ArrayList<>(Arrays.asList(1, 0, 0)));
        m_SPM.saveData(SharedPreferencesManager.convertToString(dummyData));

        m_BackgroundManager = new BackgroundManager();
        m_BackgroundManager.loadBackgrounds(SharedPreferencesManager.convertToInt(m_SPM.getData()), getResources());

        m_GameObjectManager = new GameObjectManager();

        m_GameObjectManager.loadGameObjects(SharedPreferencesManager.convertToInt(m_SPM.getData()), getResources());

        m_UpdatableObjectManager = new UpdatableObjectManager();
        m_UpdatableObjectManager.setGameObjects(m_GameObjectManager.getGameObjectArray());

        m_DrawableObjectManager = new DrawableObjectManager();
        m_DrawableObjectManager.setBackgrounds(m_BackgroundManager.getBackgrounds());
        m_DrawableObjectManager.setGameObjects(m_GameObjectManager.getGameObjectArray());

        m_CanvasScale = (float) screenSizeY / (float) Background.BACKGROUND_HEIGHT;
        m_CanvasCamera = new CanvasCamera(Background.getCameraPositionX(0), screenSizeX);

        leftButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_CanvasCamera.setCameraTargetPosition(Background.getCameraPositionX(--m_CurrentBackground));
            }
        });

        rightButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_CanvasCamera.setCameraTargetPosition(Background.getCameraPositionX(++m_CurrentBackground));
            }
        });
    }

    @Override
    public void run() {
        m_TimeLast = System.nanoTime();

        //the main game loop
        while(m_Running) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        m_TimeNow = System.nanoTime();
        m_DeltaTime = (m_TimeNow - m_TimeLast) / 1000000000.0d;
        m_TimeLast = m_TimeNow;
        float dt = (float) m_DeltaTime;

        m_UpdatableObjectManager.update(dt);
        m_CanvasCamera.update(dt);
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.translate(m_CanvasCamera.getCanvasPositionX(), 0.0f);
            canvas.scale(m_CanvasScale, m_CanvasScale, m_CanvasCamera.getPivotPositionX(), 0.0f);

            m_DrawableObjectManager.draw(canvas);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        m_Running = true;
        m_Thread = new Thread(this);
        m_Thread.start();
    }

    public void pause() {
        try {
            m_Running = false;
            m_Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {

        }
        return true;
    }
}
