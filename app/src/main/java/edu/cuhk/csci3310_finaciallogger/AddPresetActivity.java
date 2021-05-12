package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.Calendar;
import java.util.Date;

public class AddPresetActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private Button Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button0, Buttondot, ButtonClear, ButtonCategory, ButtonConfirm;
    private TextView mAmountView;
    private String currentAmount = " ";
    private EditText currentInput;
    private String FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset";
    private String FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/preset_dup";
    private String RECORD_FILE_PATH = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record";
    private String RECORD_FILE_PATH_dup = "/data/data/edu.cuhk.csci3310_finaciallogger/files/record_dup";
    private Integer SelectCategory;
    private String category;
    private PopupMenu categoryMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_preset);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

            Intent intent = getIntent();
            Button0 = (Button) findViewById(R.id.button0);
            Button1 = (Button) findViewById(R.id.button1);
            Button2 = (Button) findViewById(R.id.button2);
            Button3 = (Button) findViewById(R.id.button3);
            Button4 = (Button) findViewById(R.id.button4);
            Button5 = (Button) findViewById(R.id.button5);
            Button6 = (Button) findViewById(R.id.button6);
            Button7 = (Button) findViewById(R.id.button7);
            Button8 = (Button) findViewById(R.id.button8);
            Button9 = (Button) findViewById(R.id.button9);
            ButtonConfirm = (Button) findViewById(R.id.ConfirmLogging);
            ButtonCategory = (Button) findViewById((R.id.CategoryButton));
            Buttondot = (Button) findViewById(R.id.buttondot);
            ButtonClear = (Button) findViewById(R.id.buttonClear);
            currentInput = (EditText) findViewById(R.id.current_Input);
            mAmountView = (TextView) findViewById((R.id.AmountView));
            categoryMenu = new PopupMenu(this, ButtonCategory);
            categoryMenu.setOnMenuItemClickListener(this);
            categoryMenu.inflate(R.menu.category_menu);
            TextView ItemTitleTextView= findViewById(R.id.ItemTitleTextView);
            ItemTitleTextView.setPaintFlags(ItemTitleTextView.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
            TextView AmountTitleTextView= findViewById(R.id.AmountTitleTextView);
            AmountTitleTextView.setPaintFlags(AmountTitleTextView.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
            if (category!=null) {
                switch (category) {
                    case "Food":
                        ButtonCategory.setText("Food");
                        return;
                    case "Daily Necessities":
                        ButtonCategory.setText("Daily Necessities");
                        return;
                    case "Transportation":
                        ButtonCategory.setText("Transportation");
                        return;
                    case "Clothes":
                        ButtonCategory.setText("Clothes");
                        return;
                    case "Entertainment":
                        ButtonCategory.setText("Entertainment");
                        return;
                    case "Transfer Fee":
                        ButtonCategory.setText("Transfer Fee");
                        return;
                    case "Health":
                        ButtonCategory.setText("Health");
                        return;
                    case "Beauty":
                        ButtonCategory.setText("Beauty");
                        return;
                    case "Utilities":
                        ButtonCategory.setText("Utilities");
                        return;
                    case "Others":
                        ButtonCategory.setText("Others");
                        return;
                }
            }
            SelectCategory=0;
        }


        public void savePreset(View view) {
            String savePresetItem = String.valueOf(currentInput.getText());
            String savePresetAmount = currentAmount;
            String savePresetCategory= String.valueOf(ButtonCategory.getText());

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
                Toast.makeText(this, TARGET, Toast.LENGTH_SHORT).show();
                fos = openFileOutput("preset", MODE_PRIVATE);
                int count = 0;
                while (((line = br.readLine()) != null)) {
                    if (count == 0) {
                        fos.write(TARGET.getBytes());
                        fos.write((line+"\n").getBytes());
                        count++;
                        continue;
                    }
                    String output = line + "\n";
                    fos.write(output.getBytes());
                    count++;
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

            Intent intent = new Intent(this, GameActivity.class);
            finish();
            startActivity(intent);
        }

        public void updateAmount(View view) {
            Button button = (Button) view;
            String buttonText = button.getText().toString();
            currentAmount = currentAmount + buttonText;
            mAmountView.setText(currentAmount);
        }

        public void clearAmount(View view) {
            currentAmount = "";
            mAmountView.setText(currentAmount);
        }

        public void showCategory(View view) {
            categoryMenu.show();
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
    }
