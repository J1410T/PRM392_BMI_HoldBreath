package com.example.muibmidemo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;

public class BmiActivity extends AppCompatActivity {
    private SeekBar heightSeekBar, weightSeekBar;
    private TextView heightTextView, weightTextView, resultTextView;
    private RadioGroup genderRadioGroup;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        // Set up Toolbar for back navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        heightSeekBar = findViewById(R.id.heightSeekBar);
        weightSeekBar = findViewById(R.id.weightSeekBar);
        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);
        resultTextView = findViewById(R.id.resultTextView);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        calculateButton = findViewById(R.id.calculateButton);

        // Update height display on SeekBar change
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                heightTextView.setText(String.format("Height: %d cm", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Update weight display on SeekBar change
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightTextView.setText(String.format("Weight: %d kg", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Calculate BMI when button is clicked
        calculateButton.setOnClickListener(v -> calculateBMI());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close the current activity and go back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to calculate BMI
    private void calculateBMI() {
        int height = heightSeekBar.getProgress();
        int weight = weightSeekBar.getProgress();

        if (height > 0) {
            float bmi = (float) weight / ((float) height / 100 * (float) height / 100);
            String result = String.format("BMI: %.1f", bmi);

            if (bmi < 18.5) {
                result += " (Underweight)";
            } else if (bmi < 25) {
                result += " (Normal weight)";
            } else if (bmi < 30) {
                result += " (Overweight)";
            } else {
                result += " (Obese)";
            }

            resultTextView.setText(result);
        } else {
            resultTextView.setText("Please set a valid height");
        }
    }
}
