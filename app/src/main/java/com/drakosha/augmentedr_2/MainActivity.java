package com.drakosha.augmentedr_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import static java.lang.Thread.sleep;

public class MainActivity extends Activity {

    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    //private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            sleep(10000);
//            startActivity(new Intent("com.drakosha.augmentedr_2.AR_Activity"));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void startAR(View view) {
        startActivity(new Intent("com.drakosha.augmentedr_2.AR_Activity"));
    }

    public void displayInformation(View view) {

        //relativeLayout = (RelativeLayout) findViewById(R.id.popup_element2);
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.question_mark_popup,null);

//        popupWindow = new PopupWindow(container,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
//        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,100,1300);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        popupWindow = new PopupWindow(container, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        container.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                popupWindow.dismiss();
                return true;
            }
        });

    }

    public void chooseObject(View view) {
        startActivity(new Intent("com.drakosha.augmentedr_2.AR_Activity"));
    }

    public void chooseEnvironment(View view) {
        startActivity(new Intent("com.drakosha.augmentedr_2.AR_Activity"));
    }
}
