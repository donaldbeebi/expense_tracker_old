package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    private Button buttonRecords;
    private Button buttonGraphs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get records from the MainActivity
        Intent intent = getIntent();
        LinkedList<String> mRecordItem2 = new LinkedList<>((List<String>) intent.getSerializableExtra("recordItem"));
        LinkedList<String> mRecordAmount2 = new LinkedList<>((List<String>) intent.getSerializableExtra("recordAmount"));
        LinkedList<String> mRecordCategory2 = new LinkedList<>((List<String>) intent.getSerializableExtra("recordCategory"));
        LinkedList<String> mRecordDate2 = new LinkedList<>((List<String>) intent.getSerializableExtra("recordDate"));

        buttonRecords = (Button) findViewById(R.id.view_by_records_button);
        buttonGraphs = (Button) findViewById(R.id.view_by_graphs_button);

        // onclick behaviour of the view by records button
        buttonRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send records to RecordActivity
                Intent newIntent = new Intent(OverviewActivity.this, RecordActivity.class);
                newIntent.putExtra("recordItem2", mRecordItem2);
                newIntent.putExtra("recordAmount2", mRecordAmount2);
                newIntent.putExtra("recordCategory2", mRecordCategory2);
                newIntent.putExtra("recordDate2", mRecordDate2);
                startActivity(newIntent);
            }
        });

        // onclick behaviour of the view by graphs button
        buttonGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send records to GraphOptionsActivity
                Intent newIntent = new Intent(OverviewActivity.this, GraphOptionsActivity.class);
                newIntent.putExtra("recordItem2", mRecordItem2);
                newIntent.putExtra("recordAmount2", mRecordAmount2);
                newIntent.putExtra("recordCategory2", mRecordCategory2);
                newIntent.putExtra("recordDate2", mRecordDate2);
                startActivity(newIntent);
            }
        });
    }

    // this event will enable the back function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}