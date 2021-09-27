package com.example.maj_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class par_home extends AppCompatActivity {

    String key, name;
    TextView w;
    Button viewcode, viewevent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_par_home);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        name = intent.getStringExtra("name");
        w=(TextView) findViewById(R.id.wel);
        getSupportActionBar().setTitle(name);
        w.setText("Welcome "+name+"\n"+"Id "+key);

        viewcode= (Button) findViewById(R.id.viewevent);
        viewevent = (Button) findViewById(R.id.viewevent);


        viewevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), view_event.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });

    }

    public void qr_code(View view)
    {
        Intent intent = new Intent(par_home.this, display_qr.class);
        startActivity(intent);
    }
}