package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class AddPresetActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private TextView mCategoryInput;

    private TextView mAmountView;
    private EditText currentAmount;
    private EditText currentInput;
    private Button mSavePresetButton;
    private String FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset";
    private String FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset_dup";
    private String RECORD_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record";
    private String RECORD_FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record_dup";
    private Integer SelectCategory;
    private String category;
    private PopupMenu mCategoryPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_preset);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        mCategoryInput = findViewById(R.id.add_preset_category_input);

        currentInput = (EditText) findViewById(R.id.add_preset_title_input);
        currentAmount=(EditText) findViewById(R.id.add_preset_amount_input);
        mAmountView = (TextView) findViewById((R.id.add_preset_amount_input));

        mCategoryPopupMenu = new PopupMenu(this, mCategoryInput);
        mCategoryPopupMenu.setOnMenuItemClickListener(this);
        mCategoryPopupMenu.inflate(R.menu.category_menu);
        TextView ItemTitleTextView= findViewById(R.id.add_preset_title_text_view);
        TextView AmountTitleTextView= findViewById(R.id.add_preset_amount_text_view);

        //setting save preset button
        mSavePresetButton = findViewById(R.id.SavePresetButton);
        mSavePresetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreset(view);
            }
        });

        if (category!=null) {
            switch (category) {
                case "Food":
                    mCategoryInput.setText("Food");
                    return;
                case "Daily Necessities":
                    mCategoryInput.setText("Daily Necessities");
                    return;
                case "Transportation":
                    mCategoryInput.setText("Transportation");
                    return;
                case "Clothes":
                    mCategoryInput.setText("Clothes");
                    return;
                case "Entertainment":
                    mCategoryInput.setText("Entertainment");
                    return;
                case "Transfer Fee":
                    mCategoryInput.setText("Transfer Fee");
                    return;
                case "Health":
                    mCategoryInput.setText("Health");
                    return;
                case "Beauty":
                    mCategoryInput.setText("Beauty");
                    return;
                case "Utilities":
                    mCategoryInput.setText("Utilities");
                    return;
                case "Others":
                    mCategoryInput.setText("Others");
                    return;
            }
        }
        SelectCategory=0;
    }

    public void showCategory(View view) {
        mCategoryPopupMenu.show();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getRootView().getApplicationWindowToken(),0);
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        mCategoryInput.setText(String.valueOf(item.getTitle()));
        mCategoryInput.setTextColor(Color.parseColor("#3F3F3F"));
        mCategoryInput.setTextSize(24);
        SelectCategory = 1;
        return true;
    }

    //called when the save as preset button is clicked
    public void savePreset(View view) {
        String savePresetItem = String.valueOf(currentInput.getText());
        String savePresetAmount = String.valueOf(currentAmount.getText());
        String savePresetCategory= String.valueOf(mCategoryInput.getText());

        //check if filled in all the fields
        if (savePresetAmount==null || SelectCategory==0 || savePresetItem==null || savePresetAmount==""){
            Toast.makeText(this, "Please fill in all the required fields!", Toast.LENGTH_LONG).show();
            return;
        }

        //check if the input of amount contains more than one "."
        String[] ForCheckAmount=savePresetAmount.split("");
        Log.d("countdot check", String.valueOf(ForCheckAmount));
        Integer countdot=0;
        for (int i=0;i<ForCheckAmount.length;i++){
            Log.d("countdot",ForCheckAmount[i]);

            if (ForCheckAmount[i].equals(".")){
                Log.d("countdot check","one dot count");
                countdot++;
            }

            if (countdot==2){
                Toast.makeText(this, "Invalid input amount, please clear and try again", Toast.LENGTH_LONG).show();
                return;
            }
        }


        FileInputStream is = null;
        FileInputStream isd = null;
        try {
            isd = new FileInputStream(FILE_PATH_dup);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(isd, Charset.forName("UTF-8")));
        FileOutputStream fos = null;
        String line = "";
        try {
            String TARGET = savePresetItem + "," + savePresetAmount + "," + savePresetCategory + "\n";
            Toast.makeText(this, "Saved preset", Toast.LENGTH_SHORT).show();
            fos = openFileOutput("preset", MODE_PRIVATE);
            fos.write(TARGET.getBytes());
            while (((line = br.readLine()) != null)) {
                String output = line + "\n";
                fos.write(output.getBytes());
                //Toast.makeText(this, "overwriting", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //overwrite duplicate file
        try {
            is = new FileInputStream(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader brd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        FileOutputStream fosd = null;
        try {
            fosd = openFileOutput("preset_dup", MODE_PRIVATE);
            while (((line = brd.readLine()) != null)) {
                String output = line + "\n";
                fosd.write(output.getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setResult(Activity.RESULT_OK);
        finish();
    }
}

