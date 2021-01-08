package com.drakosha.augmentedr_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import static java.lang.Thread.sleep;

public class MainActivity extends Activity {

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

    public void chooseObject(View view) {
        startActivity(new Intent("com.drakosha.augmentedr_2.AR_Activity"));
    }

    public void chooseEnvironment(View view) {
        startActivity(new Intent("com.drakosha.augmentedr_2.AR_Activity"));
    }
}
