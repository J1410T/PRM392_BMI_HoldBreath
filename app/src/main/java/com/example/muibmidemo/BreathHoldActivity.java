package com.example.muibmidemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class BreathHoldActivity extends AppCompatActivity {
    private TextView timerTextView, resultTextView;
    private LinearProgressIndicator progressBar;
    private Button startButton, stopButton;
    private CountDownTimer countDownTimer;
    private int timeLeftInSeconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_hold);

        // Set up the toolbar with back navigation
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Enable the back button

        // Initialize views
        timerTextView = findViewById(R.id.timerTextView);
        progressBar = findViewById(R.id.progressBar);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resultTextView = findViewById(R.id.resultTextView);

        startButton.setOnClickListener(v -> startBreathHold());
        stopButton.setOnClickListener(v -> stopBreathHold());

        stopButton.setEnabled(false); // Initially disable the stop button
    }

    // Handle the toolbar back button press
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Close the current activity and go back to the previous one
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startBreathHold() {
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        timeLeftInSeconds = 0; // Reset the time
        progressBar.setMax(60);
        progressBar.setProgress(0);
        progressBar.setIndicatorColor(Color.GREEN); // Default color

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInSeconds++;
                timerTextView.setText("Timer: " + timeLeftInSeconds + "s");
                progressBar.setProgress(timeLeftInSeconds);
                updateProgressBarColor(timeLeftInSeconds);
            }

            @Override
            public void onFinish() {
                // Behavior when the timer finishes
            }
        }.start();
    }

    private void updateProgressBarColor(int seconds) {
        if (seconds < 20) {
            progressBar.setIndicatorColor(Color.RED); // Under 20 seconds, red
        } else if (seconds < 30) {
            progressBar.setIndicatorColor(Color.YELLOW); // Between 20 and 30 seconds, yellow
        } else {
            progressBar.setIndicatorColor(Color.GREEN); // Over 30 seconds, green
        }
    }

    private void stopBreathHold() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        startButton.setEnabled(true);
        stopButton.setEnabled(false);

        String result;
        if (timeLeftInSeconds < 20) {
            result = "Lungs not healthy. Please check.";
        } else if (timeLeftInSeconds < 30) {
            result = "Normal.";
        } else if (timeLeftInSeconds < 60) {
            result = "Good lungs.";
        } else {
            result = "Excellent!";
        }

        resultTextView.setText(result);
    }
}
