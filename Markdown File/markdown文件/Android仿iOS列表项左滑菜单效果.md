## Android仿iOS列表项左滑菜单效果
***
* 首先放入相应的文件

  ![Alttext](http://ogtt75s5d.bkt.clouddn.com/Android%E4%BB%BFiOS%E5%88%97%E8%A1%A8%E9%A1%B9%E5%B7%A6%E6%BB%91%E8%8F%9C%E5%8D%95%E6%95%88%E6%9E%9C.png "Android仿iOS列表项左滑菜单效果")

* 然后写xml布局：
      <com.fetuscare.phone.swipemenu.SwipeMenuListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="@null"
        android:listSelector="@color/transparent" />
* 在活动中创建SwipeMenuListView对象并设置adaptor
      private SwipeMenuListView mSwipeMenuListView;
      mSwipeMenuListView = (SwipeMenuListView) findViewById(R.id.listView);
      mSwipeMenuListView.setAdapter(contactsAdapter);
* 然后创建creator对象并把creator装入
      SwipeMenuCreator creator = new SwipeMenuCreator() {
  	  @Override
  	  public void create(SwipeMenu menu) {
  		SwipeMenuItem openItem = new SwipeMenuItem(AidListActivity.this);
  		openItem.setBackground(new ColorDrawable(Color.GREEN));
  		openItem.setWidth(dp2px(90));
  		openItem.setTitle("移除");
  		openItem.setTitleSize(20);
  		openItem.setTitleColor(Color.WHITE);
  		menu.addMenuItem(openItem);
    	}
  	};
  	mSwipeMenuListView.setMenuCreator(creator);

      public int dp2px(float dipValue) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	  }
* 在`getView()`中绑定点击事件，在switch语句中就可以实现各种事件
      mSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        //index的值就是在SwipeMenu依次添加SwipeMenuItem顺序值，类似数组的下标。
          switch (index) {
          case 0:
        	deleteAidContacts(item.getId());
        	break;
          }
          //false : 当用户触发其他地方的屏幕时候，自动收起菜单。  
          //true : 不改变已经打开菜单的样式，保持原样不收起。为false时会导致添加第一位联系人时菜单收起不完全
          return true;
        }
      });
