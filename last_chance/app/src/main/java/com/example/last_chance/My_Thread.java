package com.example.last_chance;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

class My_Thread extends Thread{

    LinearLayout ll;
    Drawable ob;

    String file_name;


    public My_Thread(LinearLayout ll, String file_name) {
        this.ll = ll;
        this.file_name = file_name;
    }

    public Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what == 1)
                ll.setBackground(ob);
        }
    };

    public void run() {
        try {
            URL url = new URL(file_name); //HTTP
            URLConnection conn = url.openConnection();
            conn.connect();

            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), conn.getContentLength());
            Bitmap imgBitmap = BitmapFactory.decodeStream(bis);

            ob = new BitmapDrawable(imgBitmap);

            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(1);
        handler.sendMessage(msg);
    }
}

