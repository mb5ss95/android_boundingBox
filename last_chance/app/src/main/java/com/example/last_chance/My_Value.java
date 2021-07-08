package com.example.last_chance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class My_Value {

    String file_name;
    String date;
    int num_of_objects;
    ArrayList<My_Objects> objects;

    public My_Value(String file_name) {
        this.file_name = file_name;
        this.date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        objects = new ArrayList<>();
    }

    public void set_Objects(String class_id, Boolean truncated, int[][] data) {
        objects.add(new My_Objects(class_id, data, truncated));
        num_of_objects = objects.size();
    }

    public void clear_object() {
        objects.clear();
        num_of_objects = objects.size();
    }

    public void remove_object(int last) {
        objects.remove(last);
        num_of_objects = objects.size();
    }

    public ArrayList<My_Objects> get_object() {
        return objects;
    }

    public int get_object_size(){
        return objects.size();
    }
}
