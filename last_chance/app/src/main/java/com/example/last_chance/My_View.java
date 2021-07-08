package com.example.last_chance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;


final public class My_View extends View {

    Paint paint;
    My_Value my_value;

    ArrayList<int[][]> xy_list = new ArrayList<>();

    int startX, startY, stopX, stopY;
    boolean check = false;

    String file_name;

    public My_View(Context context) {
        super(context);

        init_paint();
    }


    public void set_name(String name) {
        file_name = name;
        my_value = new My_Value(name);
    }

    private void init_data(int num, int num2, int[][] data) {
        String[] class_id = getResources().getStringArray(R.array.object);

        if (num2 == 0) {
            my_value.set_Objects(class_id[num], false, data);
        } else {
            my_value.set_Objects(class_id[num], true, data);
        }
    }

    private void init_paint() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < xy_list.size(); i++) {
            canvas.drawRect(xy_list.get(i)[0][0], xy_list.get(i)[0][1], xy_list.get(i)[1][0], xy_list.get(i)[1][1], paint);
        }
        if (check) {
            canvas.drawRect(startX, startY, stopX, stopY, paint);
            check = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                stopX = startX;
                stopY = startY;
                //System.out.println("start xy : " + startX + ", " + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                stopX = (int) event.getX();
                stopY = (int) event.getY();
                if (startX < stopX && startY < stopY) {
                    check = true;
                    /*Handler delayHandler = new Handler();
                    delayHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    }, 5);*/
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (startX < stopX && startY < stopY) {
                    //System.out.println("stop xy : " + stopX + ", " + stopY);
                    get_class_id(new int[][]{{startX, startY}, {stopX, stopY}});
                } else if (startX != stopX && startY != stopY) {
                    Toast.makeText(getContext(), "(↘) 방향으로 그려 주십시오.",
                            Toast.LENGTH_SHORT).show();
                    invalidate();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /*public float round(int temp) {
        return (float) (Math.round(temp * 100) / 100.0);
    }*/


    public void send_data() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(my_value);

        My_Thread2 thread2 = new My_Thread2(jsonOutput, file_name.substring(0, file_name.length() - 4));
        thread2.start();
    }

    public void remove_data() {
        int last = my_value.get_object_size();
        if (last > 0) {
            xy_list.remove(last - 1);
            my_value.remove_object(last - 1);
        }
        invalidate();
    }

    public void clear_data() {
        xy_list.clear();
        my_value.clear_object();
        invalidate();
    }

    public ArrayList<My_Objects> get_mv_object() {
        return my_value.get_object();
    }

    public void get_class_id(final int[][] temp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(" 대상을 선택하세요.").setIcon(R.drawable.lib2).setItems(R.array.object,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which1) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setTitle(" 상태를 선택하세요.").setIcon(R.drawable.lib2).setItems(R.array.hide,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, final int which2) {
                                        ////////////////////////////
                                        xy_list.add(temp);
                                        init_data(which1, which2, temp);
                                        invalidate();
                                        ////////////////////////////
                                    }
                                }).setCancelable(false).setPositiveButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        invalidate();
                                    }
                                }).show();
                    }
                }).setCancelable(false).setPositiveButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        invalidate();
                    }
                }).show();
    }
}

