package com.example.maj_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class display_qr extends AppCompatActivity {
    ImageView qrCodeIV;
    FileInputStream fileInputStream;
    String temp = "", key="";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_qr);

        qrCodeIV = findViewById(R.id.idIVQrcode);
        key=getKey();
       WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(key, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
        }










    }

    public String getKey() {
        String k="";
        try {


            fileInputStream = openFileInput("userinfo");
            int c;

            while ((c = fileInputStream.read()) != -1) {
                temp = temp + Character.toString((char) c);

            }
            String[] words = temp.split(",");
            k = words[0].toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(display_qr.this, "Id fetched is "+k, Toast.LENGTH_LONG).show();
        return k;
    }

}