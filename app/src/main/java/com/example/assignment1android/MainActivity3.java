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
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity3 extends AppCompatActivity {
    private EditText editText;
    private Button addButton;
    private LinearLayout notesContainer;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);
        editText = findViewById(R.id.editText);
        addButton = findViewById(R.id.addButton);
        notesContainer = findViewById(R.id.notesContainer);

        addButton.setOnClickListener(v -> {
            String note = editText.getText().toString();
            if (!note.isEmpty()) {
                addNoteToView(note);
                editText.setText("");
                saveNote(note);
            }
        });
        loadNotes();
    }

    private void addNoteToView(String note) {
        TextView textView = new TextView(this);
        textView.setText(note);
        textView.setPadding(20, 20, 20, 20);
        notesContainer.addView(textView);
    }

    private void saveNote(String note) {
        // Get existing notes, append the new note, and save
        String existingNotes = sharedPreferences.getString("savedNotes", "");
        sharedPreferences.edit().putString("savedNotes", existingNotes + note + ";").apply();
    }

    private void loadNotes() {
        notesContainer.removeAllViews();
        String savedNotes = sharedPreferences.getString("savedNotes", "");
        if (savedNotes != null) {
            String[] notesArray = savedNotes.split(";");
            for (String note : notesArray) {
                if (!note.isEmpty()) {
                    addNoteToView(note);
                }
            }
        }
    }
}
