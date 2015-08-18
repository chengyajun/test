package com.example.chengyajun.slidingmenu.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by chengyajun on 2015/8/7.
 */
public class SlidingMenu extends HorizontalScrollView {


    //屏幕宽度
    private int mScreenWidth;
    //menu的右边距
    private int mMenuRight = 50;
    //所有内容
    private LinearLayout mWapper;

    private ViewGroup mMenu;
    private ViewGroup mContent;

    //menu宽度和 content宽度, content宽度就是屏幕宽度
    private int mMenuWidth;

    //第一次调用、
    private boolean once = false;

    //未定义属性时使用
    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        //menu右边距,转化为像素值
        mMenuRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());

    }

    //重写onMeasure（）,决定内部子view的宽高，并决定该控件的宽高

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!once) {
            //获取LinearLayout
            mWapper = (LinearLayout) getChildAt(0);
            //获取菜单和内容
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);

            //设置菜单和内容的宽高
            mMenu.getLayoutParams().width = mScreenWidth - mMenuRight;
            mMenuWidth = mScreenWidth - mMenuRight;
            mContent.getLayoutParams().width = mScreenWidth;

            //决定子view视图的宽高后该视图的宽高就决定了，无需再设定
            once = true;
        }

    }


    //重写onLayout（）,决定视图的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }

    }

    //重写TouchEvent（），监听手抬起时的动作
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP: {

                //判断此时scrollview滚动的距离与menu宽度比较，如果大于一般则隐藏，如果小于一半则显示
                int scrollX = this.getScrollX();
                int helfMenuWidth = mMenuWidth/2;
                if(scrollX>=helfMenuWidth){
                    //隐藏menu
                    this.smoothScrollTo(mMenuWidth,0);
                }else{
                    //显示menu
                    this.smoothScrollTo(0,0);
                }
                return true;
            }


        }
        return super.onTouchEvent(ev);
    }
}