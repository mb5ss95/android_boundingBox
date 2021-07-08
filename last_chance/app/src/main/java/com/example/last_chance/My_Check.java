package com.example.last_chance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class My_Check extends Activity {

    ListView lv;
    ImageButton btn1, btn2;

    ArrayList<My_Objects> mo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fix);

        Intent intent = getIntent();
        mo = (ArrayList<My_Objects>) intent.getSerializableExtra("test");

        init_wiget();
        init_listView();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private void init_listView() {
        lv = findViewById(R.id.listView1);
        lv.setAdapter(new My_Adapter(
                getApplicationContext(),
                R.layout.sample,
                mo));
    }

    public void init_wiget(){
        btn1 = findViewById(R.id.btn5);
        btn2 = findViewById(R.id.btn6);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
