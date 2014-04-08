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

import java.util.ArrayList;

public class QuestPage extends Activity {

    ArrayList<Quest> questList = new ArrayList<Quest>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_page);

        ListView lQuestList = (ListView)findViewById(R.id.questList);

        // Init the List
        createQuestList(lQuestList);

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
        ListView lQuestList = (ListView)findViewById(R.id.questList);
        // Init the List
        createQuestList(lQuestList);

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

    public void createQuestList(ListView lQuestList)
    {
        // init quest list
        loadQuestList();
        filterAlignment();
        QuestAdapter questListAdapater = new QuestAdapter(this, questList);

        lQuestList.setAdapter(questListAdapater);

    }

    private void loadQuestList()
    {
        // Load the quest into a list
        questList.clear();
        questList.add(new Quest("Bandits in the Woods", "HotDogg The Bounty Hunter", "A Hug",
                "The famed bounty hunter HotDog has requested the aid of a hero in ridding the woods of terrifying bandits who have thus far eluded his capture, as he is actually a dog, and cannot actually grab things more than 6 feet off the ground.",
                "Good", new gpsCords(46.908588f,  -96.808991f), new gpsCords(46.8541979f,  -96.8285138f)));
        questList.add(new Quest("Special Delivery", "Sir Jimmy The Swift", "A Dollar",
                "Sir Jimmy was once the fastest man in the kingdom, brave as any soldier and wise as a king. Unfortunately, age catches us all in the end, and he has requested that I, his personal scribe, find a hero to deliver a package of particular importance--and protect it with their life.",
                "Neutral", new gpsCords(46.8657639f,  -96.7363173f), new gpsCords(46.8739748f,  -96.806112f)));
        questList.add(new Quest("Filthy Mongrel", "Prince Jack, The Iron Horse", "1000 Gold",
                "That strange dog that everyone is treating like a bounty-hunter must go. By the order of Prince Jack, that smelly, disease ridden mongrel must be removed from our streets by any means necessary. He is disrupting the lives of ordinary citizens, and it's just really weird. Make it gone.",
                "Evil", new gpsCords(46.892386f,  -96.799669f), new gpsCords(46.8739748f,  -96.806112f)));
    }

    public void filterAlignment()
    {
        SharedPreferences settings = getSharedPreferences("alignment", 0);
        String alignmentStart = settings.getString("alignment", "Neutral");
        if(!alignmentStart.equals("Neutral"))
        {
            //remove any quests of the wrong alignment
            for(int i = 0; i < questList.size(); i++)
            {
                if(!questList.get(i).getAlignment().equals(alignmentStart))
                {
                    // remove from list
                    questList.remove(i);
                    i --;
                }
            }
        }
    }

}
