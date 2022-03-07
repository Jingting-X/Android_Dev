package edu.neu.numad22sp_jingtingxing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class WebServiceActivity extends AppCompatActivity {
    private String UrlString = "https://corona.lmao.ninja/v2/states/California?yesterday=true";
    private ProgressBar progressBar;
    private TextView confirmedCases;
    private TextView deathCases;
    private TextView casesMillion;
    private TextView deathsMillion;
    private TextView updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        progressBar = findViewById(R.id.simpleProgressBar);
        confirmedCases = findViewById(R.id.textView_confirmedCases);
        deathCases = findViewById(R.id.textView_deathCases);
        casesMillion = findViewById(R.id.textView_casesOneMillion);
        deathsMillion = findViewById(R.id.textView_deathsOneMillion);
        updateDate = findViewById(R.id.textView_date);
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
            casesMillion.setText("");
            deathsMillion.setText("");
            updateDate.setText("");
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            String[] results = new String[5];
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
                results[2] = responseJSON.getString("casesPerOneMillion");
                results[3] = responseJSON.getString("deathsPerOneMillion");
                results[4] = getDate(responseJSON.getLong("updated"));

                System.out.println(results[4]);
                return results;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            results[0] = "Something went wrong.";
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
            casesMillion.setText(results[2]);
            deathsMillion.setText(results[3]);
            updateDate.setText(results[4]);
        }

    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        //cal.setTimeInMillis(time * 1000L);
        String date = DateFormat.format("MM-dd-yyyy", cal).toString();
        return date;
    }
}