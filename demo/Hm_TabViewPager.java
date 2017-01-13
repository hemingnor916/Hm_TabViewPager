package hm.hm_tabviewpager;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/14.
 * 1.编写layout文件
 * 2.
 */

public class Hm_TabViewPager extends LinearLayout {
    Context context;
    ViewPager_ pager;
    int icon_normal[];
    int icon_selected[];
    String tabTitle[];
    int tabTitleColor = Color.parseColor("#333333");
    int tabBackgroundColor = Color.parseColor("#f1f1f1");
    int dividerColorBetweenViewPagerAndTab = Color.parseColor("#efefef");
    ImageView iconImageViews[];
    private boolean noScroll = false;

    public Hm_TabViewPager(Context context) {
        super(context);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        initView_prepare();

    }

    public Hm_TabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        initView_prepare();

    }

    public int getDividerColorBetweenViewPagerAndTab() {
        return dividerColorBetweenViewPagerAndTab;
    }

    public void setDividerColorBetweenViewPagerAndTab(int dividerColorBetweenViewPagerAndTab) {
        this.dividerColorBetweenViewPagerAndTab = dividerColorBetweenViewPagerAndTab;
    }

    public boolean isNoScroll() {
        return noScroll;
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    public int getTabBackgroundColor() {
        return tabBackgroundColor;
    }

    public void setTabBackgroundColor(int tabBackgroundColor) {
        this.tabBackgroundColor = tabBackgroundColor;
    }

    public int getTabTitleColor() {
        return tabTitleColor;
    }

    public void setTabTitleColor(int tabTitleColor) {
        this.tabTitleColor = tabTitleColor;
    }

    public String[] getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String[] tabTitle) {
        this.tabTitle = tabTitle;
    }

    public int[] getIcon_normal() {
        return icon_normal;
    }

    public void setIcon_normal(int[] icon_normal) {
        this.icon_normal = icon_normal;
    }

    public int[] getIcon_selected() {
        return icon_selected;
    }

    public void setIcon_selected(int[] icon_selected) {
        this.icon_selected = icon_selected;
    }

    void initView_prepare() {
        pager = new ViewPager_(context);
        pager.setId(123);//required a view id to avoid crash.


        final View rootView = getRootView();
        ViewTreeObserver vto2 = rootView.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                initView(getMeasuredWidth(), getMeasuredHeight());
            }
        });
    }

    private void initView(int parentWidth, int parentHeight) {

        int width = getMeasuredWidth();
        /*
        添加pager
         */
        int pagerHeight = (int) (parentHeight * 0.87);
        addView(pager, LayoutParams.MATCH_PARENT, pagerHeight);
        /*
        分割线
         */
        TextView tv_divider = new TextView(context);
        tv_divider.setBackgroundColor(dividerColorBetweenViewPagerAndTab);
        addView(tv_divider, ViewGroup.LayoutParams.MATCH_PARENT, 2);
        /*
        添加底部的Tab
         */
        LinearLayout tabParent = new LinearLayout(context);
        tabParent.setOrientation(LinearLayout.HORIZONTAL);
        tabParent.setBackgroundColor(tabBackgroundColor);
        int tabHeight = parentHeight - pagerHeight;
        iconImageViews = new ImageView[icon_normal.length];
        for (int i = 0; i < icon_normal.length; i++) {
            final LinearLayout tabChild = new LinearLayout(context);
            tabChild.setOrientation(LinearLayout.VERTICAL);
            final ImageView iv = new ImageView(context);
            iconImageViews[i] = iv;
            if (i == 0) {
                iv.setImageResource(icon_selected[0]);
            } else {
                iv.setImageResource(icon_normal[i]);
            }
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER);
            tv.setText(tabTitle[i]);
            tv.setTextColor(tabTitleColor);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (tv.getTextSize() * 0.8));
            tabChild.addView(iv, LayoutParams.MATCH_PARENT, (int) (tabHeight * 0.60));
            tabChild.addView(tv, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            tabChild.setPadding(0, 10, 0, 10);
            tabParent.addView(tabChild);

            final int finalI = i;
            tabChild.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(finalI);

                }
            });
        }
        addView(tabParent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    public ViewPager_ getViewPager() {
        return pager;
    }


    public class ViewPager_ extends ViewPager {
        ViewPager_ pager = this;
        OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < iconImageViews.length; i++) {
                    if (i == position) {
                        iconImageViews[i].setImageResource(icon_selected[i]);
                    } else {
                        iconImageViews[i].setImageResource(icon_normal[i]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };


        public ViewPager_(Context context) {
            super(context);
            //重写addOnPageChangeListener
            addOnPageChangeListener(onPageChangeListener);
        }

        //从xml引用，会调用此方法
        public ViewPager_(Context context, AttributeSet attrs) {
            super(context, attrs);
            //重写addOnPageChangeListener
            addOnPageChangeListener(onPageChangeListener);

        }

        @Override
        public void setCurrentItem(int item) {
            if (noScroll) {
                super.setCurrentItem(item, false);
            } else {
                super.setCurrentItem(item, true);
            }
        }

        /**
         * 禁止滑动
         *
         * @param arg0
         * @return
         */
        @Override
        public boolean onTouchEvent(MotionEvent arg0) {
            if (noScroll)
                return false;
            else
                return super.onTouchEvent(arg0);
        }

        /**
         * 禁止滑动
         *
         * @param arg0
         * @return
         */
        @Override
        public boolean onInterceptTouchEvent(MotionEvent arg0) {
            if (noScroll)
                return false;
            else
                return super.onInterceptTouchEvent(arg0);
        }

    }

}





