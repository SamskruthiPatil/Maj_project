package com.example.maj_project;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<events,myadapter.myviewholder>
{
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
        TextView eid, ename, orgname, datetext;
        Button reg;
        DatabaseReference ref;
        registered_participants rp;
        String eid1, pid;
        String key;

        public myviewholder(@NonNull View itemView)
        {

            super(itemView);

            Intent intent = ((Activity)itemView.getContext()).getIntent();
            key = intent.getStringExtra("key");


            img=(CircleImageView)itemView.findViewById(R.id.img1);
            eid = (TextView) itemView.findViewById(R.id.eid);
            ename = (TextView) itemView.findViewById(R.id.ename);
            orgname = (TextView) itemView.findViewById(R.id.orgname);
            datetext = (TextView) itemView.findViewById(R.id.datetext);
            reg = (Button)itemView.findViewById(R.id.register);


            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    rp = new registered_participants();
                    ref = FirebaseDatabase.getInstance().getReference().child("registered_participants");

                    rp.setEid(eid.getText().toString().trim());
                    rp.setPid(key.trim());

                    ref.child(key.toString()).setValue(rp);



                    Toast.makeText(itemView.getContext(), "Data Inserted", Toast.LENGTH_LONG).show();




                }
            });


        }
    }
}
