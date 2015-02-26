package zcamara.washington.edu.quizdroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by zachcamara on 2/15/15.
 */
public class QuizApp extends Application {
    private static QuizApp sInstance;
    private static final String TAG = QuizApp.class.getName();
    private Activity context;
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
        Log.i(TAG, "QuizApp loaded successfully! Checking for downloaded topics...");
        if(!topicsCached()) {
            forceDownload();
        }
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        startAlarm();
        sInstance.initializeInstance();
    }

    public void shareContext(Activity context) {
        this.context = context;
    }

    public void downloadFailed() {
        //MAKE DIALOG BOX "DOWNLOAD FAILED. Retry or Quit?
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle("Download failed!");
        // set dialog message
        alertDialogBuilder
                .setMessage("Retry download?")
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        QuizApp.getInstance().forceDownload();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        QuizApp.getInstance().exit();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void forceDownload() {
        Toast.makeText(getApplicationContext(), "Attempting to download topics. Please wait...", Toast.LENGTH_LONG).show();
        getJSON task = new getJSON(getApplicationContext());
        String[] params = new String[2];
        params[0] = "http://tednewardsandbox.site44.com/questions.json";
        task.execute(params);
    }

    public boolean topicsCached(){
        File file = getBaseContext().getFileStreamPath("quizdata.json");
        return file.exists();
    }

    public void restart() {
        killAlarm();
        Intent mStartActivity = new Intent(getApplicationContext(), MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 200, mPendingIntent);
        System.exit(0);
    }

    public void exit() {
        killAlarm();
        context.finish();
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
    }

    public void startAlarm() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String prefFreq = sp.getString("prefFreq","5"); //default to 5 minutes
        if(!prefFreq.equals("0")) {
            int frequency = Integer.parseInt(prefFreq);
            alarmIntent = new Intent(QuizApp.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(QuizApp.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (frequency * 1000 * 60), pendingIntent);
            Toast.makeText(getInstance(), "Will attempt to download new questions every: " + frequency + " minute(s)", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getInstance(), "Please enter a valid number that is greater than 0", Toast.LENGTH_SHORT).show();
        }
    }

    public void startAlarm(String freq) {
        if(!freq.equals("0")) {
            int frequency = Integer.parseInt(freq);
            alarmIntent = new Intent(QuizApp.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(QuizApp.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (frequency * 1000 * 60), pendingIntent); //SET BACK TO 1000 * 60 for 1 min intervals
            Toast.makeText(getInstance(), "Will attempt to download new questions every: " + frequency + " minute(s)", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getInstance(), "Please enter a valid number that is greater than 0", Toast.LENGTH_SHORT).show();
        }
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
        topics = new myRepo(getApplicationContext());
        topics.buildRepo();
    }
}
