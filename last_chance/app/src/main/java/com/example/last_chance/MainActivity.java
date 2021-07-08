package com.example.last_chance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    ImageButton btn1, btn2, btn3, btn4;

    LinearLayout ll;
    My_View mv;

    String[] file_name;

    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        if (init_file()) {
            init_wiget();
            init_draw();
            init_image();
        } else {
            MainActivity.this.finish();
        }
    }

    private boolean init_file() {
        Intent intent = getIntent();
        file_name = intent.getStringArrayExtra("file");

        if (file_name == null) {
            Toast.makeText(this.getApplicationContext(), "서버 접속이 불가합니다. 잠시 후 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(this.getApplicationContext(), "서버에 접속했습니다. 잠시만 기다리세요.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    public void init_image() {
        show_image();
        mv.set_name(file_name[cnt]);
        if (++cnt == file_name.length) {
            cnt = 0;
        }
    }

    public void show_image() {
        My_Thread thread = new My_Thread(ll, "http://192.168.0.13/image/" + file_name[cnt]);
        thread.start();
    }

    public void init_draw() {
        mv = new My_View(MainActivity.this);
        ll.addView(mv);
    }


    public void init_wiget() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        ll = findViewById(R.id.ll);

        btn1.setOnClickListener(MainActivity.this);
        btn2.setOnClickListener(MainActivity.this);
        btn3.setOnClickListener(MainActivity.this);
        btn4.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                mv.clear_data();
                init_image();
                break;
            case R.id.btn2:
                mv.remove_data();
                break;
            case R.id.btn3:
                mv.clear_data();
                break;
            case R.id.btn4:
                Intent intent = new Intent(getApplicationContext(), My_Check.class);
                intent.putExtra("test", mv.get_mv_object());
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                mv.send_data();
                init_image();
                mv.clear_data();
                break;
        }
    }
}

