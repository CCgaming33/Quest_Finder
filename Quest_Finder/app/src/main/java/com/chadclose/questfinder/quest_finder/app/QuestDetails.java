package com.chadclose.questfinder.quest_finder.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class QuestDetails extends Activity {

    private LatLng lObj;
    private LatLng lGiv;
    private boolean initialZoom = false;
    private Quest loadQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_details);


        // Get Quest
        Intent lIntent = getIntent();
        loadQuest = (Quest)lIntent.getSerializableExtra("quest");

        //Text views
        TextView lQuestName = (TextView)findViewById(R.id.questName);
        TextView lPostedBy = (TextView)findViewById(R.id.postedBy);
        TextView lDescription = (TextView)findViewById(R.id.description);

        if(loadQuest!=null)
        {
            lQuestName.setText(loadQuest.getTitle());
            lPostedBy.setText(loadQuest.getCreatedBy());
            lDescription.setText(loadQuest.getDescription());
        }else{
            lQuestName.setText("ERROR");
            lPostedBy.setText("ERROR");
            lDescription.setText("ERROR");
        }


        // Create Markers
        GoogleMap aMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapView)).getMap();

        lObj = new LatLng(loadQuest.getQuestObjectiveLat(), loadQuest.getQuestObjectiveLon());
        Marker questObjective = aMap.addMarker(new MarkerOptions().position(lObj).title("Quest Objective"));

        aMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                // Auto Zoom to markers
                if(!initialZoom) {
                    initialZoom = true;
                    GoogleMap aMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();

                    LatLngBounds bounds = LatLngBounds.builder().include(lObj).build();
                    CameraPosition aPos = CameraPosition.builder().target(lObj).zoom(aMap.getMaxZoomLevel ()).build();
                    CameraUpdate aUpdate = CameraUpdateFactory.newCameraPosition(aPos);

                    aMap.animateCamera(aUpdate);
                }


            }
        });

        // Set action button Text
        setActionButton();


    }

    private void setActionButton()
    {
        Button lQuestList = (Button)findViewById(R.id.questAction);
        int stage = loadQuest.getFilterLevel();
            /* stage
            0: Available
            1: Accepted
            2: Completed
            3: Nothing
            */

        // Available
        if(stage == 0)
            lQuestList.setText("Accept");

        // Accepted
        if(stage == 1)
            lQuestList.setText("Complete");

        // Completed/Nothing
        if(stage == 2 || stage == 3)
            lQuestList.setVisibility(4);

    }

    public void onActionClick(View view)
    {
        // Based on stage perform the action
        int stage = loadQuest.getFilterLevel();
            /* stage
            0: Available
            1: Accepted
            2: Completed
            3: Nothing
            */
        if(stage == 0 | stage == 1)
        {
            // Load the quest Object
            ParseObject questObj = new ParseObject("Quests");
            questObj.setObjectId(loadQuest.getObjectId());
            try {
                questObj.fetch();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Perform Action
            if(stage == 0)
            {
                // Accept the quest
                questObj.put("acceptedBy", ParseUser.getCurrentUser());
            }else{
                // Complete the quest
                questObj.put("completed", true);
            }
            // Update Server
            questObj.saveInBackground();
            loadQuest = new Quest(questObj);
            setActionButton();
        }

    }



}
