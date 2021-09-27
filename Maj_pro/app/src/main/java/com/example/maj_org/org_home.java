package com.example.maj_org;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class org_home extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    events event1;
    DatabaseReference ref;
    EditText name, date, org;
    Button host, upload;
    String domain, duration, key, imagurl, key1;
    ImageView img;
    Uri filepath;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_home);

        Intent intent = getIntent();
        key1 = intent.getStringExtra("key");

        event1 = new events();
        ref = FirebaseDatabase.getInstance().getReference().child("events");

        name = (EditText) findViewById(R.id.eventname);
        date = (EditText) findViewById(R.id.eventdate);
        org=(EditText) findViewById(R.id.org);
        host = (Button) findViewById(R.id.host);
        img = (ImageView) findViewById(R.id.bro_img);
        upload = (Button) findViewById(R.id.uploadimg);


        Spinner spinner = (Spinner) findViewById(R.id.domain_spinner);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.domain_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinner1 = (Spinner) findViewById(R.id.duration_spinner);
        spinner1.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.duration_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        domain = spinner.getSelectedItem().toString();
        duration=spinner1.getSelectedItem().toString();




        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(org_home.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent i = new Intent(Intent.ACTION_PICK);
                                i.setType("image/");
                                startActivityForResult(Intent.createChooser(i, "Please select Image"), 1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                token.continuePermissionRequest();

                            }
                        }).check();

            }
        });

        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key=generateRandKey();
                uploadfirebase();

            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        String selectedItemText = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(org_home.this, "Select is " +selectedItemText, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        Toast.makeText(org_home.this, "key generated is "+saltStr, Toast.LENGTH_LONG).show();
        return saltStr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==1 && resultCode==RESULT_OK){
            filepath=data.getData();
            try{
                InputStream is = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(is);
                img.setImageBitmap(bitmap);
            }
            catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadfirebase() {

        ProgressDialog d = new ProgressDialog(this);
        d.setTitle("Image Uploader");
        d.show();
        FirebaseStorage fs=FirebaseStorage.getInstance();
        StorageReference uploader =fs.getReference("Image1"+new Random().nextInt(50));

        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        d.dismiss();
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imagurl=uri.toString();
                                Toast.makeText(org_home.this, "Url received ", Toast.LENGTH_LONG).show();
                                event1.setName(name.getText().toString().trim());
                                event1.setOrg(org.getText().toString().trim());
                                event1.setDate(date.getText().toString().trim());
                                event1.setDomain(domain);
                                event1.setDuration(duration);
                                event1.setKey(key.toString().trim());
                                event1.setEid(key1.toString().trim());
                                event1.setImgurl(imagurl);
                                ref.child(key.toString().trim()).setValue(event1);
                                //ref.child(name.getText().toString().trim()).setValue(reg1);
                                Toast.makeText(org_home.this, "Data Inserted ", Toast.LENGTH_LONG).show();

                            }
                        });
                        Toast.makeText(org_home.this, "File uploaded ", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        d.setMessage("Uploaded : "+(int)percent +"%");
                    }
                });

    }
}
