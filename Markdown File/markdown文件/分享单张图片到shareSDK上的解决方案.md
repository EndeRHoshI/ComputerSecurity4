## 分享单张图片到shareSDK上的解决方案

### 总体思路
 * 将数据放到页面中生成一张页面（这一步较为简单，跳过不谈）
 * 将当前页面截图存到本地
 * 将本地的图片通过shareSDK分享到各个平台
***
### 将当前页面截图存到本地
实现这一步有很多种方法，最简单的是直接调用相应的方法来截取当前屏幕的内容

这次要谈的是另一种方法，因为我们想要的是另一个页面，不是当前页面，但是这个"另一个页面"又不能显示出来，具体实现如下：
* 展示View
* 将View转成Bitmap
* 将Bitmap写入SD卡
* 返回写入文件的路径

那么我们先另起一个Activity，将其设为透明的主题样式，将要截取的View动态add到Activity之中（当然也可以写在Activity
的布局文件中）并将其设为INVISIBLE状态,现在这个View就已经呈现在我们眼前，只是我们看不到而已

需要注意的是，虽然内容为透明，但是如果Android的版本是4.0以上的话，屏幕中还是会有状态栏的变化，于是我们继续解决
状态栏的问题，具体如下：

    //有标题栏的透明背景
    @android:style/Theme.Translucent
    //无标题栏的透明背景
    @android:style/Theme.Translucent.NoTitleBar
    //无标题栏无状态栏的全屏透明背景
    @android:style/Theme.Translucent.NoTitleBar.Fullscreen
将以上的style设置进`android:theme`内就可以实现各种效果，但是都并不符合我的要求于是继续先做下去

将View转化为Bitmap的方法如下：

    public static Bitmap convertViewToBitmap(View view) {
      Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
      Bitmap.Config.RGB_565);
      //利用bitmap生成画布
      Canvas canvas = new Canvas(bitmap);
      //把view中的内容绘制在画布上
      view.draw(canvas);
      return bitmap;
    }
其中的View是你要生成图片的那个页面的View，可以这样获取到：

    mView = getLayoutInflater().inflate(R.layout.activity_boys_girls_share, null);
    setContentView(mView);
然后再对它进行初始化，填充数据后，再调用上边的`convertViewToBitmap()`方法，放入View就可以得到对应的Bitmap对象
但是这时有个问题，这个`convertViewToBitmap()`方法在`onCreate()`和`onResume()`等方法中调用时报错，原因是拿不到
View的宽高，因为这时候View还没开始生成，所以又有如下解决方法：

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
      if (hasFocus) {
        saveMyBitmap(convertViewToBitmap(mView));
      }
      super.onWindowFocusChanged(hasFocus);
    }

那就是重写`onWindowFocusChanged()`方法，这个方法在这个Activity得到或者失去焦点的时候 就会就会被调用，所以会储存照片两次，而加上hasFocus的状态判断之后就只储存一次了

还有其他关于`getWidth()`得到零的解决方法：
* [view.getWidth(), view.getHeight() 返回0的问题的解决方案][]
* [Android如何正确获得View的宽和高][]
* [view的getWidth() getHeight() 总是返回0 的几种解决方法][]

其中的`saveMyBitmap()`方法可以保存Bitmap到本地，具体的实现另外再说了

这个`saveMyBitmap()`方法是在要截图的View中实现的，那么截图完毕了，理应`finish()`掉这个Activity，在`finish()`之前传回resultCode如下：

    setResult(RESULT_OK);
    finish();
相应的就要用`startActivityForResult()`方法来启动Activity了，相关资料如下：
* [startActivityForResult和setResult详解][]

[view.getWidth(), view.getHeight() 返回0的问题的解决方案]:http://www.cnblogs.com/sidxu/p/4940370.html
[Android如何正确获得View的宽和高]:http://blog.csdn.net/aiynmimi/article/details/51900420
[view的getWidth() getHeight() 总是返回0 的几种解决方法]:http://www.mamicode.com/info-detail-497475.html
[startActivityForResult和setResult详解]:http://www.cnblogs.com/lijunamneg/archive/2013/02/05/2892616.html
