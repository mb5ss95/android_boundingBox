package com.example.http_ftp;


import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

class th extends Thread {


    String file_path;


    public th(String file_path) {
        this.file_path = file_path;
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

            }
        }
    };
    //https://c10106.tistory.com/4968

    public void run() {

        try {
            //URL url = new URL(host_path + file_name); //FTP
            URL url = new URL(file_path); //HTTP
            URLConnection conn = url.openConnection();
            conn.connect();

            System.out.println("22222222222222222222222222222222"+conn.getContent());
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), conn.getContentLength());
            System.out.println("3333333333333333333333333333333"+bis);
            System.out.println("44444444444444444444444444"+bis.read());
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(1);
        handler.sendMessage(msg);

    }
}
