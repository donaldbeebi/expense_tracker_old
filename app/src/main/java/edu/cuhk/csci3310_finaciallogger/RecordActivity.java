package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;

public class RecordActivity extends AppCompatActivity {
    String[] recordItem,recordAmount,recordCategory,recordDate;
    private RecyclerView mRecyclerView;
    private RecordListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        recordItem = intent.getStringArrayListExtra("recordItem2").toArray(new String[0]);
        recordAmount = intent.getStringArrayListExtra("recordAmount2").toArray(new String[0]);
        recordCategory = intent.getStringArrayListExtra("recordCategory2").toArray(new String[0]);
        recordDate = intent.getStringArrayListExtra("recordDate2").toArray(new String[0]);
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed,
        // initially just a list of image path
        // Update as needed...
        mAdapter = new RecordListAdapter(this, recordItem, recordAmount,recordDate,recordCategory);

        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    // this event will enable the back function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}