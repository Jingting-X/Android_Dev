 package edu.neu.numad22sp_jingtingxing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* A1 About Me */
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                A1 Toast
//                Toast.makeText(MainActivity.this, "Jingting Xing xing.jin@northeastern.edu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
            }
        });

        /* A3 Clicky */
        Button btnClicky = (Button) findViewById(R.id.buttonClicky);
        btnClicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClickyActivity.class);
                startActivity(intent);
            }
        });

        /* A4 Link Collector */
        Button btnLinkCollector = (Button) findViewById(R.id.buttonLinkCollector);
        btnLinkCollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        LinkCollectorActivity.class);
                startActivity(intent);
            }
        });

        /* A5 Locator */
        Button btnLocator = findViewById(R.id.btnLocator);
        btnLocator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocatorActivity.class);
                startActivity(intent);
            }
        });

        /* A6 Web Service */
        Button btnService = findViewById(R.id.btnService);
        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebServiceActivity.class);
                startActivity(intent);
            }
        });

    }
}