package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import edu.cuhk.csci3310_finaciallogger.game.SharedPreferencesManager;

public class InputActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private Button Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button0, Buttondot, ButtonClear, ButtonConfirm;
    private EditText currentAmount;
    private EditText currentInput;
    private String FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset";
    private String FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset_dup";
    private String RECORD_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record";
    private String RECORD_FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record_dup";
    private Integer SelectCategory;
    private PopupMenu mCategoryPopupMenu;

    //by donald
    private SharedPreferencesManager mSPM;
    private TextView mCategoryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Log.d("InputActivity", "onCreate");

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String input = intent.getStringExtra("input");
        String amount = intent.getStringExtra("PresetAmount");
        String category = intent.getStringExtra("PresetCategory");
        ButtonConfirm = (Button) findViewById(R.id.confirm_button);
        currentInput = (EditText) findViewById(R.id.input_title_input);
        currentAmount=(EditText) findViewById(R.id.input_amount_input);
        TextView ItemTitleTextView= findViewById(R.id.ItemTitleTextView);
        TextView AmountTitleTextView= findViewById(R.id.input_amount_text_view);
        currentInput.setText(input);
        SelectCategory=0;
        if (amount!=null){
            currentAmount.setText(amount);
        }

        mCategoryInput = findViewById(R.id.input_category_input);
        mCategoryPopupMenu = new PopupMenu(this, mCategoryInput);
        mCategoryPopupMenu.setOnMenuItemClickListener(this);
        mCategoryPopupMenu.inflate(R.menu.category_menu);
        if(category != null) {
            SelectCategory = 1;
            mCategoryInput.setText(category);
            mCategoryInput.setTextColor(Color.parseColor("#3F3F3F"));
            mCategoryInput.setTextSize(24);
        }
        //by donald
        mSPM = SharedPreferencesManager.getInstance();
    }

    public void confirmAmount(View view) {
        String saveRecordItem = String.valueOf(currentInput.getText());
        String saveRecordAmount = String.valueOf(currentAmount.getText());
        String saveRecordCategory= String.valueOf(mCategoryInput.getText());
        if (saveRecordAmount==null || SelectCategory==0 || saveRecordItem==null || saveRecordAmount==""){
            Toast.makeText(this, "Please fill in all the required fields!", Toast.LENGTH_LONG).show();
            return;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate saveRecordDate= LocalDate.now(ZoneId.of("Asia/Hong_Kong"));
        String DateStr=dtf.format(saveRecordDate);
        FileInputStream isr = null;
        FileInputStream isdr = null;
        try {
            isdr = new FileInputStream(RECORD_FILE_PATH_dup);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader brr = new BufferedReader(new InputStreamReader(isdr, Charset.forName("UTF-8")));
        FileOutputStream fosr = null;
        String liner = "";
        try {
            String TARGET = saveRecordItem + "," + saveRecordAmount + "," + DateStr+"," + saveRecordCategory + "\n";
            //Toast.makeText(this, TARGET, Toast.LENGTH_SHORT).show();
            fosr = openFileOutput("record", MODE_PRIVATE);
            int count = 0;
            while (((liner = brr.readLine()) != null)) {
                if (count == 0) {
                    fosr.write(TARGET.getBytes());
                    fosr.write((liner+"\n").getBytes());
                    count++;
                    continue;
                }

                String output = liner + "\n";
                fosr.write(output.getBytes());
                count++;}
                //Toast.makeText(this, "overwriting", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            isr = new FileInputStream(RECORD_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader brrd = new BufferedReader(new InputStreamReader(isr, Charset.forName("UTF-8")));
        FileOutputStream fosd = null;
        try {
            fosd = openFileOutput("record_dup", MODE_PRIVATE);
            while (((liner = brrd.readLine()) != null)) {
                String output = liner + "\n";
                fosd.write(output.getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //by Donald
        //the user earns 1 buck when he finishes logging
        int earnedBucks = 1;
        mSPM.addBucks(earnedBucks);
        Toast.makeText(this, "Logging complete. You just earned " + earnedBucks + " buck.", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void showCategory(View view) {
        mCategoryPopupMenu.show();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getRootView().getApplicationWindowToken(),0);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        mCategoryInput.setText(String.valueOf(item.getTitle()));
        mCategoryInput.setTextColor(Color.parseColor("#3F3F3F"));
        mCategoryInput.setTextSize(24);
        SelectCategory = 1;
        return true;
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