package com.example.last_chance;

import java.io.Serializable;
import java.util.ArrayList;

public class My_Objects implements Serializable {

    String class_id;
    ArrayList<int[][]> data;
    ArrayList<Boolean> truncated;

    public My_Objects(String class_id, int[][] data_temp, Boolean truncated_temp) {
        this.class_id = class_id;

        data = new ArrayList<>();
        truncated = new ArrayList<>();

        data.add(data_temp);
        truncated.add(truncated_temp);
    }
}
