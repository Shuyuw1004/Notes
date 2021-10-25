package com.example.notes;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public void onButtonClick(View view) {
        EditText usernameTextField = (EditText) findViewById(R.id.etName);

        String str = usernameTextField.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", str).apply();

        goToSecondActivity(str);
    }

    public void goToSecondActivity(String s) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("username", s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")) {
            String currUsername = sharedPreferences.getString("username", "");
            goToSecondActivity(currUsername);
        } else {
            setContentView(R.layout.activity_main);
        }

    }


}