package edu.cuhk.csci3310_finaciallogger;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import edu.cuhk.csci3310_finaciallogger.R;
import edu.cuhk.csci3310_finaciallogger.game.GameView;

public class GameActivity extends AppCompatActivity {

    private FrameLayout m_FrameLayout;
    private GameView m_GameView;
    private RelativeLayout m_GameOverlay;

    private View m_AnchorView;
    private Button m_RightButton;

    private int RIGHT_BUTTON_ID;
    private int ANCHOR_VIEW_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Preparing the frame layout
         */
        m_FrameLayout = new FrameLayout(this);
        /*
         * Setting up the game overlay
         */
        m_GameOverlay = new RelativeLayout(this);

        /*
         * Setting up the anchor view
         */
        m_AnchorView = new View(this);
        ANCHOR_VIEW_ID = View.generateViewId();
        m_AnchorView.setId(ANCHOR_VIEW_ID);
        RelativeLayout.LayoutParams anchorViewParams = new RelativeLayout.LayoutParams(0, 200);
        anchorViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
        m_AnchorView.setLayoutParams(anchorViewParams);
        m_GameOverlay.addView(m_AnchorView);

        /*
         * Setting up the right button
         */
        m_RightButton = new Button(this);
        m_RightButton.setText("Right");
        RIGHT_BUTTON_ID = View.generateViewId();
        m_RightButton.setId(RIGHT_BUTTON_ID);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl.addRule(RelativeLayout.ABOVE, ANCHOR_VIEW_ID);
        m_RightButton.setLayoutParams(rl);
        m_GameOverlay.addView(m_RightButton);

        /*
         * Setting the overview button
         */
        Button overviewButton = new Button(this);
        overviewButton.setText("Overview");

        /*
         * Setting up the game view
         */
        Rect bounds = getWindowManager().getCurrentWindowMetrics().getBounds();
        TypedValue tv = new TypedValue();

        int statusBarHeight = 0;
        int resource = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resource > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resource);
        }
        int navigationBarHeight = 0;
        resource = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if(resource > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resource);
        }
        int actionBarHeight = 0;
        if (this.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        m_GameView = new GameView(
                this,
                bounds.width(),
                bounds.height() - actionBarHeight - statusBarHeight - navigationBarHeight,
                m_RightButton,
                getSharedPreferences("edu.cuhk.csci3310_finaciallogger", MODE_PRIVATE)
        );

        /*
         * Setting up the frame layout
         */
        m_FrameLayout.addView(m_GameView);
        m_FrameLayout.addView(m_GameOverlay);
        setContentView(m_FrameLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        m_GameView.pause();
    }

    protected void onResume() {
        super.onResume();
        m_GameView.resume();
        Log.d("GameActivity", "onResume");
    }
}