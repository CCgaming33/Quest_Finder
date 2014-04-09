package com.chadclose.questfinder.quest_finder.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // load username
        loadUserName();

        // Populate Spinner
        Spinner spinner = (Spinner) findViewById(R.id.alignment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alignment, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Load preferences
        SharedPreferences settings = getSharedPreferences("alignment", 0);
        String alignmentStart = settings.getString("alignment", "Neutral");
        int lStartPosition = adapter.getPosition(alignmentStart);
        spinner.setSelection(lStartPosition);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {



            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                // Save the selection
                SharedPreferences settings = getSharedPreferences("alignment", 0);
                SharedPreferences.Editor editor = settings.edit();
                Spinner spinner = (Spinner) findViewById(R.id.alignment);
                editor.putString("alignment", spinner.getSelectedItem().toString());
                // Commit the edits!
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

    }


    public void loadUserName()
    {
        SharedPreferences settingsUser = getSharedPreferences("username", 0);
        String username = settingsUser.getString("username", "");
        EditText lUsername = (EditText) findViewById(R.id.editName);
        lUsername.setText(username);
    }


}
