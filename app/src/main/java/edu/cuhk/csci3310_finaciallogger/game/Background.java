package edu.cuhk.csci3310_finaciallogger.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import edu.cuhk.csci3310_finaciallogger.R;

public class Background implements DrawableObject {
    public static final int BACKGROUND_WIDTH = 480;
    public static final int BACKGROUND_HEIGHT = 1056;
    public static final String[] HABITAT_TYPES = new String[] { "savanna", "safari", "wetland" };

    public static FloatRect getBoundary(int index) {
        return new FloatRect(index * BACKGROUND_WIDTH + 48.0f, 48.0f, index * BACKGROUND_WIDTH + 431.0f, 767.0f);
    }

    public static int getCameraPositionX(int index) {
        return index * BACKGROUND_WIDTH + BACKGROUND_WIDTH / 2;
    }

    private int m_PositionX, m_PositionY;
    private Bitmap m_Bitmap;

    Background(int type, int positionX, int positionY, Resources res) {
        m_PositionX = positionX;
        m_PositionY = positionY;

        switch(type) {
            case 0:
                m_Bitmap = BitmapFactory.decodeResource(res, R.drawable.background_1);
                break;
            case 1:
                m_Bitmap = BitmapFactory.decodeResource(res, R.drawable.background_2);
                break;
        }
        m_Bitmap = Bitmap.createBitmap(m_Bitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(m_Bitmap, m_PositionX, m_PositionY, null);
    }

}
