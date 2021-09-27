package com.example.maj_org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class reg_event extends AppCompatActivity {
    String key, name;
    TextView w;
    Button addevent, viewwvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_event);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        name = intent.getStringExtra("name");
        w=(TextView) findViewById(R.id.wel);
        getSupportActionBar().setTitle(name);
        w.setText("Welcome "+name+"\n"+"Id "+key);

        addevent= (Button) findViewById(R.id.addevent);
        viewwvent = (Button) findViewById(R.id.viewevent);

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), org_home.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });
        viewwvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), view_myevents.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });
    }
}