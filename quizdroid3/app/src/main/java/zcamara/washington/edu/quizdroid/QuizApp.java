package zcamara.washington.edu.quizdroid;

import android.app.Application;

/**
 * Created by zachcamara on 2/15/15.
 */
public class QuizApp extends Application {
    private static QuizApp sInstance;

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
        //LOG MESSAGE HERE FOR POINTS!!!!!!!!!!!!!!!!
        //NEED TO ADDD
        //DON't FORGET
        sInstance.initializeInstance();
    }

    protected void initializeInstance() {
        // do all your initialization here
        topics = new myRepo();
        topics.buildRepo();
    }
}
