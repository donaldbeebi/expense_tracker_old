package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class LoggingActivity extends AppCompatActivity {
    String[] PresetList,PresetListAmount,PresetListCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        PresetList = intent.getStringArrayListExtra("presetItem").toArray(new String[0]);
        PresetListAmount = intent.getStringArrayListExtra("presetItemAmount").toArray(new String[0]);
        PresetListCategory = intent.getStringArrayListExtra("presetItemCategory").toArray(new String[0]);
        Button StartInputButton=findViewById(R.id.StartInputbutton);
        EditText input=(EditText) findViewById((R.id.Manual_Input));

        //adding underline for the title text
        TextView PresetTitleItemTextView= findViewById(R.id.PresetTitleItemTextView);
        TextView PresetTitleAmountTextView=findViewById(R.id.PresetTitleAmountTextView);
        TextView PresetTitleCategoryTextView= findViewById(R.id.PresetTitleCategoryTextView);
        PresetTitleAmountTextView.setPaintFlags(PresetTitleAmountTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        PresetTitleItemTextView.setPaintFlags(PresetTitleItemTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        PresetTitleCategoryTextView.setPaintFlags(PresetTitleCategoryTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        //initializing fragment
        Bundle bundle=new Bundle();
        bundle.putStringArray("presetItemList", PresetList);
        bundle.putStringArray("presetAmountList", PresetListAmount);
        bundle.putStringArray("presetCategoryList", PresetListCategory);
        PresetFragment presetFragment=new PresetFragment();
        presetFragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.PresetFragment,presetFragment);
        transaction.commit();

        //onClick for logging with Manual Input
        StartInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggingActivity.this, InputActivity.class);
                intent.putExtra("input", input.getText().toString());
                startActivity(intent);

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

    public void startNewPreset(View view) {
        Intent intent = new Intent(LoggingActivity.this, AddPresetActivity.class);
        startActivity(intent);
    }
}
