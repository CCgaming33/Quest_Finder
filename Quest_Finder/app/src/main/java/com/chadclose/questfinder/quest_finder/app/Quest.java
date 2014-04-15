package com.chadclose.questfinder.quest_finder.app;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Chad on 4/8/14.
 */
public class Quest implements Serializable {

    private String objId;
    private String acceptedBy;
    private int alignment;
    private boolean completed;
    private String description;
    private float questObjectiveLat;
    private float questObjectiveLon;
    private String title;
    private String questGiver;
    private String questGiverIconURL;
    private String questLocationImageURL;


    public Quest(ParseObject aObj)
    {
        objId = aObj.getObjectId();
        // Quest Accepted By name
        ParseUser aAcceptedBy =  aObj.getParseUser("acceptedBy");
        if(aAcceptedBy != null) {
            try {
                aAcceptedBy.fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            acceptedBy = aAcceptedBy.getString("name");
        }

        alignment = aObj.getInt("alignment");
        completed = aObj.getBoolean("completed");
        description = aObj.getString("description");

        // Quest Location
        ParseGeoPoint aTempPoint = aObj.getParseGeoPoint("location");
        questObjectiveLat = (float)aTempPoint.getLatitude();
        questObjectiveLon = (float)aTempPoint.getLongitude();

        title = aObj.getString("name");

        // Quest Giver name
        ParseUser aQuestGiver =  aObj.getParseUser("questGiver");
        try {
            aQuestGiver.fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        questGiver = aQuestGiver.getString("name");
        questGiverIconURL = aQuestGiver.getString("imageUrl");
        questLocationImageURL =  aObj.getString("locationImageUrl");
    }

    public String getTitle()
    {
        return title;
    }

    public String getCreatedBy()
    {
        return questGiver;
    }

    public String getDescription()
    {
        return description;
    }

    public String getAlignment()
    {
        if(alignment == 1)
            return "Good";
        if(alignment == 2)
            return "Evil";
        return "Neutral";
    }

    public float getQuestObjectiveLat()
    {
        return questObjectiveLat;

    }

    public float getQuestObjectiveLon()
    {
        return questObjectiveLon;

    }

    public int getFilterLevel()
    {
            /* Flitertype
            0: Available
            1: Accepted
            2: Completed
            3: Not visable
            */
        if(acceptedBy == null)
        {
            if (completed)
                return 2;
            return 0;
        }else{
            // Have you accepted it?
            //if(acceptedBy.equals(ParseUser.getCurrentUser().getString("name")))
            //{
                if (completed) {
                    return 2;
                } else {
                    return 1;
                }
            //}else{
                //return 3;
            //}
        }
    }

    public String getObjectId()
    {
        return objId;
    }

    public String getGiverIconURL() { return questGiverIconURL; }

    public String getQuestLocationImageURL() { return questLocationImageURL; }

}
