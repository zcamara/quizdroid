package zcamara.washington.edu.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class QuestionResults extends ActionBarActivity {

    private Button nextBtn;
    private int qNum;
    private int correct;
    private int wrong;
    private int numQ;
    private Bundle mBundle;
    private boolean lastQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_results);
        Intent launchedMe = getIntent();
        final int topicID = launchedMe.getIntExtra("questionSet", 0);
        correct = launchedMe.getIntExtra("correct", 0);
        wrong = launchedMe.getIntExtra("wrong", 0);
        numQ = launchedMe.getIntExtra("numQ", 0);
        mBundle = launchedMe.getBundleExtra("mBundle");
        String userAnswer = launchedMe.getStringExtra("userAnswer");
        String answer = launchedMe.getStringExtra("answer");

        TextView correcttxt = (TextView) findViewById(R.id.correct);
        TextView incorrecttxt = (TextView) findViewById(R.id.incorrect);
        TextView answerSummary = (TextView) findViewById(R.id.answersummary);

        if(userAnswer.equals(answer)) {
            answerSummary.setText("Congratulations! " + answer + " was the correct choice!");
            correct++;
        } else {
            wrong++;
            answerSummary.setText("Sorry! You chose: " + userAnswer + " and the correct response was: " + answer);
        }
        int total = correct + wrong;
        qNum = total;
        correcttxt.setText("You have "+correct+" out of "+total+" correct!");
        incorrecttxt.setText("Incorrect responses so far: "+wrong);
        nextBtn = (Button) findViewById(R.id.nextbtn);
        if(total == numQ) {
            nextBtn.setText("Finish");
            lastQ = true;
        } else
            lastQ = false;
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity;
                if(!lastQ) {
                    nextActivity = new Intent(QuestionResults.this, QuestionPage.class);
                    nextActivity.putExtra("questionSet", topicID);
                    nextActivity.putExtra("correct", correct);
                    nextActivity.putExtra("wrong", wrong);
                    nextActivity.putExtra("qNum", qNum);
                    nextActivity.putExtra("numQ", numQ);
                    nextActivity.putExtra("mBundle", mBundle);

                } else
                    nextActivity = new Intent(QuestionResults.this, MainActivity.class);
                startActivity(nextActivity);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_results, menu);
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
