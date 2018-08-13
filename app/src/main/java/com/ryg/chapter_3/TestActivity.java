package com.ryg.chapter_3;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.ryg.chapter_3.R;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * scrollTo  scrollBy   只能改变view内容  而不能改变view在布局中的位置
 * scrollBy移动的是相对距离
 * scrollTo移动的是绝对距离  但是坐标系并不是手机屏幕上的坐标系  而是以初始位置为0.0原点的相对坐标系
 * <p>
 * <p>
 * 测试：scrollto滑动的时候 执行动画 让view移动位置  但这丝毫没有影响 scrollto的执行 由此可以得出 绝对距离 也是相对于view内而言
 * 也就是不管view怎么移动 view内的坐标系不变化的
 * <p>
 * <p>
 * getScrollX getScrollY初始值都为0
 * 正负值由是否向靠近（0.0）点方向为准   靠近则为正
 * 遗留问题 为啥长按响应不了？
 */
public class TestActivity extends Activity implements OnClickListener,
        OnLongClickListener {

    private static final String TAG = "TestActivity";

    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 30;
    private static final int DELAYED_TIME = 33;

    private Button mButton1;
    private View mButton2;
    private Button testBt;
    private int mCount = 0;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SCROLL_TO: {
                    mCount++;
                    if (mCount <= FRAME_COUNT) {
                        float fraction = mCount / (float) FRAME_COUNT;
                        int scrollX = (int) (fraction * 100);
                        Log.d(TAG, "scrollX=" + scrollX);
                        Log.d(TAG, "前===getScrollX=" + mButton1.getScrollX() + "  getScrollY=" + mButton1.getScrollY());
                        //前  初始值都为0
                        mButton1.scrollTo(scrollX, 4);
//                        if(mCount==20){
//                            Animation animation = AnimationUtils.loadAnimation(TestActivity.this, R.anim.translate);
//                            mButton1.setAnimation(animation);
//                            animation.start();
//                        }

                        Log.d(TAG, "后===getScrollX=" + mButton1.getScrollX() + "  getScrollY=" + mButton1.getScrollY());
                        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
                    }
                    break;
                }

                default:
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
        testBt = (Button) findViewById(R.id.testbt);
        testBt.setOnClickListener(this);
        mButton2 = (TextView) findViewById(R.id.button2);
        mButton2.setOnLongClickListener(this);
        mButton1.setOnLongClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Log.d(TAG, "button1.left=" + mButton1.getLeft());
            Log.d(TAG, "button1.x=" + mButton1.getX());
            Log.d(TAG, "button1.tx=" + mButton1.getTranslationX());
        }
    }

    int count;

    @Override
    public void onClick(View v) {
        if (v == mButton1) {

//这种写法为啥不行？
//            Animation animation = AnimationUtils.loadAnimation(TestActivity.this, R.anim.translate);
//            mButton1.startAnimation(animation);
//            animation.start();


//这种写法为啥行?
            //这种方式 view的参数的确没有改变  点击原来的空白位置 仍然会有事件  新位置则没有事件
//            Animation animation = AnimationUtils.loadAnimation(TestActivity.this, R.anim.translate);
//            mButton1.startAnimation(animation);

//==================================================================================
//直接设置偏移量也可以移动view
//            mButton1.setTranslationX(100);
//            mButton1.setTranslationY(100);

//==================================================================================
            //这是属性动画 可以解决参数不变的问题   x  tx都发生了变化
//            ObjectAnimator.ofFloat(mButton1, "translationX", 0, 100)
//                    .setDuration(1000).start();
//==================================================================================
//奇怪 这种方式 x变化了   但tx没有变化
//            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mButton1
//                    .getLayoutParams();
//            params.width += 100;
//            params.leftMargin += 100;
//            mButton1.requestLayout();
//            mButton1.setLayoutParams(params);
//==================================================================================
            //这种不是view的移动
            final int startX = 0;
            final int deltaX = 100;

            //意思是把0-1之间的数以1s内输出？
            ValueAnimator animator = ValueAnimator.ofInt(0,
                    1).setDuration(1000);
            animator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    float fraction = animator.getAnimatedFraction();
                    count++;
                    Log.d(TAG, "fraction=" + fraction + "  count=" + count);
                    mButton1.scrollTo(startX + (int) (deltaX * fraction), 0);
                }
            });
            animator.start();
//==================================================================================
            //延时策略
            //   mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
        }


        if (v == testBt) {
            Log.d(TAG, "button1.left=" + mButton1.getLeft());
            Log.d(TAG, "button1.x=" + mButton1.getX());
            Log.d(TAG, "button1.tx=" + mButton1.getTranslationX());
            Log.d(TAG, "mButton2.left=" + mButton2.getLeft());
            Log.d(TAG, "mButton2.x=" + mButton2.getX());
            Log.d(TAG, "button2.tx=" + mButton2.getTranslationX());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, "long click", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }
}
