package com.yichang.kaku.member;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.uploadimages.zoom.PhotoView;
import com.yichang.kaku.view.uploadimages.zoom.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends BaseActivity implements OnClickListener {
    private ViewPagerFixed pager;
    private MyPageAdapter adapter;
    private ArrayList<View> listViews = null;

    //当前的位置
    private int location = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        List<Bitmap> bimpList = KaKuApplication.mBimpList;

        pager = (ViewPagerFixed) findViewById(R.id.gallery);
        pager.setOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < bimpList.size(); i++) {
            initListViews(bimpList.get(i));
        }

        adapter = new MyPageAdapter(listViews);
        pager.setAdapter(adapter);
        int id = getIntent().getIntExtra("position", 0);
        pager.setCurrentItem(id);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        long down_time = 0;
        long up_time = 0;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            down_time = System.currentTimeMillis();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            up_time = System.currentTimeMillis();
        }
        if(up_time-down_time<100){
            finish();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            location = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void initListViews(Bitmap bm) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        PhotoView img = new PhotoView(this);
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        listViews.add(img);
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();

    }

    @Override
    protected void onDestroy() {
        KaKuApplication.mBimpList.clear();
        super.onDestroy();
    }

    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;

        private int size;

        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {
            try {
                ((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }


}
