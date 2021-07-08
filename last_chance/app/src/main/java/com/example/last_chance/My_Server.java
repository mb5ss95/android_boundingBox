package com.example.last_chance;

import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.InputStream;

public class My_Server {

    FTPClient FC;

    String TAG = "Connect Server";

    public My_Server(){
        FC = new FTPClient();
    }


    public boolean ftp_Connect(String host, String user, String pass, int port) {
        boolean result = false;
        try {
            FC.connect(host, port);

            if (FTPReply.isPositiveCompletion(FC.getReplyCode())) {
                result = FC.login(user, pass);
                FC.enterLocalPassiveMode();
            }
        } catch (Exception e) {
            Log.d(TAG, "Couldn't connect to host");
        }
        return result;
    }

    public boolean ftp_Disconnect() {
        boolean result = false;
        try {
            FC.logout();
            FC.disconnect();
            result = true;
        } catch (Exception e) {
            Log.d(TAG, "Failed to disconnect with server");
        }
        return result;
    }

    public boolean ftp_Change_Dir(String dir) {
        try{
            FC.changeWorkingDirectory(dir);
            return true;
        }catch (Exception e){
            Log.d(TAG, "Couldn't change the directory");
        }
        return false;
    }

    public boolean ftp_Upload_File(InputStream ips, String desFileName, String desDirectory) {
        boolean result = false;
        try {
            if(ftp_Change_Dir(desDirectory)) {
                result = FC.storeFile(desFileName, ips);
            }
        } catch(Exception e){
            Log.d(TAG, "Couldn't upload the file");
        }
        return result;
    }

}
