package zcamara.washington.edu.quizdroid;

import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;

/**
 * Created by zachcamara on 2/22/15.
 */
public class preferenceActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // add the xml resource
        addPreferencesFromResource(R.xml.user_settings);
        findPreference("prefURL").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                QuizApp.getInstance().killAlarm();
                QuizApp.getInstance().startAlarm();
                return true;
            }
        });
        findPreference("prefFreq").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                QuizApp.getInstance().killAlarm();
                QuizApp.getInstance().startAlarm();
                return true;
            }
        });
    }







}
