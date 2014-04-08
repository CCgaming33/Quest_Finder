package com.chadclose.questfinder.quest_finder.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chad on 4/7/14.
 */
public class QuestAdapter extends ArrayAdapter<Quest> {

    public QuestAdapter(Context context, ArrayList<Quest> users) {
        super(context, R.layout.item_quest, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Quest quest = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_quest, null);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView createdBy = (TextView) convertView.findViewById(R.id.createdBy);
        TextView reward = (TextView) convertView.findViewById(R.id.reward);
        // Populate the data into the template view using the data object
        title.setText(quest.getTitle());
        createdBy.setText("Posted By: " + quest.getCreatedBy());
        reward.setText("Reward: " + quest.getReward());
        // Return the completed view to render on screen
        return convertView;
    }
}