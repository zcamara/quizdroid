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
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private ListView topics;

    @Override
    protected void onDestroy() {
        if(this.isFinishing()) {
            QuizApp.getInstance().killAlarm();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.i(prefURL, prefFreq);
        // Get ListView object from xml
        topics = (ListView) findViewById(R.id.topics);
        // Defined Array values to show in ListView
        ArrayList<String> values = new ArrayList<String>();
        ArrayList<Integer> icons = new ArrayList<Integer>();
        for (Topic topic : QuizApp.getInstance().getRepo().getRepo()) {
            values.add(topic.getTitle() + ": " + topic.getShortDesc());
            icons.add(topic.getIcon());
        }
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        CustomList adapter = new CustomList(MainActivity.this, values, icons);

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);*/
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

        if(item.toString().equals("Preferences")) {//preferences
            Intent i = new Intent(MainActivity.this, preferenceActivity.class);
            startActivity(i);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class CustomList extends ArrayAdapter<String>{
        private final Activity context;
        private final ArrayList<String> web;
        private final ArrayList<Integer> imageId;
        public CustomList(Activity context,
                          ArrayList<String> web, ArrayList<Integer> imageId) {
            super(context, R.layout.list_single, web);
            this.context = context;
            this.web = web;
            this.imageId = imageId;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(web.get(position));
            imageView.setImageResource(imageId.get(position));
            return rowView;
        }
    }

}

