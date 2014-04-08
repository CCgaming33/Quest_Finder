package com.chadclose.questfinder.quest_finder.app;
import java.io.Serializable;

/**
 * Created by Chad on 4/8/14.
 */
public class gpsCords implements Serializable{

    private float lat;
    private float lon;

    public gpsCords(float aLat, float aLon)
    {
        lat = aLat;
        lon = aLon;
    }

    public float getLat()
    {
        return lat;
    }

    public float getLon()
    {
        return lon;
    }


}
