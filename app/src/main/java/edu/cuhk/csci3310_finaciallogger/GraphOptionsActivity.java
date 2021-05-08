package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class GraphOptionsActivity extends AppCompatActivity {

    private Button buttonCategory;
    private Button buttonTime;
    private Button buttonType;

    private LinkedList<String> mRecordItem3, mRecordAmount3, mRecordCategory3, mRecordDate3;
    private String category = null, time = null, type = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_options);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get records from the OverviewActivity
        Intent intent = getIntent();
        mRecordItem3 = new LinkedList<>((List<String>) intent.getSerializableExtra("recordItem2"));
        mRecordAmount3 = new LinkedList<>((List<String>) intent.getSerializableExtra("recordAmount2"));
        mRecordCategory3 = new LinkedList<>((List<String>) intent.getSerializableExtra("recordCategory2"));
        mRecordDate3 = new LinkedList<>((List<String>) intent.getSerializableExtra("recordDate2"));

        buttonCategory = (Button) findViewById(R.id.button_category);
        buttonTime = (Button) findViewById(R.id.button_time);
        buttonType = (Button) findViewById(R.id.button_type);
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

    // create the category pop up menu
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void showPopupCategory(View v) {
        @SuppressLint("RtlHardcoded") PopupMenu popup = new PopupMenu(this, v, Gravity.RIGHT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.category_menu, popup.getMenu());
        popup.show();

        // the on menu item click behaviour of the category pop up menu
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item1:
                    category = "Food";
                    buttonCategory.setText(category);
                    return true;
                case R.id.item2:
                    category = "Daily Necessities";
                    buttonCategory.setText(category);
                    return true;
                case R.id.item3:
                    category = "Transportation";
                    buttonCategory.setText(category);
                    return true;
                case R.id.item4:
                    category = "Clothes";
                    buttonCategory.setText(category);
                    return true;
                case R.id.item5:
                    category = "Entertainment";
                    buttonCategory.setText(category);
                    return true;
                case R.id.item6:
                    category = "Transfer Fee";
                    buttonCategory.setText(category);
                    return true;
                case R.id.item7:
                    category = "Health";
                    buttonCategory.setText(category);
                    return true;
                case R.id.item8:
                    category = "Beauty";
                    buttonCategory.setText(category);
                    return true;
                case R.id.item9:
                    category = "Utilities";
                    buttonCategory.setText(category);
                    return true;
                case R.id.item10:
                    category = "Others";
                    buttonCategory.setText(category);
                    return true;
                default:
                    return false;
            }
        });
    }

    // create the time pop up menu
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void showPopupTime(View v) {
        @SuppressLint("RtlHardcoded") PopupMenu popup = new PopupMenu(this, v, Gravity.RIGHT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.time_menu, popup.getMenu());
        popup.show();

        // the on menu item click behaviour of the category pop up menu
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.time1:
                    time = "By Days";
                    buttonTime.setText(time);
                    return true;
                case R.id.time2:
                    time = "By Months";
                    buttonTime.setText(time);
                    return true;
                case R.id.time3:
                    time = "By Years";
                    buttonTime.setText(time);
                    return true;
                default:
                    return false;
            }
        });
    }

    // create the graph type pop up menu
    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    public void showPopupType(View v) {
        @SuppressLint("RtlHardcoded") PopupMenu popup = new PopupMenu(this, v, Gravity.RIGHT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.type_menu, popup.getMenu());
        popup.show();

        // the on menu item click behaviour of the category pop up menu
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.type1:
                    type = "Bar Chart";
                    buttonType.setText(type);
                    return true;
                case R.id.type2:
                    type = "Line Chart";
                    buttonType.setText(type);
                    return true;
                case R.id.type3:
                    type = "Pie Chart";
                    buttonType.setText(type);
                    return true;
                default:
                    return false;
            }
        });
    }

    public void generateGraph(View view) {
        if (category != null && time != null && type != null) {
            if (type.equals("Bar Chart")) {
                Intent newIntent = new Intent(GraphOptionsActivity.this, BarChartActivity.class);
                // send records to BarChartActivity
                newIntent.putExtra("recordItem3", mRecordItem3);
                newIntent.putExtra("recordAmount3", mRecordAmount3);
                newIntent.putExtra("recordCategory3", mRecordCategory3);
                newIntent.putExtra("recordDate3", mRecordDate3);

                // send category and time to BarChartActivity
                newIntent.putExtra("category", category);
                newIntent.putExtra("time", time);
                startActivity(newIntent);
            }
            else if (type.equals("Line Chart")) {
                Intent newIntent = new Intent(GraphOptionsActivity.this, LineChartActivity.class);
                // send records to LineChartActivity
                newIntent.putExtra("recordItem3", mRecordItem3);
                newIntent.putExtra("recordAmount3", mRecordAmount3);
                newIntent.putExtra("recordCategory3", mRecordCategory3);
                newIntent.putExtra("recordDate3", mRecordDate3);

                // send category and time to LineChartActivity
                newIntent.putExtra("category", category);
                newIntent.putExtra("time", time);
                startActivity(newIntent);
            }
        }
        else{
            Toast.makeText(this, "Error, please make sure you selected a category, a time and a chart type", Toast.LENGTH_LONG).show();
        }
    }
}