package com.chadclose.questfinder.quest_finder.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class QuestDetails extends Activity {

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

        LatLng lObj = new LatLng(lQuest.getQuestObjective().getLat(), lQuest.getQuestObjective().getLon());
        Marker questObjective = aMap.addMarker(new MarkerOptions().position(lObj).title("Quest Objective"));

        LatLng lGiv = new LatLng(lQuest.getQuestGiver().getLat(), lQuest.getQuestGiver().getLon());
        Marker questGiver = aMap.addMarker(new MarkerOptions().position(lGiv).title("Quest Giver"));



    }


}
