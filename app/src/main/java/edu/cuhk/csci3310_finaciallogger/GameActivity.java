package edu.cuhk.csci3310_finaciallogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;

import edu.cuhk.csci3310_finaciallogger.R;
import edu.cuhk.csci3310_finaciallogger.game.GameView;

public class GameActivity extends AppCompatActivity {

    private Boolean m_FirstTime;
    private HashMap<String, LinkedList<String>> m_Data;
    private static String PRESET_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset";
    private static String RECORD_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record";

    private FrameLayout m_FrameLayout;
    private GameView m_GameView;
    private RelativeLayout m_GameOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting up m_Data
        m_Data = new HashMap<>();
        m_Data.put("preset_list", new LinkedList<>());
        m_Data.put("preset_list_amount", new LinkedList<>());
        m_Data.put("preset_list_category", new LinkedList<>());
        m_Data.put("record_item", new LinkedList<>());
        m_Data.put("record_amount", new LinkedList<>());
        m_Data.put("record_date", new LinkedList<>());
        m_Data.put("record_category", new LinkedList<>());

        //Loading data
        File file = new File(PRESET_FILE_PATH);
        InputStream isd = null;
        InputStream isdr = null;

        if (file.exists()) {
            try {
                m_FirstTime = false;
                isd = new FileInputStream(PRESET_FILE_PATH);
                isdr= new FileInputStream(RECORD_FILE_PATH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            m_FirstTime = true;
            isd = getResources().openRawResource(R.raw.preset);
            isdr = getResources().openRawResource(R.raw.record);
        }

        //Reading from input stream for creating the preset list in the Logging Activity
        BufferedReader br = new BufferedReader(new InputStreamReader(isd, Charset.forName("UTF-8")));
        BufferedReader brr = new BufferedReader(new InputStreamReader(isdr, Charset.forName("UTF-8")));
        //for creating file to save info for first time open the app
        FileOutputStream fos = null;
        FileOutputStream fosd = null;
        FileOutputStream fosr = null;
        FileOutputStream fosrd = null;
        String linewrite = "";
        String linewriter = "";

        //creating files for preset options
        try {
            if (m_FirstTime) {
                fos = openFileOutput("preset", MODE_PRIVATE);
                fosd = openFileOutput("preset_dup", MODE_PRIVATE);
            }
            while (((linewrite = br.readLine()) != null)) {
                String output = linewrite + "\n";
                String[] store = linewrite.split(",");
                if (m_FirstTime) {
                    fos.write(output.getBytes());
                    fosd.write(output.getBytes());
                }

                m_Data.get("preset_list").addLast(store[0]);
                m_Data.get("preset_list_amount").addLast(store[1]);
                m_Data.get("preset_list_category").addLast(store[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creating local saving for logging record
        try {
            if (m_FirstTime) {
                fosr = openFileOutput("record", MODE_PRIVATE);
                fosrd = openFileOutput("record_dup", MODE_PRIVATE);
            }
            while (((linewriter = brr.readLine()) != null)) {
                String output = linewriter + "\n";
                String[] store = linewriter.split(",");
                if (m_FirstTime) {
                    fosr.write(output.getBytes());
                    fosrd.write(output.getBytes());
                }
                m_Data.get("record_item").addLast(store[0]);
                m_Data.get("record_amount").addLast(store[1]);
                m_Data.get("record_date").addLast(store[2]);
                m_Data.get("record_category").addLast(store[3]);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Preparing the frame layout
        m_FrameLayout = new FrameLayout(this);

        //Setting up the game overlay
        m_GameOverlay = new RelativeLayout(this);

        //Setting up variables
        View anchorView;
        Button button;
        RelativeLayout.LayoutParams rl;

        //Setting up the first anchor view
        anchorView = new View(this);
        int anchor_view_1_id = View.generateViewId();
        anchorView.setId(anchor_view_1_id);
        rl = new RelativeLayout.LayoutParams(0, 200);
        rl.addRule(RelativeLayout.CENTER_VERTICAL);
        anchorView.setLayoutParams(rl);
        m_GameOverlay.addView(anchorView);

        //Setting up the right button
        Button rightButton = new Button(this);
        rightButton.setText("Right");
        int right_button_id = View.generateViewId();
        rightButton.setId(right_button_id);
        rl = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl.addRule(RelativeLayout.ABOVE, anchor_view_1_id);
        rightButton.setLayoutParams(rl);
        m_GameOverlay.addView(rightButton);

        //Setting up the second anchor view
        anchorView = new View(this);
        int anchor_view_2_id = View.generateViewId();
        anchorView.setId(anchor_view_2_id);
        rl = new RelativeLayout.LayoutParams(0, 200);
        rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
        anchorView.setLayoutParams(rl);
        m_GameOverlay.addView(anchorView);

        //Setting up the overview button
        button = new Button(this);
        button.setText(R.string.overview_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, OverviewActivity.class);
                intent.putExtra("recordItem", m_Data.get("record_item"));
                intent.putExtra("recordAmount", m_Data.get("record_amount"));
                intent.putExtra("recordCategory", m_Data.get("record_category"));
                intent.putExtra("recordDate", m_Data.get("record_date"));
                startActivity(intent);
            }
        });
        int overview_button_id = View.generateViewId();
        button.setId(overview_button_id);
        rl = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.addRule(RelativeLayout.LEFT_OF, anchor_view_2_id);
        button.setLayoutParams(rl);
        m_GameOverlay.addView(button);

        //Setting up the log button
        button = new Button(this);
        button.setText("Log");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, LoggingActivity.class);
                intent.putExtra("presetItem", m_Data.get("preset_list"));
                intent.putExtra("presetItemAmount", m_Data.get("preset_list_amount"));
                intent.putExtra("presetItemCategory", m_Data.get("preset_list_category"));
                startActivity(intent);
            }
        });
        int log_button_id = View.generateViewId();
        button.setId(log_button_id);
        rl = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.addRule(RelativeLayout.RIGHT_OF, anchor_view_2_id);
        button.setLayoutParams(rl);
        m_GameOverlay.addView(button);

        //Setting up the game view
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
                rightButton,
                getSharedPreferences("edu.cuhk.csci3310_finaciallogger", MODE_PRIVATE));

        //Setting up the frame layout
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

    public void openRecord(View view) {
        Intent intent = new Intent(GameActivity.this, OverviewActivity.class);
        intent.putExtra("recordItem", m_Data.get("record_item"));
        intent.putExtra("recordAmount", m_Data.get("record_amount"));
        intent.putExtra("recordCategory", m_Data.get("record_category"));
        intent.putExtra("recordDate", m_Data.get("record_date"));
        startActivity(intent);
    }
}