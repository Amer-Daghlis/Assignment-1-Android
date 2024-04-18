package com.example.assignment1android;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private EditText etCategory;
    private EditText etDescription;
    private Button btnAdd;
    private TextView tvDataDisplay;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etCategory = findViewById(R.id.etCategory);
        etDescription = findViewById(R.id.etDescription);
        btnAdd = findViewById(R.id.btnAdd);
        tvDataDisplay = findViewById(R.id.tvDataDisplay);
        sharedPreferences = getSharedPreferences("HistoryData", MODE_PRIVATE);

        btnAdd.setOnClickListener(v -> saveData());

        loadData();  // Load data initially when the activity starts
    }

    private void loadData() {
        StringBuilder dataDisplay = new StringBuilder();
        String[] categories = new String[]{"Ancient History", "World Wars", "American History"};

        dataDisplay.append("Ancient History\n");
        dataDisplay.append("* The Roman Empire, At its zenith, the Roman Empire covered much of Europe, North Africa, and the Middle East.\n\n");
        dataDisplay.append("World Wars\n");
        dataDisplay.append("* World War I, World War I lasted from 1914 to 1918 and involved many of the world's nations.\n\n");
        dataDisplay.append("American History\n");
        dataDisplay.append("* Declaration of Independence, The Declaration of Independence was adopted on July 4, 1776.\n\n");

        // Load additional data from SharedPreferences
        for (String category : categories) {
            String data = sharedPreferences.getString(category, null);
            if (data != null) {
                dataDisplay.append(category).append("\n").append(data).append("\n");
            }
        }

        // Optionally load dynamic categories
        java.util.Map<String, ?> allEntries = sharedPreferences.getAll();
        for (java.util.Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (!java.util.Arrays.asList(categories).contains(entry.getKey())) {
                dataDisplay.append(entry.getKey()).append("\n").append(entry.getValue()).append("\n");
            }
        }

        tvDataDisplay.setText(dataDisplay.toString());
    }

    private void saveData() {
        String category = etCategory.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (!category.isEmpty() && !description.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String existingData = sharedPreferences.getString(category, null);

            if (existingData == null) {
                // No existing data, create new category
                editor.putString(category, description + "\n");
            } else {
                // Existing data found, append new description
                editor.putString(category, existingData + description + "\n");
            }
            editor.apply();

            // Clear the fields
            etCategory.setText("");
            etDescription.setText("");
            loadData(); // Reload the data
        }
    }
}
