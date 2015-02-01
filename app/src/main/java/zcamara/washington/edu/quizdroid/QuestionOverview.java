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

    private String[] descriptions = {""
                                    ,""
                                    ,""};

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
        String[][] questions = (String[][]) mBundle.getSerializable("questions");

        topic = (TextView) findViewById(R.id.topic);
        description = (TextView) findViewById(R.id.description);
        startBtn = (Button) findViewById(R.id.beginbtn);
        numQ = (TextView) findViewById(R.id.numQ);

        topic.setText(launchedMe.getStringExtra("topic"));
        description.setText(descriptions[topicID]);
        numQ.setText(questions[topicID].length+" Questions");

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(QuestionOverview.this, QuestionPage.class);
                nextActivity.putExtra("questionSet", topicID);
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
