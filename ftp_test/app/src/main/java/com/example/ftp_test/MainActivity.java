package com.example.ftp_test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {

    Button btn1, btn2;
    ImageView img;

    private ConnectFTP connectFTP;
    Bitmap imgBitmap;

    String TAG = "Connect FTP";
    String host = "192.168.0.13";
    String user = "user02";
    String pass = "test";
    String file_list[];
    String dir_path = "/image";

    int port = 21;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy() 호출됨", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_connect();
        init_wiget();

    }

    private void init_connect() {

        connectFTP = new ConnectFTP();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean status = connectFTP.ftpConnect(host, user, pass, port);
                if (status == true) {
                    Log.d(TAG, "Connection Success");
                    String temp = connectFTP.ftpGetDirectory();
                    System.out.println("생성자 호출 완료" + temp);
                    String[] temp2 = connectFTP.ftpGetFileList(temp + "/image");
                    System.out.println("생성자 호출 완료22222" + temp2[0]);
                } else {
                    Log.d(TAG, "Connection failed");
                }
            }
        }).start();
    }
    private void init_wiget() {

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        img = findViewById(R.id.imageView);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        if(msg.what == 1)
                            System.out.println("handle start!!!!!!!!!!!!!!!!!!!!!!!!11");
                        img.setImageBitmap(imgBitmap);
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        imgBitmap = null;

                        try {

                            URL url = new URL("ftp://user02:test@192.168.0.13:21/image/nako009.jpg"+";type=bin");
                            URLConnection conn = url.openConnection();
                            conn.connect();

                            //int nSize = conn.getContentLength();
                            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                            //BufferedInputStream bis = new BufferedInputStream(url.openStream(), nSize);
                            imgBitmap = BitmapFactory.decodeStream(bis);

                            bis.close();
                            Message msg = handler.obtainMessage(1);
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();



            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //init_connect();
            }
        });
    }
}