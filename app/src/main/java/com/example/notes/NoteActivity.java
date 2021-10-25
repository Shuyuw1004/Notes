package com.example.notes;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // 1. Get noteEditText view
        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);

        // 2. Get intent
        Intent intent = getIntent();

        // 3. Get the value of integer noteid from intent and initialize noteid
        noteid = intent.getIntExtra("noteid", noteid);

        if (noteid != -1) {
            // Display content of note by retrieving from "notes"
            Note note = SecondActivity.notes.get(noteid);
            String noteContent = note.getContent();
            noteEditText.setText(noteContent);
        }

    }

    public void onSave(View view) {
        // 1. Get noteEditText view and the content that user entered
        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        String content = noteEditText.getText().toString();

        // 2. Initialize SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        // 3. Initialize DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 4. Set username by fetching it from SharePreferences
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(usernameKey, "");

        // 5. Save information to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            // Add note
            title = "NOTE_" + (SecondActivity.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            // Update note
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        // 6. Go to second activity
        goToLoginActivity();
    }

    public void goToLoginActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}