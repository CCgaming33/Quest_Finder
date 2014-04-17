package com.chadclose.questfinder.quest_finder.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // load username
        String username = ParseUser.getCurrentUser().getString("name");
        EditText lUsername = (EditText) findViewById(R.id.editName);
        lUsername.setText(username);

        // Populate Spinner
        Spinner spinner = (Spinner) findViewById(R.id.alignment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alignment, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Load Alignment
        int lStartPosition = ParseUser.getCurrentUser().getInt("alignment");
        spinner.setSelection(lStartPosition);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // update alignment
                ParseUser yourUser = ParseUser.getCurrentUser();
                yourUser.put("alignment", pos);
                yourUser.saveInBackground();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        // changing name
        EditText nameChange = (EditText)findViewById(R.id.editName);

        nameChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                // Update name
                ParseUser yourUser = ParseUser.getCurrentUser();
                yourUser.put("name", charSequence.toString());
                yourUser.saveInBackground();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    public void updateLocationClick(View view)
    {
        // Update the users lat and long
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            ParseUser yourUser = ParseUser.getCurrentUser();
            yourUser.put("location", new ParseGeoPoint(latitude, longitude));
            yourUser.saveInBackground();
        }
    }


}
