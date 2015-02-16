package zcamara.washington.edu.quizdroid;

import android.app.FragmentManager;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;


public class fragmentActivity extends ActionBarActivity {

    private static int qNum,correct,wrong;
    private static String uAnswer;
    private static String answer;
    private static Topic topicObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            Intent launchedMe = getIntent();
            qNum = launchedMe.getIntExtra("qNum", 0);
            correct = launchedMe.getIntExtra("correct", 0);
            wrong = launchedMe.getIntExtra("wrong", 0);
            topicObj = QuizApp.getInstance().getRepo().getTopic(launchedMe.getStringExtra("topic"));
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new topicOverviewFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class topicOverviewFragment extends Fragment {

        public topicOverviewFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.topic_overview_fragment, container, false);

            TextView topic = (TextView) rootView.findViewById(R.id.topic);
            TextView description = (TextView) rootView.findViewById(R.id.description);
            Button startBtn = (Button) rootView.findViewById(R.id.beginbtn);
            TextView numQ = (TextView) rootView.findViewById(R.id.numQ);

            topic.setText(topicObj.getTitle());
            description.setText(topicObj.getLongDesc());
            numQ.setText(topicObj.getQuestions().size()+" Questions");

            startBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new questionFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                            .replace(R.id.container, fragment)
                            .commit();
                }
            });


            return rootView;
        }
    }
    public static class questionFragment extends Fragment {

        public questionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.question_fragment, container, false);

            TextView question = (TextView) rootView.findViewById(R.id.question);
            final Button nextBtn = (Button) rootView.findViewById(R.id.nextbtn);
            final RadioGroup qGroup = (RadioGroup) rootView.findViewById(R.id.options);
            RadioButton op1 = (RadioButton) rootView.findViewById(R.id.op1);
            RadioButton op2 = (RadioButton) rootView.findViewById(R.id.op2);
            RadioButton op3 = (RadioButton) rootView.findViewById(R.id.op3);
            RadioButton op4 = (RadioButton) rootView.findViewById(R.id.op4);
            TextView qCounter = (TextView) rootView.findViewById(R.id.questionNum);

            ArrayList<Quiz> questions = (ArrayList)topicObj.getQuestions();
            final Quiz currentQuestion = questions.get(qNum);
            String[] options = currentQuestion.getAnswers();

            qCounter.setText("Question: "+ (qNum + 1));
            question.setText(currentQuestion.getQuestion());
            op1.setText(options[0]);
            op2.setText(options[1]);
            op3.setText(options[2]);
            op4.setText(options[3]);

            qGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    nextBtn.setVisibility(View.VISIBLE);
                }
            });

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton userAns = (RadioButton) rootView.findViewById(qGroup.getCheckedRadioButtonId());
                    uAnswer = userAns.getText().toString();
                    answer = currentQuestion.getAnswer();
                    if(uAnswer.equals(answer))
                        correct++;
                    else
                        wrong++;
                    Fragment fragment = new questionResultsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                            .replace(R.id.container, fragment)
                            .commit();
                }
            });

            return rootView;
        }
    }
    public static class questionResultsFragment extends Fragment {

        public questionResultsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.question_results_fragment, container, false);

            TextView correcttxt = (TextView) rootView.findViewById(R.id.correct);
            TextView incorrecttxt = (TextView) rootView.findViewById(R.id.incorrect);
            TextView answerSummary = (TextView) rootView.findViewById(R.id.answersummary);

            if(uAnswer.equals(answer)) {
                answerSummary.setText("Congratulations! " + answer + " was the correct choice!");
            } else {
                answerSummary.setText("Sorry! You chose: " + uAnswer + " and the correct response was: " + answer);
            }
            int total = correct + wrong;
            correcttxt.setText("You have "+correct+" out of "+total+" correct!");
            incorrecttxt.setText("Incorrect responses so far: "+wrong);
            Button nextBtn = (Button) rootView.findViewById(R.id.nextbtn);
            if(total == topicObj.getQuestions().size()) {
                nextBtn.setText("Finish");
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            } else {
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qNum++;
                        Fragment fragment = new questionFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                                .replace(R.id.container, fragment)
                                .commit();
                    }
                });
            }
            return rootView;
        }
    }
}
