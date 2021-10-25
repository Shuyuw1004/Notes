package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    TextView welcomeTextView;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 1. Display welcome message. Fetch username from SharedPreferences
        welcomeTextView = (TextView) findViewById(R.id.textView2);

        // get username using shared preferences
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(usernameKey, "");
        welcomeTextView.setText("Welcome " + username + "!");

        // 2. Get SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        // 3. Initialize the notes class variable
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);

        // 4. Create an ArrayList<String> object by iterating over notes object
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
            // 5. User ListView view to display notes on screen
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            // 6. Add onItemClickListener for ListView item, a note in our case
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Initialize intent to take user to the NoteActivity
                    Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                    // Add the position of item that was clicked on as "noteid"
                    intent.putExtra("noteid", position);
                    startActivity(intent);
                }
            });
        }
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.example_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.Logout:
                    // erase username from shared preferences
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
                    String usernameKey = "username";
                    sharedPreferences.edit().remove(usernameKey).apply();
                    goToMainActivity();
                    return true;

                case R.id.AddN:
                    goToNoteActivity();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        public void goToMainActivity() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        public void goToNoteActivity() {
            Intent intent = new Intent(this, NoteActivity.class);
            startActivity(intent);
        }

    }