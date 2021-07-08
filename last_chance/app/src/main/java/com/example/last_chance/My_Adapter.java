package com.example.last_chance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class My_Adapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<My_Objects> al;
    LayoutInflater inf;

    public My_Adapter(Context context, int layout, ArrayList<My_Objects> al) {
        this.context = context;
        this.layout = layout;
        this.al = al;
        this.inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { // 총 데이터의 개수
        return al.size();
    }

    @Override
    public Object getItem(int position) { // 해당 행의 데이터
        return al.get(position);
    }

    @Override
    public long getItemId(int position) { // 해당 행의 유니크한 id
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inf.inflate(layout, null);

        TextView tv1 = convertView.findViewById(R.id.txt1);
        TextView tv2 = convertView.findViewById(R.id.txt2);
        //TextView tv3 = convertView.findViewById(R.id.txt3);

        My_Objects mv = al.get(position);
        tv1.setText(mv.class_id);
        tv2.setText(mv.truncated.toString());
        //tv3.setText(mv.truncated.toString());

        return convertView;
    }


}
