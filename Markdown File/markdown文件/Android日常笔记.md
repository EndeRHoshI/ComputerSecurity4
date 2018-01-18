## 日常开发学习笔记
***
### listView中item的部分点击效果
* 令item中某部件`android:clickable="true"`时候，这个部件就是可以点击并且有按压效果的，其父布局可以设置`android:listSelector="@color/transparent"`，这样父布局就不会有点击的效果
***
### onSharedPreferenceChanged 不工作
* 尝试调用这些方法来注册监听器：

      @Override
      protected void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
          .registerOnSharedPreferenceChangeListener(this);
      }

      @Override
      protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
          .unregisterOnSharedPreferenceChangeListener(this);
      }
***
### textView的"资源无法找到"错误
* `setText()`方法中传入了整形int，这时这个int代表的是resId，如果没有相应的资源就会报错，所以如果要传入一个int型数据的话要记得写成`setText("" + int)`;

***
### handler无法正常发送信息
* 代码中提示警告，我的handler对象应该是static的，否则可能会发生泄漏，但是我忽视了这个警告，没有用static来声明它，于是每次程序运行时，只有第一次时可以正常接收到send出去的msg，将相关变量和方法用static修饰后解决，原因有待研究

![](http://ogtt75s5d.bkt.clouddn.com/handler%E6%97%A0%E6%B3%95%E6%AD%A3%E5%B8%B8%E5%8F%91%E9%80%81%E6%B6%88%E6%81%AF.png "handler无法正常发送信息")

***
### `setBackgroud()`方法过时导致程序闪退

* LinearLayout.setBackgroud(Drawable background)其实已经过时了，不推荐使用该方法，因为在Api16之后才有这个方法也就是Android 4.1 Jelly Bean (API level 16) 之后才有这个方法，如果在这之前的手机，调用这个方法，就会出现NoSuchMethodError错误所以如果使用该方法就要做兼容处理，当然，也可以使用setBackgroundResource方法代替这个方法是从Api 1开始就有，所以就不用担心了。

### 红米note4X运行程序时报错，无法运行
* 我新买的红米note4X无法正常运行程序，报错如下：
![](http://ogtt75s5d.bkt.clouddn.com/%E9%83%A8%E5%88%86%E6%89%8B%E6%9C%BA%E6%97%A0%E6%B3%95%E8%BF%90%E8%A1%8C%E7%A8%8B%E5%BA%8F.jpg "部分手机无法运行程序")
处理如下：打开手机的"开发者选项"，关闭掉"启用MIUI优化"，重启手机，再次跑程序，能够正常运行

### gradle下载失败或不全导致无法bulid的处理方法
* 在配置环境时，由于"某些原因"导致gradle下载失败或者不全，所以在bulid时会提示如下：
![](http://ogtt75s5d.bkt.clouddn.com/gradle%E4%B8%8D%E5%85%A8%E5%AF%BC%E8%87%B4%E6%97%A0%E6%B3%95build.png "gradle不全导致无法build")
* 解决方法如下：
 1. 打开你的C:\Users\Administrator\.gradle\wrapper\dists\gradle-3.3-all\55gk2rcmfc6p2dg9u9ohc3hw9目录下（我的是win7系统），查看里边的gradle版本
 2. 然后在浏览器中键入地址"http://downloads.gradle.org/distributions/gradle-2.14-all.zip"，修改版本号为你本地的版本号，等待他下载完
 3. 最后把这个zip文件拉到1.所说的文件目录下，然后重启AS，再次编译一次，应该就没有问题了
