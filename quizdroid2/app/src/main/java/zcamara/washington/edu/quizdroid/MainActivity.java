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

public class MainActivity extends ActionBarActivity {

    ListView topics;
    String questions[][]={{"What do you get if you add up the numbers 1-100 consecutively?","If a 10 mile taxi ride costs $12 and a 15 mile ride costs $18, then at the same rate how much would a 24 mile ride cost?","Let's say it takes a bunch of workers 90 days to construct a house. Working at the same rate, how many days would be saved if the number of workers was increased by 25%","Lisa has a collection of 80 CDs. If 40 percent of her CDs are rap,and the rest are hip hop, how many hip hop CDs does she have?","What is the smallest positive number that is divisible by 1, 2, 3, 4, 5, and 6?"},
                          {"A man has a mass of 60 kg on Earth. What will his mass be on the Moon?","What will happen to a fresh egg in concentrated saline?","Who is known as \"the father of quantum theory\"?","A man weighs 120 N on Earth. What will he weigh on the Moon?","What is the maximum number of solar eclipses that can be seen from the Earth in one year?","Which of the following is a scalar quantity?"},
                          {"Peter Parker works as a photographer for:","S.H.I.E.L.D.'s highest ranking agent is:","Dr. Doom went to the same college as:","Edwin Jarvis is the butler to:","Wyatt Wingfoot was a college roommate of","Peter Parker's parents are named:","Tony Stark's father is named:"}
    };
    String answers[][]={{"5050","$28.80","18 days","48 CDs","60"},
                        {"60kg","It will float","Max Planck","20 N","5","Energy"},
                        {"The Daily Bugle","Nick Fury","Reed Richards","Tony Stark","Johnny Storm","Richard and Mary Parker","Howard Stark"}
    };
    String opt[][]={
            {
                    "4980","5050","5210","4762",
                    "$28.80","$26.70","$25.30","$31.20",
                    "22 days","20 days","16 days","18 days",
                    "32 CDs","64 CDs","48 CDs","56 CDs",
                    "60", "120", "30", "180"
            },
            {
                    "90kg","20kg", "60kg", "30kg",
                    "It will float","It will break","It will sink","It will sink slightly and remain at the center of the water",
                    "Otto Hahn", "Max Planck", "Albert Einstein","James Chadwick",
                    "20 N","40 N","60 N","75 N",
                    "10","7","8","5",
                    "Energy","Weight","Acceleration","Displacement"
            },
            {
                    "The Daily Bugle","The New York Times", "The Daily Planet","The Rolling Stone",
                    "Steven Rogers","Nick Fury","Natalia Romanova","Reed Richards",
                    "Tony Stark","Bruce Banner", "Peter Parker","Reed Richards",
                    "Charles Xavier","Nick Fury","Tony Stark","Brian Baddock",
                    "Johnny Storm","Reed Richards", "James Howlett","Peter Parker",
                    "Martha and Johnathon Parker","William and Jessica Parker","Thomas and Margaret Parker","Richard and Mary Parker",
                    "Howard Stark","Tyler Stark", "Joseph Stark","Samuel Stark"
            }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get ListView object from xml
        topics = (ListView) findViewById(R.id.topics);
        // Defined Array values to show in ListView
        String[] values = new String[] {
                "Math",
                "Physics",
                "Marvel Super Heroes"
        };
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
                // ListView Clicked item index
                int itemPosition     = position;
                // ListView Clicked item value
                String  itemValue    = (String) topics.getItemAtPosition(position);
                Intent nextActivity = new Intent(MainActivity.this, fragmentActivity.class);
                nextActivity.putExtra("questionSet", itemPosition);
                nextActivity.putExtra("qNum", 0);
                nextActivity.putExtra("correct", 0);
                nextActivity.putExtra("wrong", 0);
                Bundle mBundle = new Bundle();
                mBundle.putStringArray("questions", questions[itemPosition]);
                mBundle.putStringArray("answers", answers[itemPosition]);
                mBundle.putStringArray("opt", opt[itemPosition]);
                nextActivity.putExtra("mBundle", mBundle);
                nextActivity.putExtra("topic", itemValue);
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

