现在大部分App底部都有一个菜单，实现这个功能有很多办法：

* TabHost+Fragment
* RadioGroup+Fragment
* FragmentTabHost+Fragment
* 5.0以后有个新控件，BottomNavigator
这篇主要介绍下FragmentTabHost配合Fragment使用

首先直接写好xml如下：

    <android.support.v4.app.FragmentTabHost
        <!--这个id必须是@android:id/tabhost-->
        android:id="@android:id/tabhost"
        android:layout_width="match_parent"
        android:layout_height="match_parent" >

        <LinearLayout
            <!--把下面两个FrameLayout和一个TabWidget放在一个LinearLayout里边-->
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical" >

            <FrameLayout
                <!--这个其实是真正的内容，权重设1-->
                android:id="@+id/realtabcontent"
                android:layout_width="fill_parent"
                android:layout_height="0dp"
                android:layout_weight="1" />

            <TabWidget
                <!--没有这个显示不正常，这个有待研究-->
                <!--当tabs在realtabcontent上面时这个tab会出现在顶部，反之底部-->
                android:id="@android:id/tabs"
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:layout_gravity="bottom"
                android:divider="@null" />

            <FrameLayout
                <!--必须有一个FrameLayout名为@android:id/tabcontent-->
                android:id="@android:id/tabcontent"
                android:layout_width="fill_parent"
                android:layout_height="0dp"
                android:layout_weight="0" />

        </LinearLayout>
    </android.support.v4.app.FragmentTabHost>
然后在继承自FragmentActivity的MainActivity中编写如下代码：（AppCompatActivity继承自FragmentActivity故无妨）：

    //定义字符串
    private static final String DISCOVER = "discover";

    //将四个tab添加进去
    addTab(tabHost, DISCOVER, DiscoverFragment.class, getIndicatorView(R.layout.tab_discover));
    addTab(tabHost, MYMUSIC , MyMusicFragment .class, getIndicatorView(R.layout.tab_mymusic));
    addTab(tabHost, FRIENDS , FriendsFragment .class, getIndicatorView(R.layout.tab_friends));
    addTab(tabHost, PERSONAL, PersonalFragment.class, getIndicatorView(R.layout.tab_personal));

    //封装FragmentTabHost的addTab()方法，传入各个参数，addTab()方法的参数有待研究学习
    private static void addTab(FragmentTabHost tabHost, String title, Class<?> fragmentClazz, View indicator) {
      Bundle arguments = new Bundle();
      arguments.putString("text", title);
      tabHost.addTab(tabHost.newTabSpec(title).setIndicator(indicator),
              fragmentClazz, arguments);
    }

    //inflate()方法的参数有待研究学习
    private View getIndicatorView(int resId) {
      View v = null;
      if (resId != 0) {
        v = getLayoutInflater().inflate(resId, null);
      }
      return v;
    }
加入ViewPager实现滑动效果的文章另外整理
