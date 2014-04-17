package com.chadclose.questfinder.quest_finder.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class signUp extends Activity {

    private ImageView aPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // Populate Spinner
        Spinner spinner = (Spinner) findViewById(R.id.alignmentChoice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alignment, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        aPhoto = (ImageView)findViewById(R.id.Photo);



    }


    public void onTakePhoto(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 0)
        {
            // Get bitmap
            Bitmap aPicture = (Bitmap)data.getExtras().get("data");
            // assign the photo to the imageview to display
            aPhoto.setImageBitmap(aPicture);
            // save the photo for future use

        }
    }


    public void onSubmitClick(View view)
    {
        // get fields
        String lUsername = ((EditText)findViewById(R.id.usernameCreate)).getText().toString();
        String lPassword = ((EditText)findViewById(R.id.passwordCreate)).getText().toString();
        String lName = ((EditText)findViewById(R.id.nameCreate)).getText().toString();
        Integer lAlignment = ((Spinner) findViewById(R.id.alignmentChoice)).getSelectedItemPosition();
        ParseUser aNewUser = new ParseUser();
        aNewUser.setUsername(lUsername);
        aNewUser.setPassword(lPassword);
        aNewUser.put("name", lName);
        aNewUser.put("alignment", lAlignment);
        Log.w("User", lUsername + ", " + lPassword + ", " + lName + "," + lAlignment);

        aNewUser.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Go to quest page
                    goToQuestPage();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.w("Sign Up", "Sign Up Fail");
                }
            }
        });

    }

    public void goToQuestPage()
    {
        Intent intent = new Intent(this, QuestPage.class);
        startActivity(intent);
    }

}
