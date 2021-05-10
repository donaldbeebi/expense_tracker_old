package edu.cuhk.csci3310_finaciallogger.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import edu.cuhk.csci3310_finaciallogger.R;

public class GameObject implements DrawableObject, UpdatableObject {
    private static final float MAX_DEGREE = 10.0f;
    private static final float ROTATION_SPEED = 60.0f;
    private static final float SPEED = 100.0f;

    private Bitmap m_Bitmap;

    private float m_PositionX;
    private float m_PositionY;
    private float m_VelocityX;
    private float m_VelocityY;

    private float m_Direction;
    private float m_Rotation;
    private boolean m_RotatingRight;
    private boolean m_InMotion;
    private float m_ScaleX;

    private int m_CurrentStepNumber;
    private int m_MaxStepNumber;

    private float m_CurrentIdleTime;
    private float m_MaxIdleTime;

    private FloatRect m_Boundary;
    private int m_Location;

    GameObject(int type, int positionX, int positionY, FloatRect boundary, int location, Resources res) {
        m_Bitmap = BitmapFactory.decodeResource(res, R.drawable.object_spritesheet);
        m_Bitmap = Bitmap.createBitmap(m_Bitmap);

        m_PositionX = positionX;
        m_PositionY = positionY;
        m_VelocityX = 0.0f;
        m_VelocityY = 0.0f;

        m_Rotation = 0.0f;
        m_RotatingRight = true;
        m_InMotion = false;
        if(Math.random() > 0.5d) m_ScaleX = 1.0f;
        else m_ScaleX = -1.0f;

        m_Boundary = new FloatRect(boundary.left, boundary.top - m_Bitmap.getHeight() / 2.0f, boundary.right - m_Bitmap.getWidth(), boundary.bottom - m_Bitmap.getHeight());
        m_Location = location;

        randomize();
    }

    private void randomize() {
        /*
         * Randomizing the walking direction
         */
        m_Direction = (float) (Math.random() * 360.0d);
        m_VelocityX = SPEED * (float) Math.cos(Math.toRadians(m_Direction));
        m_VelocityY = SPEED * (float) Math.sin(Math.toRadians(m_Direction));

        /*
         * Randomizing the number of steps to take
         */
        m_MaxStepNumber = (int) (Math.random() * 9.0d) + 1;

        /*
         * Randomizing the idle time
         */
        m_MaxIdleTime = (float) (Math.random() * 10.0d);
    }

    public int getLocation() {
        return m_Location;
    }

    @Override
    public void update(float dt) {
        if(m_InMotion) {
            //face the correct direction
            if(m_VelocityX > 0.0f) m_ScaleX = 1.0f;
            else m_ScaleX = -1.0f;
            if(m_RotatingRight) {
                //counting the number of steps
                if(m_Rotation < 0.0f && m_Rotation + ROTATION_SPEED * dt > 0.0f) m_CurrentStepNumber++;
                //time to go idle
                if(m_CurrentStepNumber == m_MaxStepNumber) {
                    m_InMotion = false;
                    m_CurrentStepNumber = 0;
                    m_Rotation = 0.0f;
                    randomize();
                }
                //time to swing to the right
                else {
                    m_Rotation += ROTATION_SPEED * dt;
                    if(m_Rotation > MAX_DEGREE) {
                        float excess = m_Rotation - MAX_DEGREE;
                        m_RotatingRight = false;
                        m_Rotation = MAX_DEGREE - excess;
                    }
                }
            }
            //time to swing to the left
            else {
                m_Rotation -= ROTATION_SPEED * dt;
                if(m_Rotation < -MAX_DEGREE) {
                    float excess = -(MAX_DEGREE + m_Rotation);
                    m_RotatingRight = true;
                    m_Rotation = -MAX_DEGREE + excess;
                }
            }
            //strolling around while detecting collision
            m_PositionX = Math.max(m_Boundary.left, Math.min(m_Boundary.right, m_PositionX + m_VelocityX * dt));
            m_PositionY = Math.max(m_Boundary.top, Math.min(m_Boundary.bottom, m_PositionY + m_VelocityY * dt));
        }
        //idling
        if(!m_InMotion) {
            m_CurrentIdleTime += dt;
            if(m_CurrentIdleTime > m_MaxIdleTime) {
                m_CurrentIdleTime = 0.0f;
                m_InMotion = true;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.setRotate(m_Rotation, m_Bitmap.getWidth() / 2.0f, m_Bitmap.getHeight());
        matrix.postScale(m_ScaleX, 1.0f, m_Bitmap.getWidth() / 2.0f, m_Bitmap.getHeight());
        matrix.postTranslate(m_PositionX, m_PositionY);
        canvas.drawBitmap(m_Bitmap, matrix, null);
    }
}
