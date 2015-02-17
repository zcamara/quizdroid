package zcamara.washington.edu.quizdroid;

import android.app.Application;
import android.util.Log;

/**
 * Created by zachcamara on 2/15/15.
 */
public class QuizApp extends Application {
    private static QuizApp sInstance;
    private static final String TAG = QuizApp.class.getName();

    private TopicRepository topics;

    public static QuizApp getInstance() {
        return sInstance;
    }

    public TopicRepository getRepo() {
        return topics;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Log.i(TAG, "QuizApp loaded successfully!");
        sInstance.initializeInstance();
    }

    protected void initializeInstance() {
        // do all your initialization here
        topics = new myRepo();
        topics.buildRepo();
    }
}
