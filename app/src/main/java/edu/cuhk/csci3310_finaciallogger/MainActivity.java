/**
 * ABANDONED CLASS
 */
package edu.cuhk.csci3310_finaciallogger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {


//
//    private RecyclerView mRecyclerView;
//    private RecordAdapter mAdapter;

    private Integer Firsttime;
    private LinkedList<String> mPresetList = new LinkedList<>();
    private LinkedList<String> mPresetListAmount = new LinkedList<>();
    private LinkedList<String> mPresetListCategory = new LinkedList<>();
    private LinkedList<String> mRecordItem= new LinkedList<>();
    private LinkedList<String> mRecordAmount= new LinkedList<>();
    private LinkedList<String> mRecordDate= new LinkedList<>();
    private LinkedList<String> mRecordCategory= new LinkedList<>();


    private String savedFilePath= "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset";
    private String savedFilePath2="/data/data/edu.cuhk.csci3310_finaciallogger/files/record";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button LoggingButton = findViewById(R.id.LoggingButton);
        Button OverViewButton = findViewById(R.id.OverViewButton);
        File file = new File(savedFilePath);
        InputStream isd = null;
        InputStream isdr = null;

        if (file.exists()) {
            try {
                Firsttime = 0;
                isd = new FileInputStream(savedFilePath);
                isdr= new FileInputStream(savedFilePath2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Firsttime = 1;
            isd = getResources().openRawResource(R.raw.preset);
            isdr = getResources().openRawResource(R.raw.record);

        }

        //Read from inputstream for creating the presetlist in the Logging Activity
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
            if (Firsttime == 1) {
                fos = openFileOutput("preset", MODE_PRIVATE);
                fosd = openFileOutput("preset_dup", MODE_PRIVATE);
            }
            while (((linewrite = br.readLine()) != null)) {
                String output = linewrite + "\n";
                String[] store = linewrite.split(",");
                if (Firsttime == 1) {
                    fos.write(output.getBytes());
                    fosd.write(output.getBytes());
                }

                mPresetList.addLast(store[0]);
                mPresetListAmount.addLast(store[1]);
                mPresetListCategory.addLast(store[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creating local saving for logging record
        try {
            if (Firsttime==1) {
                fosr = openFileOutput("record", MODE_PRIVATE);
                fosrd = openFileOutput("record_dup", MODE_PRIVATE);
            }
                while (((linewriter = brr.readLine()) != null)) {
                    String output = linewriter + "\n";
                    String[] store = linewriter.split(",");
                    if (Firsttime == 1) {
                        fosr.write(output.getBytes());
                        fosrd.write(output.getBytes());
                    }
                    mRecordItem.addLast(store[0]);
                    mRecordAmount.addLast(store[1]);
                    mRecordDate.addLast(store[2]);
                    mRecordCategory.addLast(store[3]);
                }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //End of for creating file to save info for first time open the app
        LoggingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoggingActivity.class);
                intent.putExtra("presetItem", mPresetList);
                intent.putExtra("presetItemAmount", mPresetListAmount);
                intent.putExtra("presetItemCategory",mPresetListCategory);
                finish();
                startActivity(intent);

            }
        });

        //DEBUG
        Log.d("MainActivity", "OnCreate");
        //startActivity(new Intent(this, GameActivity.class));
    }



    public void openRecord(View view) {
        Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
        intent.putExtra("recordItem", mRecordItem);
        intent.putExtra("recordAmount", mRecordAmount);
        intent.putExtra("recordCategory",mRecordCategory);
        intent.putExtra("recordDate",mRecordDate);
        startActivity(intent);
    }
}