package com.chadclose.questfinder.quest_finder.app;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Chad on 4/8/14.
 */
public class Quest implements Serializable{

    private String title;
    private String createdBy;
    private String reward;
    private String description;
    private String alignment;
    private gpsCords questObjective;
    private gpsCords questGiver;

    public Quest()
    {
        title = "NA";
        createdBy = "NA";
        reward = "NA";
        description = "NA";
        alignment = "Neutral";
    }

    public Quest(String aTitle, String aCreatedBy, String aReward, String aDescription, String aAlignment, gpsCords objective, gpsCords giver)
    {
        title = aTitle;
        createdBy = aCreatedBy;
        reward = aReward;
        description = aDescription;
        alignment = aAlignment;
        questObjective = objective;
        questGiver = giver;
    }

    public String getTitle()
    {
        return title;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public String getDescription()
    {
        return description;
    }

    public String getReward()
    {
        return reward;
    }

    public String getAlignment()
    {

        return alignment;
    }

    public gpsCords getQuestObjective()
    {
        return questObjective;
    }

    public gpsCords getQuestGiver()
    {
        return questGiver;
    }
}
