package com.example.muibmidemo;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import com.google.android.material.appbar.MaterialToolbar;

public class GameActivity extends AppCompatActivity {

    private View movingObject;
    private TextView scoreTextView;
    private Button backToMainButton;
    private int score = 0;
    private Handler handler = new Handler();
    private Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Set up the toolbar with back navigation
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Enable the back button


        // Khởi tạo các view
        movingObject = findViewById(R.id.movingObject);
        scoreTextView = findViewById(R.id.scoreTextView);

        // Khi người dùng chạm vào đối tượng
        movingObject.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Tăng điểm khi chạm
                    score++;
                    scoreTextView.setText("Score: " + score);
                    // Di chuyển đối tượng đến vị trí khác
                    moveObject();
                }
                return true;
            }
        });

        // Handle the toolbar back button press
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                finish();  // Close the current activity and go back to the previous one
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        // Xử lý khi nhấn nút "Quay về màn hình chính"
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng GameActivity
        });

        // Di chuyển đối tượng lần đầu tiên
        moveObject();
    }

    // Hàm di chuyển đối tượng đến vị trí ngẫu nhiên
    private void moveObject() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Lấy kích thước màn hình để di chuyển đối tượng
                int x = random.nextInt(getWindowManager().getDefaultDisplay().getWidth() - movingObject.getWidth());
                int y = random.nextInt(getWindowManager().getDefaultDisplay().getHeight() - movingObject.getHeight());

                // Đặt vị trí mới cho đối tượng
                movingObject.setX(x);
                movingObject.setY(y);
            }
        });
    }
}
