package zcamara.washington.edu.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    ListView topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get ListView object from xml
        topics = (ListView) findViewById(R.id.topics);
        // Defined Array values to show in ListView
        ArrayList<String> values = new ArrayList<String>();
        for (Topic topic : QuizApp.getInstance().getRepo().getRepo()) {
            values.add(topic.getTitle() + ": " + topic.getShortDesc());
        }
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        // Assign adapter to ListView
        topics.setAdapter(adapter);
        // ListView Item Click Listener
        topics.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item value
                String[] topicNames = QuizApp.getInstance().getRepo().getTopics();
                Intent nextActivity = new Intent(MainActivity.this, fragmentActivity.class);
                nextActivity.putExtra("topic",topicNames[position]);
                nextActivity.putExtra("qNum", 0);
                nextActivity.putExtra("correct", 0);
                nextActivity.putExtra("wrong", 0);
                startActivity(nextActivity);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

