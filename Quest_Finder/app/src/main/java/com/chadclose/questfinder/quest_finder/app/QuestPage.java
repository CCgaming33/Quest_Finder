package com.chadclose.questfinder.quest_finder.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class QuestPage extends Activity {

    ArrayList<Quest> questList = new ArrayList<Quest>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_page);

        ListView lQuestList = (ListView)findViewById(R.id.questList);
                lQuestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {

                        // Pass the quest
                        Quest lQuestToPass = questList.get(position);
                        Intent intent = new Intent(QuestPage.this, QuestDetails.class);
                        intent.putExtra("quest", lQuestToPass);
                        startActivity(intent);
                    }
                });

    }

    @Override
    protected void onResume(){
        super.onResume();

        // Init the List
        loadQuestList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quest_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            // Open Settings Page
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadQuestList()
    {
        clearQuestList();
        // First Load Objects For Parse
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Quests");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    createQuestList(objects);
                } else {
                    //objectRetrievalFailed();
                }
            }
        });
    }

    private void clearQuestList()
    {
        questList.clear();
        // create the quest list from an empty questList
        ListView lQuestList = (ListView)findViewById(R.id.questList);
        filterAlignment();
        QuestAdapter questListAdapater = new QuestAdapter(this, questList);
        lQuestList.setAdapter(questListAdapater);

        // Start Progress Bar
        ProgressBar lProgress = (ProgressBar)findViewById(R.id.progressBar);
        lProgress.setVisibility(0);

    }

    private void createQuestList(List<ParseObject> objects)
    {

        // Add objects into quest list
        for(ParseObject aObj : objects)
            questList.add(new Quest(aObj));

        // create the quest list from questList
        ListView lQuestList = (ListView)findViewById(R.id.questList);
        filterAlignment();
        QuestAdapter questListAdapater = new QuestAdapter(this, questList);
        lQuestList.setAdapter(questListAdapater);

        // Start Progress Bar
        ProgressBar lProgress = (ProgressBar)findViewById(R.id.progressBar);
        lProgress.setVisibility(4);
    }


    public void filterAlignment()
    {
        SharedPreferences settings = getSharedPreferences("alignment", 0);
        String alignmentStart = settings.getString("alignment", "Neutral");
        Integer alignment = ParseUser.getCurrentUser().getInt("alignment");
        if(alignment != 0)
        {
            //remove any quests of the wrong alignment
            for(int i = 0; i < questList.size(); i++)
            {
                if(alignment == 1) {
                    if (!questList.get(i).getAlignment().equals("Good")) {
                        // remove from list
                        questList.remove(i);
                        i--;
                    }
                }else{
                    if (!questList.get(i).getAlignment().equals("Evil")) {
                        // remove from list
                        questList.remove(i);
                        i--;
                    }
                }
            }
        }
    }

}
