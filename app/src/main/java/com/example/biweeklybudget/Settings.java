package com.example.biweeklybudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dateEdit = findViewById(R.id.seed_pay);
        saveSeed = findViewById(R.id.save_pay);
    }

    public void saveSeed(View view){
            dateStr = dateEdit.getText().toString();
            if (dateStr.isEmpty()){
                Toast.makeText(
                        getApplicationContext(),
                        R.string.invalid_date,
                        Toast.LENGTH_LONG).show();
            }else {
                date = Integer.parseInt(dateStr);
                if (date >= 101 && date <= 131 || date >= 201 && date <= 228 || date >= 301 && date <= 331 || date >= 401 && date <= 430 || date >= 501 && date <= 531 || date >= 601 && date <= 630 || date >= 701 && date <= 731 || date >= 801 && date <= 831 || date >= 901 && date <= 930 || date >= 1001 && date <= 1031 || date >= 1101 && date <= 1130 || date >= 1201 && date <= 1231) {
                    Intent intent = new Intent(this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("seed", date);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.invalid_date,
                            Toast.LENGTH_LONG).show();
                }
            }
    }
}
