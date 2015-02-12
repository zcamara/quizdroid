package zcamara.washington.edu.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class QuestionOverview extends ActionBarActivity {

    private String[] descriptions = {"Love math? Were you that kid in high school that solved math puzzles in your free time? Get an 800 on the math portion of the SAT? If you answered yes to any of these questions, you'll love these simple math questions. No calculators allowed!"
                                    ,"Physics: the branch of science concerned with the nature and properties of matter and energy. The subject matter of physics, distinguished from that of chemistry and biology, includes mechanics, heat, light and other radiation, sound, electricity, magnetism, and the structure of atoms. Test your random physics knowledge here!"
                                    ,"Marvel counts among its characters such well-known properties as Spider-Man, Wolverine, Iron Man, the Hulk, Thor, Captain America, the Silver Surfer, Daredevil, and Ghost Rider, such teams as the Avengers, the Fantastic Four, the Guardians of the Galaxy, and X-Men. Most of Marvel's fictional characters operate in a single reality known as the Marvel Universe. How well do you know Marvel characters?"};

    private TextView topic;
    private TextView description;
    private TextView numQ;
    private Button startBtn;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_overview);

        Intent launchedMe = getIntent();
        final int topicID = launchedMe.getIntExtra("questionSet", 0);
        mBundle = launchedMe.getBundleExtra("mBundle");

        String[] questions = mBundle.getStringArray("questions");

        topic = (TextView) findViewById(R.id.topic);
        description = (TextView) findViewById(R.id.description);
        startBtn = (Button) findViewById(R.id.beginbtn);
        numQ = (TextView) findViewById(R.id.numQ);

        topic.setText(launchedMe.getStringExtra("topic"));
        description.setText(descriptions[topicID]);
        numQ.setText(questions.length+" Questions");

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(QuestionOverview.this, QuestionPage.class);
                nextActivity.putExtra("mBundle", mBundle);
                startActivity(nextActivity);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_overview, menu);
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
