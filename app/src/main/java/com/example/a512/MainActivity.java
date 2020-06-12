package com.example.a512;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int SETTINGS_REQUEST_CODE = 1;
    private ImageView background;
    private TextView textViewInputNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = findViewById(R.id.imageView);

        textViewInputNumbers = findViewById(R.id.textViewInputNumbers);

        findViewById(R.id.textViewButton0).setOnClickListener(listener);
        findViewById(R.id.textViewButton1).setOnClickListener(listener);
        findViewById(R.id.textViewButton2).setOnClickListener(listener);
        findViewById(R.id.textViewButton3).setOnClickListener(listener);
        findViewById(R.id.textViewButton4).setOnClickListener(listener);
        findViewById(R.id.textViewButton5).setOnClickListener(listener);
        findViewById(R.id.textViewButton6).setOnClickListener(listener);
        findViewById(R.id.textViewButton7).setOnClickListener(listener);
        findViewById(R.id.textViewButton8).setOnClickListener(listener);
        findViewById(R.id.textViewButton9).setOnClickListener(listener);
        findViewById(R.id.textViewButtonDot).setOnClickListener(listener);

        Button textViewButtonC = findViewById(R.id.textViewButtonC);
        textViewButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewInputNumbers.setText("");

            }
        });

        Button btnSet = findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        SettingsActivity.class);
                startActivityForResult(intent, SETTINGS_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE && data != null) {
            File file = (File) data.getSerializableExtra(SettingsActivity.IMAGE_RESULT_KEY);
            Objects.requireNonNull(file);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            background.setImageBitmap(bitmap);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            textViewInputNumbers.append(button.getText());
        }
    };
}