package zcamara.washington.edu.quizdroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import android.widget.Toast;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import android.content.Context;

/**
 * Created by zachcamara on 2/25/15.
 */
public class getJSON extends AsyncTask<String, Void, String>{
@Override
protected String doInBackground(String... urls) {
        try {
            URL u = new URL(urls[0]);
            url = urls[0];
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(1000);
            c.setReadTimeout(1000);
            c.connect();
            int status = c.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        c.disconnect();
                        return sb.toString();
                }
            c.disconnect();
            } catch (Exception ex) {
                Log.d(AlarmReceiver.class.getName(), ex.toString());
            }
            return null;
        }

    private Context context;
    private String url;

    public getJSON(Context ctx) {
        this.context = ctx;
    }

@Override
protected void onPostExecute(String result) {
    // result is what you got from your connection
    boolean restart = false;
    if (result != null && !result.isEmpty()) {
        try {
            if (!QuizApp.getInstance().topicsCached()) {
                restart = true;
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(context.getFilesDir() + File.separator + "quizdata.json"));
            bufferedWriter.write(result);
            bufferedWriter.close();
            if (restart)
                QuizApp.getInstance().restart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        Toast.makeText(context, "Error loading questions from: " + url, Toast.LENGTH_SHORT).show();
        QuizApp.getInstance().downloadFailed();
    }
}
}
