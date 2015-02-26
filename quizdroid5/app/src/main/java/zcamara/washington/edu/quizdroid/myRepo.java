package zcamara.washington.edu.quizdroid;

/**
 * Created by zachcamara on 2/15/15.
 */
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class myRepo implements TopicRepository {

    private ArrayList<Topic> repo;
    private Context context;

    public myRepo(Context context) {
        this.context = context;
    }

    private int[] topicIcons = {
            R.drawable.science,
            R.drawable.marvel,
            R.drawable.math
    };

    public void buildRepo() {
        //Create Topic and Quiz objects
        String json = loadJSONFromAsset();
        repo = new ArrayList<Topic>();
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);

                Log.d("TEST", jsonArray.toString());

                for(int i = 0; i < jsonArray.length(); i++) { //each topic
                    Topic newTopic = new Topic();
                    JSONObject topic = jsonArray.getJSONObject(i);
                    String title = topic.getString("title");
                    String desc = topic.getString("desc");
                    newTopic.setTitle(title);
                    newTopic.setShortDesc(desc);
                    newTopic.setLongDesc(desc);
                    newTopic.setIcon(topicIcons[i]);
                    JSONArray questions = topic.getJSONArray("questions");
                    for(int questionID = 0; questionID < questions.length(); questionID++){
                        JSONObject question = questions.getJSONObject(questionID);
                        Quiz newQuestion = new Quiz();
                        String questionTxt = question.getString("text");
                        int answer = question.getInt("answer");
                        JSONArray answers = question.getJSONArray("answers");
                        String[] answerOpt = new String[4];
                        for(int ans = 0; ans < answers.length(); ans++) {
                            answerOpt[ans] = answers.getString(ans);
                        }
                        newQuestion.setQuestion(questionTxt);
                        newQuestion.setAnswers(answerOpt);
                        newQuestion.setCorrectAns(answer - 1);
                        newTopic.addQuestion(newQuestion);
                    }
                    repo.add(newTopic);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String loadJSONFromAsset() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new
                    File(context.getFilesDir()+File.separator+"quizdata.json")));
            String read;
            StringBuilder builder = new StringBuilder("");

            while((read = bufferedReader.readLine()) != null){
                builder.append(read);
            }
            bufferedReader.close();
            return builder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String[] getTopics(){
        String[] topics = new String[repo.size()];
        for (int i = 0; i < repo.size(); i++) {
            topics[i] = repo.get(i).getTitle();
        }
        return topics;
    }

    public Collection<Topic> getRepo() {
        return repo;
    }

    public Topic getTopic(String topic) {
        List<String> topicList = Arrays.asList(getTopics());
        int index = topicList.indexOf(topic);
        return repo.get(index);
    }

}
