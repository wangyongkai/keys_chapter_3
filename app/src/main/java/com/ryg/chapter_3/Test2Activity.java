package com.ryg.chapter_3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Test2Activity extends Activity {

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
        setContentView(R.layout.activity_test2);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println("b-x=====" + mButton1.getScrollX() + "  y====" + mButton1.getScrollY());


                //底层调用scrollTo  写多少就是移动多少 正负由是否滑出view决定  滑出view则看不见
                //mButton1.scrollBy(80, 50);


                //问题：scrollTo中的坐标到底是相对于谁的坐标？
                // 但是坐标系并不是手机屏幕上的坐标系  而是以初始位置为0.0原点的相对坐标系
                //结论：第一次执行 scrollBy和scrollTo执行效果一样，因为mscrollx值初始为0。
                mButton1.scrollTo(80, 50);//多次调用没有效果

                System.out.println("a-x=====" + mButton1.getScrollX() + "  y====" + mButton1.getScrollY());


            }
        });

    }


}
