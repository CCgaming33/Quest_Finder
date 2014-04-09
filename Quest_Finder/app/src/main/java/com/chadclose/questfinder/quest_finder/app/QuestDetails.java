package com.chadclose.questfinder.quest_finder.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


public class QuestDetails extends Activity {

    private LatLng lObj;
    private LatLng lGiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_details);


        // Get Quest
        Intent lIntent = getIntent();
        Quest lQuest = (Quest)lIntent.getSerializableExtra("quest");

        //Text views
        TextView lQuestName = (TextView)findViewById(R.id.questName);
        TextView lPostedBy = (TextView)findViewById(R.id.postedBy);
        TextView lReward = (TextView)findViewById(R.id.rewardInfo);
        TextView lDescription = (TextView)findViewById(R.id.description);

        if(lQuest!=null)
        {
            lQuestName.setText(lQuest.getTitle());
            lPostedBy.setText(lQuest.getCreatedBy());
            lReward.setText(lQuest.getReward());
            lDescription.setText(lQuest.getDescription());
        }else{
            lQuestName.setText("ERROR");
            lPostedBy.setText("ERROR");
            lReward.setText("ERROR");
            lDescription.setText("ERROR");
        }


        // Create Markers
        GoogleMap aMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapView)).getMap();

        lObj = new LatLng(lQuest.getQuestObjective().getLat(), lQuest.getQuestObjective().getLon());
        Marker questObjective = aMap.addMarker(new MarkerOptions().position(lObj).title("Quest Objective"));

        lGiv = new LatLng(lQuest.getQuestGiver().getLat(), lQuest.getQuestGiver().getLon());
        Marker questGiver = aMap.addMarker(new MarkerOptions().position(lGiv).title("Quest Giver"));

        aMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                // Auto Zoom to markers
                GoogleMap aMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapView)).getMap();

                LatLngBounds bounds = LatLngBounds.builder().include(lGiv).include(lObj).build();

                CameraUpdate aUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100);;
                aMap.animateCamera(aUpdate);



            }
        });
    }


}
