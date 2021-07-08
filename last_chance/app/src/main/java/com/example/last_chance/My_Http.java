package com.example.last_chance;

import android.os.AsyncTask;

public class My_Http extends AsyncTask<Void, Void, String> {

    String url;
    String[] file_list;

    public My_Http(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        My_Server2 request = new My_Server2();

        return request.request(url);
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);

        String[] temp = str.split("</a></li>");
        String[] temp2;

        file_list = new String[temp.length - 2];

        for (int j = 1; j < temp.length - 1; j++) {
            temp2 = temp[j].split("> ");
            file_list[j - 1] = temp2[1];
            //System.out.println(file_list[j - 1]);
        }
    }

    public String[] get_list() {
        return file_list;
    }
}
