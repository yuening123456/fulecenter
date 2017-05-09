package cn.ucai.fulicenter_2017.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.ucai.fulicenter_2017.data.net.GoodsModel;
import cn.ucai.fulicenter_2017.data.net.IGoodsModel;
import cn.ucai.fulicenter_2017.data.utils.ImageLoader;
import cn.ucai.fulicenter_2017.data.utils.L;


/**
 * Created by yao on 2017/4/24.
 */

public class AutoSlideLoopView extends ViewPager {
    FlowIndicator mFlowIndicator;
    int mCount;//图片的数量
    Handler mHandler;
    Timer mTimer;
    ArrayList<String> mGoodsList;
    IGoodsModel model;
    boolean isBoolean=true;

    public Timer getTimer() {
        return mTimer;
    }

    public void setTimer(Timer mTimer) {
        this.mTimer = mTimer;
    }

    public AutoSlideLoopView(Context context) {
        super(context);
    }

    public AutoSlideLoopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListener();
        initHandler();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case 0:
                isBoolean=false;
                break;
            case 1:
                isBoolean=true;
                break;
            case 2:
                isBoolean=false;
                break;

        }
        L.e("main","action"+ev.getAction());
        return super.onTouchEvent(ev);
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                    AutoSlideLoopView.this.setCurrentItem(getCurrentItem() + 1);
            }
        };
    }

    private void setListener() {
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFlowIndicator.setFocus(position% mGoodsList.size() );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class GoodsAdapter extends PagerAdapter {
        Context context;
        ArrayList<String> goodsList;

        public GoodsAdapter(Context context, ArrayList<String> goodsList) {
            this.context = context;
            this.goodsList = goodsList;
            model=new GoodsModel();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView ivGoods = new ImageView(context);
            ivGoods.setLayoutParams(new LinearLayout.LayoutParams(100, 250));
            container.addView(ivGoods);
            ImageLoader.downloadImg(context,ivGoods,goodsList.get(position%mGoodsList.size()));
            return ivGoods;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }

    public void startPlay(Context context, ArrayList<String> goodsList, final FlowIndicator flowIndicator) {
        mGoodsList = goodsList;
        mCount = goodsList.size();
        GoodsAdapter adapter = new GoodsAdapter(context, goodsList);
        this.setAdapter(adapter);
        mFlowIndicator = flowIndicator;
        mFlowIndicator.setFocus(0);
        mFlowIndicator.setCount(mCount);
        MyScroller myScroller = new MyScroller(context);
        myScroller.setDuration(2000);
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this,myScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                L.e("main","goodList"+mGoodsList.size());
                    mHandler.sendEmptyMessage(0);

            }
        }, 1000, 2000);
    }

    class MyScroller extends Scroller {
        int duration;

        public void setDuration(int duration) {
            this.duration=duration;
        }

        public MyScroller(Context context) {
            super(context);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }
    }

}
