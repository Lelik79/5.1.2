package com.example.a512;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SettingsActivity extends AppCompatActivity {
    static final String IMAGE_RESULT_KEY = "IMAGE_RESULT_KEY";
    private static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 100;
    private EditText fileInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        fileInput = findViewById(R.id.editText);
        findViewById(R.id.setBgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });
    }
    private void checkPermissions() {
        int permissionStatus = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            loadImg();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_WRITE_STORAGE
            );
        }
    }
    private void loadImg() {
        if (isExternalStorageWritable()) {
            String fileName = fileInput.getText().toString();
            if (TextUtils.isEmpty(fileName)) {
                Toast.makeText(this, "Введите имя файла.", Toast.LENGTH_SHORT).show();
                return;
            }
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), fileName);
            if (!file.exists()) {
                Toast.makeText(this, "Файл с указанным именем не найден.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent result = new Intent();
            result.putExtra(IMAGE_RESULT_KEY, file);
            setResult(RESULT_OK, result);
            finish();
        }
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_WRITE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImg();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(SettingsActivity.this, "Необходимо разрешение на чтение фалов", Toast.LENGTH_SHORT).show();
            } else  {
                Toast.makeText(SettingsActivity.this, "Включите разрешение на чтение файлов в настройках", Toast.LENGTH_SHORT).show();
            }
        }
    }
}