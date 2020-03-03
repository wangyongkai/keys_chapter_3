package com.ryg.chapter_3;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Test3Activity extends Activity {

    private static final String TAG = "TestActivity";

    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 30;
    private static final int DELAYED_TIME = 33;

    private Button mButton1;
    private View mButton2;
    private Button testBt;
    private int mCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("999999999999999999999999");

                ObjectAnimator.ofFloat(mButton1, "translationX", 0, 100).setDuration(100).start();


            }
        });

    }


}
