package zcamara.washington.edu.quizdroid;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zachcamara on 2/15/15.
 */
public class QuizApp extends Application {
    private static QuizApp sInstance;
    private static final String TAG = QuizApp.class.getName();

    private TopicRepository topics;
    private PendingIntent pendingIntent;
    private Intent alarmIntent;
    private AlarmManager alarmManager;

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
        Log.i(TAG, "QuizApp loaded successfully! Starting alarm service...");
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        startAlarm();
        sInstance.initializeInstance();
    }

    public void startAlarm() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String prefFreq = sp.getString("prefFreq","1");
        int frequency = Integer.parseInt(prefFreq);
        alarmIntent = new Intent(QuizApp.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(QuizApp.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (frequency * 1000 * 60), pendingIntent);
        Toast.makeText(getInstance(), "Will attempt to download new questions every: " + frequency + " minute(s)", Toast.LENGTH_SHORT).show();
    }

    public void killAlarm() {
        if(pendingIntent == null) {
            alarmIntent = new Intent(QuizApp.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(QuizApp.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        alarmIntent = new Intent(QuizApp.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(QuizApp.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    protected void initializeInstance() {
        // do all your initialization here
        topics = new myRepo();
        topics.buildRepo();
    }
}
