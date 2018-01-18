### 防止软键盘遮挡View（按钮、输入框等）的解决办法
***
* 首先在布局中定义根布局为main
      android:id="@+id/main"
* 然后在代码中取得**根布局**和你想要不被遮挡的**最下方那个View**的对象
      private LinearLayout main;
      private Button action_login;

      main = (LinearLayout) findViewById(R.id.main);
      action_login = (Button) findViewById(R.id.action_login);
* 再添加监听器
      addLayoutListener(main, action_login);
* 其中的addLayoutListener()方法如下：
      /**
       * 用于防止软键盘遮挡
       *  1、获取main在窗体的可视区域
       *  2、获取main在窗体的不可视区域高度
       *  3、判断不可视区域高度
       *      1、大于100：键盘显示  获取Scroll的窗体坐标
       *                           算出main需要滚动的高度，使scroll显示。
       *      2、小于100：键盘隐藏
       *
       * @param main 根布局
       * @param scroll 需要显示的最下方View
       */
       public void addLayoutListener(final View main, final View scroll) {
       main.getViewTreeObserver().addOnGlobalLayoutListener(
         new ViewTreeObserver.OnGlobalLayoutListener() {
           @Override
           public void onGlobalLayout() {
               Rect rect = new Rect();
               main.getWindowVisibleDisplayFrame(rect);
               int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
               if (mainInvisibleHeight > 100) {
                   int[] location = new int[2];
                   scroll.getLocationInWindow(location);
                   int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                   main.scrollTo(0, srollHeight);
               } else {
                   main.scrollTo(0, 0);
               }
           }
       });
       }
**注意：**与一些监听事件同用时，当接收到事件监听而要`setVisable(View.GONE)`时，最好使用`setVisable(View.INVISIBLE)`，
因为使用前者的话会导致布局改变然后导致监听异常，不能保证View不被遮挡

另：其实把这种布局写进一个LinearLayout里边，然后再放在一个ScrollView里，也能起到很好的缩放效果，令输入框不被阻挡，而又不用设置`android:windowSoftInputMode`属性，这个需要灵活处理
