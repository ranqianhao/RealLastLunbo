package com.example.ranqianhao.reallastlunbo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends Activity implements OnPageChangeListener {

    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化布局
        initViews();

        // 初始化Model数据
        initData();

        // 初始化Controller 控制器
        initAdapter();

//        //轮询
//        new Thread() {
//            public void run() {
//                isRunning = true;
//                while (isRunning) {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    // 往下跳一位
//                    runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            System.out.println("设置当前位置: " + viewPager.getCurrentItem());
//                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//                        }
//                    });
//                }
//            }
//            ;
//        }.start();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
//      viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象
        point_container = (LinearLayout) findViewById(R.id.point_container);

        tv_desc = (TextView) findViewById(R.id.tv_desc);


    }

    //重写ondestroy,让轮播事件停止
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }


    //初始化数据
    private void initData()  {
        //首先初始化图片资源
         imageResIds = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};
        // 文本描述
        contentDescs = new String[]{
                "测试数据1",
                "测试数据2",
                "测试数据3",
                "测试数据4",
        };

        ImageView imageView;
        imageViewList = new ArrayList<ImageView>();
        View pointView;

       ViewPager.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);
//
//            // 加小白点, 指示器
//            pointView = new View(this);
//            pointView.setBackgroundResource(R.drawable.selector_bg_point);
//            layoutParams = new LinearLayout.LayoutParams(5, 5);
//            if (i != 0)
//      //          layoutParams.leftMagin = 10;
//
            // 设置默认所有都不可用
  //          pointView.setEnabled(false);
    //        point_container.addView(pointView, layoutParams);
        }

    }

    private void initAdapter(){
        point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        //适配器
        viewPager.setAdapter(new MyAdapter());

        int pos = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE / 2 %imageViewList.size());
        viewPager.setCurrentItem(500000);
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount(){
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view,  Object object) {
            return view == object;
        }

    private void initViews() {
    }



    // 1. 返回要显示的条目内容, 创建条目
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // System.out.println("instantiateItem初始化: " + position);
        // container: 容器: ViewPager
        // position: 当前要显示条目的位置 0 -> 4

        //newPosition = position % 5
        int newPosition = position % imageViewList.size();

        ImageView imageView = imageViewList.get(newPosition);
        // a. 把View对象添加到container中
        container.addView(imageView);
        // b. 把View对象返回给框架, 适配器
        return imageView; // 必须重写, 否则报异常
    }

    // 2. 销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // object 要销毁的对象
        //System.out.println("destroyItem销毁: " + position);
        container.removeView((View) object);
    }
}

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        //System.out.println("onPageSelected: " + position);
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);

        //for (int i = 0; i < ll_point_container.getChildCount(); i++) {
        //          View childAt = ll_point_container.getChildAt(position);
        //          childAt.setEnabled(position == i);
        //      }
        // 把之前的禁用, 把最新的启用, 更新指示器
        point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        point_container.getChildAt(newPosition).setEnabled(true);

        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }
}