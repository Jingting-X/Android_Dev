package edu.neu.numad22sp_jingtingxing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);

        TextView textContent = (TextView) findViewById(R.id.textView1);
        textContent.setText("Pressed: -");

        final Button btnA = (Button)findViewById(R.id.buttonA);
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textContent.setText("Pressed: A");
            }
        });

        final Button btnB = (Button)findViewById(R.id.buttonB);
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textContent.setText("Pressed: B");
            }
        });

        final Button btnC = (Button)findViewById(R.id.buttonC);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textContent.setText("Pressed: C");
            }
        });

        final Button btnD = (Button)findViewById(R.id.buttonD);
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textContent.setText("Pressed: D");
            }
        });

        final Button btnE = (Button)findViewById(R.id.buttonE);
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textContent.setText("Pressed: E");
            }
        });

        final Button btnF = (Button)findViewById(R.id.buttonF);
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textContent.setText("Pressed: F");
            }
        });

    }
}