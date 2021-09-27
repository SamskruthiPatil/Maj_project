package com.example.maj_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class viewschedule extends AppCompatActivity {

    String eid, oid;
    DatabaseReference ref;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewschedule);

        Intent intent = getIntent();
        eid = intent.getStringExtra("eid");
        oid = intent.getStringExtra("oid");


        img = (ImageView) findViewById(R.id.img);

        System.out.println(eid+"^^^^^^^^^^^^^^^^^^^^^^^^^^"+oid);
        //System.out.println(oid);
        ref = FirebaseDatabase.getInstance().getReference().child("users_org").child(oid).child("events").child(eid);
        DatabaseReference a = ref.child("schurl");

        a.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);

                // loading that data into rImage
                // variable which is ImageView
                Picasso.get().load(link).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(viewschedule.this, "Error Loading Image", Toast.LENGTH_SHORT).show();

            }
        });
    }
}