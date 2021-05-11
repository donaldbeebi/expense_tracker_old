package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {
    private LinkedList<String> mRecordItem;
    private LinkedList<String> mRecordAmount;
    private LinkedList<String> mRecordCategory;
    private LinkedList<String> mRecordDate;
    private String category;
    private String time;

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
        mRecordItem = new LinkedList<>((List<String>) intent.getSerializableExtra("recordItem3"));
        mRecordAmount = new LinkedList<>((List<String>) intent.getSerializableExtra("recordAmount3"));
        mRecordCategory = new LinkedList<>((List<String>) intent.getSerializableExtra("recordCategory3"));
        mRecordDate = new LinkedList<>((List<String>) intent.getSerializableExtra("recordDate3"));

        // get category and time from the GraphOptionsActivity
        category = intent.getStringExtra("category");
        time = intent.getStringExtra("time");

        // creating the bar chart
        BarChart barChart = findViewById(R.id.barChart);

        // get the data
        ArrayList<BarEntry> spending = getData();

        // setting different attributes of the bar chart
        BarDataSet barDataSet = new BarDataSet(spending, "Spending");
        barDataSet.setColors(obtainColor());
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        // setting the x axis of the bar chart
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getXAxisValues()));
        xAxis.setGranularity(1f);

        // set the position of the legend
        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setFormSize(16f);
        legend.setTextSize(16f);
        legend.setWordWrapEnabled(true);

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

    // check the date of the data to decided whether it should be included in the chart
    // and return its position in the chart
    public int checkDate(String date){
        int position = -1;
        switch (time) {
            case "By Days": {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                long dayDiff = ChronoUnit.DAYS.between(LocalDate.parse(date, dtf), LocalDate.now(ZoneId.of("Asia/Hong_Kong")));
                if (dayDiff < 7) {
                    position = 6 - (int) dayDiff;
                }
                ;
                break;
            }
            case "By Months": {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                long monthDiff = ChronoUnit.MONTHS.between(LocalDate.parse(date, dtf), LocalDate.now(ZoneId.of("Asia/Hong_Kong")));
                if (monthDiff < 6) {
                    position = 5 - (int) monthDiff;
                }
                ;
                break;
            }
            case "By Years": {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                long yearDiff = ChronoUnit.YEARS.between(LocalDate.parse(date, dtf), LocalDate.now(ZoneId.of("Asia/Hong_Kong")));
                if (yearDiff < 5) {
                    position = 4 - (int) yearDiff;
                }
                ;
                break;
            }
        }
        return position;
    }

    // set the x-axis value
    public ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        switch (time) {
            case "By Days":
                for (int i = 6; i >= 0; i--) {
                    xAxis.add(LocalDate.now(ZoneId.of("Asia/Hong_Kong")).minusDays(i).format(DateTimeFormatter.ofPattern("MMM dd")));
                }
                break;
            case "By Months":
                for (int i = 5; i >= 0; i--) {
                    xAxis.add(LocalDate.now(ZoneId.of("Asia/Hong_Kong")).minusMonths(i).format(DateTimeFormatter.ofPattern("MMM/yyyy")));
                }
                break;
            case "By Years":
                for (int i = 4; i >= 0; i--) {
                    xAxis.add(LocalDate.now(ZoneId.of("Asia/Hong_Kong")).minusYears(i).format(DateTimeFormatter.ofPattern("yyyy")));
                }
                break;
        }
        return xAxis;
    }

    // obtain the data
    // check the date of the data, add the data to the amountList if it is within the time range selected by users
    public ArrayList<BarEntry> getData(){
        ArrayList<BarEntry> spending = new ArrayList<>();
        int position = 0;
        float[] amountList;
        switch (time) {
            case "By Days":
                amountList = new float[7];
                for (int i = 0; i < mRecordItem.size(); i++) {
                    if (mRecordCategory.get(i).equals(category)) {
                        position = checkDate(mRecordDate.get(i));
                        if (position != -1) {
                            amountList[position] += Float.parseFloat(mRecordAmount.get(i));
                        }
                    }
                }

                for (int i = 0; i < 7; i++) {
                    spending.add(new BarEntry(i, amountList[i]));
                }
                break;
            case "By Months":
                amountList = new float[6];
                for (int i = 0; i < mRecordItem.size(); i++) {
                    if (mRecordCategory.get(i).equals(category)) {
                        position = checkDate(mRecordDate.get(i));
                        if (position != -1) {
                            amountList[position] += Float.parseFloat(mRecordAmount.get(i));
                        }
                    }
                }

                for (int i = 0; i < 6; i++) {
                    spending.add(new BarEntry(i, amountList[i]));
                }
                break;
            case "By Years":
                amountList = new float[5];
                for (int i = 0; i < mRecordItem.size(); i++) {
                    if (mRecordCategory.get(i).equals(category)) {
                        position = checkDate(mRecordDate.get(i));
                        if (position != -1) {
                            amountList[position] += Float.parseFloat(mRecordAmount.get(i));
                        }
                    }
                }

                for (int i = 0; i < 5; i++) {
                    spending.add(new BarEntry(i, amountList[i]));
                }
                break;
        }
        return spending;
    }

    // return a color a scheme for the chart
    public ArrayList<Integer> obtainColor() {
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(227, 65, 50));
        colors.add(Color.rgb(236,219,83));
        colors.add(Color.rgb(191,216,51));
        colors.add(Color.rgb(108,160,220));
        colors.add(Color.rgb(100,83,148));
        if (time.equals("By Months") || time.equals("By Days")){
            colors.add(Color.rgb(108,79,60));
        }
        if (time.equals("By Days")){
            colors.add(Color.rgb(179,182,185));
        }
        return colors;
    }
}