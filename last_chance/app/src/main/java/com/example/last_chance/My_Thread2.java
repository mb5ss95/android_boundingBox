package com.example.last_chance;

import java.io.ByteArrayInputStream;

public class My_Thread2 extends Thread implements init_data{

    My_Server connectFTP;

    String json;
    String file_name;


    public My_Thread2(String json, String file_name){
        this.json = json;
        this.file_name = file_name + ".txt";
        connectFTP = new My_Server();
    }

    public void run() {
        if (connectFTP.ftp_Connect(host, user, pass, port)) {
            if(connectFTP.ftp_Upload_File(new ByteArrayInputStream(json.getBytes()), file_name, "/json")){
                connectFTP.ftp_Disconnect();
            }
        }
    }
}
