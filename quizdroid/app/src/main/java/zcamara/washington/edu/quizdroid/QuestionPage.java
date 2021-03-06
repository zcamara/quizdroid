package zcamara.washington.edu.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;


public class QuestionPage extends ActionBarActivity {

    private TextView question;
    private TextView qCounter;
    private Button nextBtn;
    private RadioGroup qGroup;
    private RadioButton op1,op2,op3,op4;
    private String questions[];
    private String answers[];
    private String opt[];
    private Bundle mBundle;


    private int qNum,correct,wrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        question = (TextView) findViewById(R.id.question);
        nextBtn = (Button) findViewById(R.id.nextbtn);
        qGroup = (RadioGroup) findViewById(R.id.options);
        op1 = (RadioButton) findViewById(R.id.op1);
        op2 = (RadioButton) findViewById(R.id.op2);
        op3 = (RadioButton) findViewById(R.id.op3);
        op4 = (RadioButton) findViewById(R.id.op4);
        qCounter = (TextView) findViewById(R.id.questionNum);

        Intent launchedMe = getIntent();
        qNum = launchedMe.getIntExtra("qNum", 0);
        correct = launchedMe.getIntExtra("correct", 0);
        wrong = launchedMe.getIntExtra("wrong", 0);
        mBundle = launchedMe.getBundleExtra("mBundle");
        questions = mBundle.getStringArray("questions");
        answers = mBundle.getStringArray("answers");
        opt = mBundle.getStringArray("opt");

        qCounter.setText("Question: "+ (qNum + 1));
        question.setText(questions[qNum]);
        op1.setText(opt[0 + (qNum * 4)]);
        op2.setText(opt[1 + (qNum * 4)]);
        op3.setText(opt[2 + (qNum * 4)]);
        op4.setText(opt[3 + (qNum * 4)]);

        qGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                nextBtn.setVisibility(View.VISIBLE);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton userAns = (RadioButton) findViewById(qGroup.getCheckedRadioButtonId());
                String uAns = userAns.getText().toString();
                Intent nextActivity = new Intent(QuestionPage.this, QuestionResults.class);
                nextActivity.putExtra("correct", correct);
                nextActivity.putExtra("wrong", wrong);
                nextActivity.putExtra("numQ", questions.length);
                nextActivity.putExtra("mBundle", mBundle);
                nextActivity.putExtra("userAnswer", uAns);
                nextActivity.putExtra("answer", answers[qNum]);
                startActivity(nextActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_page, menu);
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
