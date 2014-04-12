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
    private boolean initialZoom = false;

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
        TextView lDescription = (TextView)findViewById(R.id.description);

        if(lQuest!=null)
        {
            lQuestName.setText(lQuest.getTitle());
            lPostedBy.setText(lQuest.getCreatedBy());
            lDescription.setText(lQuest.getDescription());
        }else{
            lQuestName.setText("ERROR");
            lPostedBy.setText("ERROR");
            lDescription.setText("ERROR");
        }


        // Create Markers
        GoogleMap aMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapView)).getMap();

        lObj = new LatLng(lQuest.getQuestObjectiveLat(), lQuest.getQuestObjectiveLon());
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
    }


}
