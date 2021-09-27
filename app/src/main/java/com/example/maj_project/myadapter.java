package com.example.maj_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.Settings.System.getString;

public class myadapter extends FirebaseRecyclerAdapter<events,myadapter.myviewholder>
{
    String temp="", temp1="";
    public myadapter(@NonNull FirebaseRecyclerOptions<events> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull events events)
    {
        holder.eid.setText(events.getKey());
        holder.ename.setText(events.getName());
        holder.orgname.setText(events.getOrg());
        holder.datetext.setText(events.getDate());
        Glide.with(holder.img.getContext()).load(events.getImgurl()).into(holder.img);
        holder.oid.setText(events.getEid().toString().trim());
        temp = events.getEid().toString().trim();
        temp1 = holder.eid.getText().toString().trim();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView eid, ename, orgname, datetext, oid, textView;
        Button reg, schedule;
        DatabaseReference ref, ref1;
        registered_participants rp;
        String eid1, pid, token;
        String key;
        events e1;
        ImageButton notification;

        public myviewholder(@NonNull View itemView)
        {

            super(itemView);

            Intent intent = ((Activity)itemView.getContext()).getIntent();
            key = intent.getStringExtra("key");
            token = intent.getStringExtra("token");


            img=(CircleImageView)itemView.findViewById(R.id.img1);
            eid = (TextView) itemView.findViewById(R.id.eid);
            ename = (TextView) itemView.findViewById(R.id.ename);
            orgname = (TextView) itemView.findViewById(R.id.orgname);
            datetext = (TextView) itemView.findViewById(R.id.datetext);
            oid = (TextView) itemView.findViewById(R.id.oid);
            reg = (Button)itemView.findViewById(R.id.register);
            schedule = (Button) itemView.findViewById(R.id.schedule);
            textView = (TextView) itemView.findViewById(R.id.textView);
            notification = (ImageButton) itemView.findViewById(R.id.notification);

            //if(FirebaseDatabase.getInstance().getReference().child("events").child(eid.getText().toString()).getChildrenCount()>9)


            notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder((Activity) itemView.getContext());

                    ref1 = FirebaseDatabase.getInstance().getReference().child("events");

                    ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.child(eid.getText().toString()).hasChild("updates")) {
                                notification.setImageResource(R.drawable.bell1);
                                textView.setText(snapshot.child(eid.getText().toString()).child("updates").child("msg").child("message").getValue().toString());
                                dialog.setMessage(snapshot.child(eid.getText().toString()).child("updates").child("msg").child("message").getValue().toString());
                                dialog.setTitle("Updates about the event");
                            }
                            else {
                                dialog.setMessage("No Updates Available");
                            }
                                dialog.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                //Toast.makeText(,"Yes is clicked",Toast.LENGTH_LONG).show();
                                            }
                                        });

                                AlertDialog alertDialog = dialog.create();
                                alertDialog.show();

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            });


            //e1= new events();


            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    rp = new registered_participants();
                    ref = FirebaseDatabase.getInstance().getReference().child("users_org").child(oid.getText().toString().trim()).child("events").child(eid.getText().toString().trim()).child("registered_participants");

                    //temp = e1.getEid().toString().trim();

                    Toast.makeText(itemView.getContext(), "Org id is"+temp, Toast.LENGTH_LONG).show();

                    //rp.setEid(eid.getText().toString().trim());
                    rp.setPid(key.trim());
                    System.out.println(token);
                    rp.setToken(token.toString().trim());

                    ref.child(key.toString()).setValue(rp);
                    //Toast.makeText(itemView.getContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                }
            });

            schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // Intent intent = new Intent(((Activity)itemView.getContext()), viewschedule.class);

                    Intent intent = new Intent((Activity)itemView.getContext(),viewschedule.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("eid",eid.getText().toString().trim());
                    bundle.putString("oid", oid.getText().toString().trim());
                    intent.putExtras(bundle);
                    ((Activity)itemView.getContext()).startActivity(intent);
                    //sam1(itemView);
                }
            });



        }
    }

    public void sam(View itemView)
    {
        String activity;
        //Intent intent = new Intent(activity, viewschedule.class);
        //intent.putExtra( "oid", "sdfg");
        //intent.putExtra("eid", "cvbn");
        //System.out.println(oid.getText().toString()+"@@@@@@@@@@@@@@@@@@@@@@"+eid.getText().toString());
        //startActivity(intent);

    }

    public  void sam1(View itemView)
    {

    }
}
