package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import edu.cuhk.csci3310_finaciallogger.game.SharedPreferencesManager;

public class InputActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private Button Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button0, Buttondot, ButtonClear, ButtonCategory, ButtonConfirm;
    private EditText currentAmount;
    private EditText currentInput;
    private String FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset";
    private String FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset_dup";
    private String RECORD_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record";
    private String RECORD_FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record_dup";
    private Integer SelectCategory;
    private String category;
    private PopupMenu categoryMenu;

    //by donald
    private SharedPreferencesManager m_SPM;

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
        ButtonConfirm = (Button) findViewById(R.id.ConfirmLogging);
        ButtonCategory = (Button) findViewById((R.id.CategoryButton));
        currentInput = (EditText) findViewById(R.id.current_Input);
        currentAmount=(EditText) findViewById(R.id.AmountView);
        TextView ItemTitleTextView= findViewById(R.id.ItemTitleTextView);
        ItemTitleTextView.setPaintFlags(ItemTitleTextView.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
        TextView AmountTitleTextView= findViewById(R.id.AmountTitleTextView);
        AmountTitleTextView.setPaintFlags(AmountTitleTextView.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
        currentInput.setText(input);
        SelectCategory=0;
        if (amount!=null){
            currentAmount.setText(amount);
        }

        categoryMenu = new PopupMenu(this, ButtonCategory);
        categoryMenu.setOnMenuItemClickListener(this);
        categoryMenu.inflate(R.menu.category_menu);
        if (category!=null) {
            SelectCategory=1;
            switch (category) {
                case "Food":
                    ButtonCategory.setText("Food");
                    break;
                case "Daily Necessities":
                    ButtonCategory.setText("Daily Necessities");
                    break;
                case "Transportation":
                    ButtonCategory.setText("Transportation");
                    break;
                case "Clothes":
                    ButtonCategory.setText("Clothes");
                    break;
                case "Entertainment":
                    ButtonCategory.setText("Entertainment");
                    break;
                case "Transfer Fee":
                    ButtonCategory.setText("Transfer Fee");
                    break;
                case "Health":
                    ButtonCategory.setText("Health");
                    break;
                case "Beauty":
                    ButtonCategory.setText("Beauty");
                    break;
                case "Utilities":
                    ButtonCategory.setText("Utilities");
                    break;
                case "Others":
                    ButtonCategory.setText("Others");
                    break;
            }
        }
        //by donald
        m_SPM = SharedPreferencesManager.getInstance();
        Log.d("InputActivity", "m_SPM got instance");
    }

    public void confirmAmount(View view) {
        String saveRecordItem = String.valueOf(currentInput.getText());
        String saveRecordAmount = String.valueOf(currentAmount.getText());
        String saveRecordCategory= String.valueOf(ButtonCategory.getText());
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
        m_SPM.addBucks(earnedBucks);
        Toast.makeText(this, "Logging complete. You just earned " + earnedBucks + " buck.", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void showCategory(View view) {
        categoryMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                ButtonCategory.setText("Food");
                SelectCategory=1;
                return true;
            case R.id.item2:
                ButtonCategory.setText("Daily Necessities");
                SelectCategory=1;
                return true;
            case R.id.item3:
                ButtonCategory.setText("Transportation");
                SelectCategory=1;
                return true;
            case R.id.item4:
                ButtonCategory.setText("Clothes");
                SelectCategory=1;
                return true;
            case R.id.item5:
                ButtonCategory.setText("Entertainment");
                SelectCategory=1;
                return true;
            case R.id.item6:
                ButtonCategory.setText("Transfer Fee");
                SelectCategory=1;
                return true;
            case R.id.item7:
                ButtonCategory.setText("Health");
                SelectCategory=1;
                return true;
            case R.id.item8:
                ButtonCategory.setText("Beauty");
                SelectCategory=1;
                return true;
            case R.id.item9:
                ButtonCategory.setText("Utilities");
                SelectCategory=1;
                return true;
            case R.id.item10:
                ButtonCategory.setText("Others");
                SelectCategory=1;
                return true;
            default:
                return false;
        }
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