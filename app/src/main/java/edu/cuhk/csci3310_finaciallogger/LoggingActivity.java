package edu.cuhk.csci3310_finaciallogger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoggingActivity extends AppCompatActivity {
    Button preset1,preset2,preset3,preset4;
    String[] PresetList,PresetListAmount,PresetListCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);
        Intent intent = getIntent();
        PresetList = intent.getStringArrayListExtra("presetItem").toArray(new String[0]);
        PresetListAmount = intent.getStringArrayListExtra("presetItemAmount").toArray(new String[0]);
        PresetListCategory = intent.getStringArrayListExtra("presetItemCategory").toArray(new String[0]);
        preset1 = findViewById(R.id.preset1);
        preset2 = findViewById(R.id.preset2);
        preset3 = findViewById(R.id.preset3);
        preset4 = findViewById(R.id.preset4);
        Button StartInputButton=findViewById(R.id.StartInputbutton);
        EditText input=(EditText) findViewById((R.id.Manual_Input));
        preset1.setText(PresetList[0]);
        preset2.setText(PresetList[1]);
        preset3.setText(PresetList[2]);
        preset4.setText(PresetList[3]);

        preset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggingActivity.this, InputActivity.class);
                intent.putExtra("input",PresetList[0]);
                intent.putExtra("amount",PresetListAmount[0]);
                intent.putExtra("category",PresetListCategory[0]);
                startActivity(intent);

            }
        });
        preset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggingActivity.this, InputActivity.class);
                intent.putExtra("input",PresetList[1]);
                intent.putExtra("amount",PresetListAmount[1]);
                intent.putExtra("category",PresetListCategory[1]);
                startActivity(intent);

            }
        });
        preset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggingActivity.this, InputActivity.class);
                intent.putExtra("input",PresetList[2]);
                intent.putExtra("amount",PresetListAmount[2]);
                intent.putExtra("category",PresetListCategory[2]);
                startActivity(intent);

            }
        });
        preset4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggingActivity.this, InputActivity.class);
                intent.putExtra("input",PresetList[3]);
                intent.putExtra("amount",PresetListAmount[3]);
                intent.putExtra("category",PresetListCategory[3]);
                startActivity(intent);

            }
        });


        StartInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggingActivity.this, InputActivity.class);
                intent.putExtra("input", input.getText().toString());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        preset1.setText(PresetList[0]);
        preset2.setText(PresetList[1]);
        preset3.setText(PresetList[2]);
        preset4.setText(PresetList[3]);
    }
}
