package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get records from the GraphOptionsActivity
        Intent intent = getIntent();
        LinkedList<String> mRecordItem = new LinkedList<>((List<String>) intent.getSerializableExtra("recordItem3"));
        LinkedList<String> mRecordAmount = new LinkedList<>((List<String>) intent.getSerializableExtra("recordAmount3"));
        LinkedList<String> mRecordCategory = new LinkedList<>((List<String>) intent.getSerializableExtra("recordCategory3"));
        LinkedList<String> mRecordDate = new LinkedList<>((List<String>) intent.getSerializableExtra("recordDate3"));

        // creating the bar chart
        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> spending = new ArrayList<>();

        for(int i = 0; i < mRecordItem.size(); i++) {
            spending.add(new BarEntry(i, Float.parseFloat(mRecordAmount.get(i))));
        }
        BarDataSet barDataSet = new BarDataSet(spending, "Spending");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
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