package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoggingActivity extends AppCompatActivity {
    String[] PresetList, PresetListAmount, PresetListCategory;
    PresetFragment mPresetFragment;

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
        Button StartInputButton=findViewById(R.id.StartInputButton);
        //EditText input= findViewById((R.id.Manual_Input));

        //adding underline for the title text
        //TextView PresetTitleItemTextView= findViewById(R.id.PresetTitleItemTextView);
        //TextView PresetTitleAmountTextView=findViewById(R.id.PresetTitleAmountTextView);
        //TextView PresetTitleCategoryTextView= findViewById(R.id.PresetTitleCategoryTextView);
        //PresetTitleAmountTextView.setPaintFlags(PresetTitleAmountTextView.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
        //PresetTitleItemTextView.setPaintFlags(PresetTitleItemTextView.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
        //PresetTitleCategoryTextView.setPaintFlags(PresetTitleCategoryTextView.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);

        //initializing fragment
        Bundle bundle=new Bundle();
        bundle.putStringArray("presetItemList", PresetList);
        bundle.putStringArray("presetAmountList", PresetListAmount);
        bundle.putStringArray("presetCategoryList", PresetListCategory);
        mPresetFragment =new PresetFragment();
        mPresetFragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.PresetFragment,mPresetFragment);
        transaction.commit();

        //onClick for logging with Manual Input
        StartInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggingActivity.this, InputActivity.class);
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
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if(resultCode == Activity.RESULT_OK) {
                mPresetFragment.updateDataSet();
            }
        }
    }
}
