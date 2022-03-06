package edu.neu.numad22sp_jingtingxing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebServiceActivity extends AppCompatActivity {
    private String UrlString = "https://corona.lmao.ninja/v2/states/California?yesterday=true";
    private TextView confirmedCases;
    private TextView deathCases;
    private ProgressBar progressBar;
    private TextView connectionProgressOrCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        confirmedCases = findViewById(R.id.textView_confirmedCases);
        deathCases = findViewById(R.id.textView_deathCases);
    }

    public void getNewCasesCallback(View view) {
        GetInfoTask task = new GetInfoTask();
        task.execute();
    }



    private class GetInfoTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            confirmedCases.setText("");
            deathCases.setText("");
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            String[] results = new String[2];
            URL url;

            try {
                url = new URL(UrlString);

                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setRequestMethod("GET");
                httpConn.setDoInput(true);
                httpConn.setConnectTimeout(5000);

                httpConn.connect();

                InputStream inputStream = httpConn.getInputStream();
                String response = convertStreamToString(inputStream);
                inputStream.close();
                httpConn.disconnect();

                JSONObject responseJSON = new JSONObject(response);

                results[0] = responseJSON.getString("todayCases");
                results[1] = responseJSON.getString("todayDeaths");

                System.out.println(results[0]);
                return results;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            results[0] = "Something went wrong. Please try again later. ";
            return results;
        }

        @Override
        protected void onPostExecute(String[] results) {
            super.onPostExecute(results);
            progressBar.setVisibility(View.INVISIBLE);
            if (results.length == 1) {
                return;
            }

            confirmedCases.setText(results[0]);
            deathCases.setText(results[1]);
        }

    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}