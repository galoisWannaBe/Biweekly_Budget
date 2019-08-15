package com.example.biweeklybudget;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    EditText dateEdit;
    Button saveSeed;
    int date = 0;
    StringBuilder sb;
    int mon;
    int day;
    int jul;
    String dateStr;
    int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.setTitle("Settings");
        dateEdit = findViewById(R.id.seed_pay);
        saveSeed = findViewById(R.id.save_pay);
    }

    public void saveSeed(View view){
            dateStr = dateEdit.getText().toString();
            if (dateStr.isEmpty()){
                Toast.makeText(
                        getApplicationContext(),
                        "Please enter a valid date",
                        Toast.LENGTH_LONG).show();
            }else {
                date = Integer.parseInt(dateStr);
                jul = 0;
                if (date >= 101 && date <= 131 || date >= 201 && date <= 228 || date >= 301 && date <= 331 || date >= 401 && date <= 430 || date >= 501 && date <= 531 || date >= 601 && date <= 630 || date >= 701 && date <= 731 || date >= 801 && date <= 831 || date >= 901 && date <= 930 || date >= 1001 && date <= 1031 || date >= 1101 && date <= 1130 || date >= 1201 && date <= 1231) {
                    day = date % 100;
                    date -= day;
                    date = date / 100;
                    mon = date - 1;
                    for(int i = 0; i < mon; i++){
                        jul += months[i];
                    }
                    jul += day;
                    jul = jul % 14;
                    Intent intent = new Intent(this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("seed", jul);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Please enter a valid date",
                            Toast.LENGTH_LONG).show();
                }
            }
    }

}
