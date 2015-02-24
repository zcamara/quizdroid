package zcamara.washington.edu.quizdroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String prefURL = sp.getString("prefURL","http://tednewardsandbox.site44.com/questions.json");
        Toast.makeText(context, prefURL, Toast.LENGTH_SHORT).show();
    }
}
