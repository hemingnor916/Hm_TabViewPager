# Hm_TabViewPager
告别繁琐，简单几个步骤用ViewPagere实现TabHost。 A few steps to make a TabHost from ViewPager.

    step 1:
    Copy the file "Hm_TabViewPager.java" to your project .
    
    step 2:
    <hm.hm_tabviewpager.Hm_TabViewPager
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </hm.hm_tabviewpager.Hm_TabViewPager>

    step 3:
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hm_TabViewPager tabViewPager = (Hm_TabViewPager) findViewById(R.id.pager);
        tabViewPager.setIcon_normal(new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher});// required
        tabViewPager.setIcon_selected(new int[]{R.drawable.ic_launcher2, R.drawable.ic_launcher2});// required
        tabViewPager.setTabTitle(new String[]{"page 1", "page 2"});// required
        tabViewPager.setTabBackgroundColor(Color.parseColor("#f3f3f3"));// default: #f1f1f1
        tabViewPager.setTabTitleColor(Color.parseColor("#333333"));// default: #333333
        tabViewPager.setDividerColorBetweenViewPagerAndTab(Color.parseColor("#000000"));// default: #efefef
        tabViewPager.setNoScroll(false);// default: false

        Hm_TabViewPager.ViewPager_ pager = tabViewPager.getViewPager();
        Adapter adapter = new Adapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

    }



#Contact me
Email: he.mingyang@qq.com
