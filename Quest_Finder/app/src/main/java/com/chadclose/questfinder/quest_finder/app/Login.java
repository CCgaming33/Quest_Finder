package com.chadclose.questfinder.quest_finder.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Load preferences
        boolean rememberUser = loadRememberPassword();
        CheckBox rememberUserBox = (CheckBox) findViewById(R.id.rememberUsername);
        rememberUserBox.setChecked(rememberUser);

        // If its checked, load username
        if(rememberUser)
            loadUserName();

        rememberUserBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                saveRememberPassword(isChecked);

            }
        });
    }

    public void onLoginButtonPressed(View view)
    {
        // Check for the username to be Lancelot and
        // the password to be arthurDoesntKnow
        EditText lUsername = (EditText)findViewById(R.id.username);
        EditText lPassword = (EditText)findViewById(R.id.password);
        if(lUsername.getText().toString().equals("Lancelot"))
        {
            if(lPassword.getText().toString().equals("arthurDoesntKnow"))
            {
                saveUserName(lUsername.getText().toString());
                // Go to QuestPage
                Intent intent = new Intent(this, QuestPage.class);
                startActivity(intent);
            }else{
                // Password is incorrect
                lPassword.setError("Incorrect");
            }
        }else{
            // User is incorrect
            lUsername.setError("Incorrect");
        }
        //Remove
        Intent intent = new Intent(this, QuestPage.class);
        startActivity(intent);
    }

    public void loadUserName()
    {
        // Load the saved username and set the username field to the username
        SharedPreferences settingsUser = getSharedPreferences("username", 0);
        String username = settingsUser.getString("username", "");
        EditText lUsername = (EditText) findViewById(R.id.username);
        lUsername.setText(username);
    }

    public boolean loadRememberPassword()
    {
        // Return the remember password preference
        SharedPreferences settings = getSharedPreferences("rememberUser", 0);
        boolean rememberUser = settings.getBoolean("rememberUser", false);
        return rememberUser;
    }

    public void saveRememberPassword(boolean isChecked)
    {
        // Save the selection
        SharedPreferences settings = getSharedPreferences("rememberUser", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("rememberUser", isChecked);
        // Commit the edits!
        editor.commit();
    }

    public void saveUserName(String aUserName)
    {
        // Save the selection
        SharedPreferences settingsUser = getSharedPreferences("username", 0);
        SharedPreferences.Editor editor = settingsUser.edit();
        editor.putString("username", aUserName);
        // Commit the edits!
        editor.commit();
    }





}
