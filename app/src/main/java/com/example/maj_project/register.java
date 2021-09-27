package com.example.maj_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;
import java.util.Random;

public class register extends AppCompatActivity {

    EditText name, email, mob, pass;
    Button signu;
    DatabaseReference ref;
    reg_p reg1;
    String myContents="", key="", token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.em);
        mob = (EditText) findViewById(R.id.mob);
        pass = (EditText) findViewById(R.id.pass);
        signu = (Button) findViewById(R.id.signu);



        reg1= new reg_p();
        ref = FirebaseDatabase.getInstance().getReference().child("reg_p");

        signu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph = mob.getText().toString().trim();
                int pas = Integer.parseInt(pass.getText().toString().trim());

                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";

                if(name.equals("") || name.equals(null)) {
                    name.setError("Username can't be empty");
                    return;

                } else if (email.equals("") || email.equals(null)||!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Please enter right email Address");
                    return;

                }
                else if (mob.equals("") || mob.equals(null)||mob.length()<10) {
                    mob.setError("Enter a right mobile number");


                }



                key=generateRandKey();
                reg1.setEmail(email.getText().toString().trim());
                reg1.setName(name.getText().toString().trim());
                reg1.setMob(ph);
                reg1.setPass(pas);
                reg1.setKey(key);
                ref.child(name.getText().toString().trim()).setValue(reg1);

                myContents = key.toString().trim()+","+email.getText().toString()+","+name.getText().toString()+
                        ","+ph+","+Integer.toString(pas);
                writeTxtFile(myContents);


                // ref.push().setValue(reg1);

                Toast.makeText(register.this, "Data Inserted", Toast.LENGTH_LONG).show();



                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }


        });






    }

    private String generateRandKey() {

        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        Toast.makeText(register.this, "Id generated is "+saltStr, Toast.LENGTH_LONG).show();
        return saltStr;
    }
    public void writeTxtFile(String contents) {
        // add-write text into file
        try {

            FileOutputStream fileout = openFileOutput("userinfo", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(contents);
            outputWriter.close();

            Toast.makeText(register.this,
                    "Text File Created.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(register.this,
                    "Error While Creating Text File", Toast.LENGTH_SHORT).show();
        }

    }

}