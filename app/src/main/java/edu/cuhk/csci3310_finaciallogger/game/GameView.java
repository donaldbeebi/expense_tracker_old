package edu.cuhk.csci3310_finaciallogger.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class GameView extends SurfaceView implements Runnable {

    private Thread m_Thread;
    private boolean m_Running;

    private long m_TimeNow;
    private long m_TimeLast;
    private double m_DeltaTime;
    DecimalFormat m_Formatter;

    private float m_CanvasScale;
    private int m_CurrentBackground;

    private SharedPreferencesManager m_SPM;
    private CanvasCamera m_CanvasCamera;
    private BackgroundManager m_BackgroundManager;
    private GameObjectManager m_GameObjectManager;
    private UpdatableObjectManager m_UpdatableObjectManager;
    private DrawableObjectManager m_DrawableObjectManager;
    private CoinManager m_CoinManager;

    private TextView m_CoinsTextView;
    private TextView m_BucksTextView;


    //The constructor is called in onCreate() in GameActivity
    public GameView(Context context, int screenSizeX, int screenSizeY, Button leftButton, Button rightButton, TextView bucksTextView, TextView coinsTextView) {
        super(context);

        m_CurrentBackground = 0;
        m_Formatter = new DecimalFormat("#,###");

        //TODO: MARK AS INITIALIZED IS REDUNDANT??
        //m_SPM = new SharedPreferencesManager(getContext().getSharedPreferences("edu.cuhk.csci3310_finaciallogger", Context.MODE_PRIVATE));
        m_SPM = SharedPreferencesManager.getInstance();
        m_SPM.setSharedPreferences(getContext().getSharedPreferences("edu.cuhk.csci3310_finaciallogger", Context.MODE_PRIVATE));

        //DEBUG
        //int[] dummyData = new int[] { 22 };
        //ArrayList<ArrayList<Integer>> dummyData = new ArrayList<>();
        //dummyData.add(new ArrayList<>(Arrays.asList(1, 0, 22)));
        //m_SPM.saveGameObjectData(SharedPreferencesManager.convertToString(dummyData));
        //m_SPM.saveGameObjectData(dummyData);
        int[] gameObjectData = m_SPM.getGameObjectData();

        m_BackgroundManager = new BackgroundManager();
        //m_BackgroundManager.loadBackgrounds(m_SPM.getGameObjectData(), getResources());
        m_BackgroundManager.loadBackgrounds(gameObjectData, getResources());

        m_GameObjectManager = new GameObjectManager();

        m_GameObjectManager.loadGameObjects(gameObjectData, getResources());

        m_UpdatableObjectManager = new UpdatableObjectManager();
        m_UpdatableObjectManager.setGameObjects(m_GameObjectManager.getGameObjectArray());

        m_DrawableObjectManager = new DrawableObjectManager();
        m_DrawableObjectManager.setBackgrounds(m_BackgroundManager.getBackgrounds());
        m_DrawableObjectManager.setGameObjects(m_GameObjectManager.getGameObjectArray());

        m_CanvasScale = (float) screenSizeY / (float) Background.BACKGROUND_HEIGHT;
        m_CanvasCamera = new CanvasCamera(Background.getCameraPositionX(0), screenSizeX);

        //m_CoinManager = new CoinManager(m_SPM.getCoins(), m_SPM.getGameObjectData());
        m_CoinManager = CoinManager.getInstance();
        m_CoinManager.initialize(m_SPM.getCoins(), m_SPM.getGameObjectData());
        m_CoinManager.compensate(m_SPM.getTimeLastOpened());

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

        m_BucksTextView = bucksTextView;
        m_CoinsTextView = coinsTextView;
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
        m_CoinManager.update(dt);
        m_CoinsTextView.post(new Runnable() {
            @Override
            public void run() {
                m_CoinsTextView.setText(m_Formatter.format((int) m_CoinManager.getTotalNumberOfCoins()));
            }
        });
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

    public void resume(long timeLastPaused) {
        //updating the currency number
        m_CoinManager.compensate(timeLastPaused);
        m_BucksTextView.post(new Runnable() {
            @Override
            public void run() {
                m_BucksTextView.setText(m_Formatter.format(m_SPM.getBucks()));
            }
        });

        m_Running = true;
        m_Thread = new Thread(this);
        m_Thread.start();
    }

    public void pause() {
        m_SPM.saveCoinInfo(m_CoinManager.getTotalNumberOfCoins(), System.nanoTime());
        try {
            m_Running = false;
            m_Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //this is called every time it resumes from the spinning wheel transaction activity to the game activity
    public void updateGameStates() {
        int[] gameObjectData = m_SPM.getGameObjectData();
        m_BackgroundManager.loadBackgrounds(gameObjectData, getResources());
        m_GameObjectManager.loadGameObjects(gameObjectData, getResources());
        m_UpdatableObjectManager.setGameObjects(m_GameObjectManager.getGameObjectArray());
        m_DrawableObjectManager.setBackgrounds(m_BackgroundManager.getBackgrounds());
        m_DrawableObjectManager.setGameObjects(m_GameObjectManager.getGameObjectArray());
        m_CoinManager.updateAnimalNumberList(gameObjectData);
        m_BucksTextView.post(new Runnable() {
            @Override
            public void run() {
                m_BucksTextView.setText(m_Formatter.format(m_SPM.getBucks()));
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {

        }
        return true;
    }
}
