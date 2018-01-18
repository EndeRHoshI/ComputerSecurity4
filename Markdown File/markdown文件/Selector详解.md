# Selector详解
***

## 引言
* selector中文的意思选择器，在Android中常常用来作组件的背景，这样做的好处是省去了用代码控制实现组件在不同状态下不同的背景颜色或图片的变换。使用十分方便。

## selector的定义

* selector就是状态列表（StateList）， 它分为两种，一种 **Color-Selector** 和 **Drawable-Selector**。
***
#### Color-Selector
* color-selector 就是颜色状态列表，可以跟color一样使用，颜色会随着组件的状态而改变。文件的位置存储于

  `/res/color/filename.xml`

	在Java中使用是：R.color.filename

	在XML中使用是：@[package]color/filename
#### 语法

  	<?xml version="1.0" encoding="utf-8"?>
  	<selector xmlns:android="http://schemas.android.com/apk/res/android" >
  	  <item
  	    android:color                = "hex_color"        //颜色值，#RGB,$ARGB,#RRGGBB,#AARRGGBB
  	    android:state_pressed        = ["true" | "false"] //是否触摸
  	    android:state_focused        = ["true" | "false"] //是否获得焦点
  	    android:state_selected       = ["true" | "false"] //是否被状态
  	    android:state_checkable      = ["true" | "false"] //是否可选
  	    android:state_checked        = ["true" | "false"] //是否选中
  	    android:state_enabled        = ["true" | "false"] //是否可用
  	    android:state_window_focused = ["true" | "false"] //是否窗口聚焦
          />
  	</selector>
#### 示例
* 在/res/color/文件夹下新建test_color_selector.xml

	  <?xml version="1.0" encoding="utf-8"?>
	  <selector xmlns:android="http://schemas.android.com/apk/res/android">
	    <item android:state_pressed="true" android:color="#ffff0000"/> <!-- pressed -->
	    <item android:state_focused="true" android:color="#ff0000ff"/> <!-- focused -->
	    <item android:color="#ff000000"/> <!-- default -->
	  </selector>
* 调用：

	  <Button
	    android:id="@+id/bt_about"
	    style="@style/Button_style"
	    android:layout_width="250dp"
	    android:layout_height="50dp"
	    android:layout_margin="5dp"
	    android:textColor="@color/test_color_selector" <!-- 在此处调用了color-selector -->
	    android:text="@string/about" />


***
#### Drawable-Selector

* drawable-selector 是背景图状态列表，可以跟图片一样使用，背景会根据组件的状态变化而变化。文件存储于
  `/res/drawable/filename.xml`

	Java中调用：R.drawable.filename

	XML中调用：@[package:]drawable/filename
#### 语法
  	<?xml version="1.0" encoding="utf-8"?>
  	<selector xmlns:android   = "http://schemas.android.com/apk/res/android"
  	  android:constantSize    = ["true" | "false"]//drawable的大小是否当中状态变化，true表示是变化，false表示不变换，默认为false
  	  android:dither          = ["true" | "false"]//当位图与屏幕的像素配置不一样时(例如，一个ARGB为8888的位图与RGB为555的屏幕)会自行递色(dither)。设置为false时不可递色。默认true
  	  android:variablePadding = ["true" | "false"] >//内边距是否变化，默认false
  	    <item
  	      android:drawable             = "@[package:]drawable/drawable_resource"//图片资源
  	      android:state_pressed        = ["true" | "false"] //是否触摸
  	      android:state_focused        = ["true" | "false"] //是否获取到焦点
  	      android:state_hovered        = ["true" | "false"] //光标是否经过
  	      android:state_selected       = ["true" | "false"] //是否选中
  	      android:state_checkable      = ["true" | "false"] //是否可勾选
  	      android:state_checked        = ["true" | "false"] //是否勾选
  	      android:state_enabled        = ["true" | "false"] //是否可用
  	      android:state_activated      = ["true" | "false"] //是否激活
  	      android:state_window_focused = ["true" | "false"] //所在窗口是否获取焦点
  	      />
  	</selector>
#### 示例
* 在/res/drawable/文件夹下新建test_drawable_selector.xml

	  <?xml version="1.0" encoding="utf-8"?>
		<selector xmlns:android="http://schemas.android.com/apk/res/android">
		  <item android:state_selected="true" android:drawable="@drawable/button_bg_press" />
		  <item android:state_focused="true" android:drawable="@drawable/button_bg_press" />
		  <item android:state_pressed="true" android:drawable="@drawable/button_bg_press"  />
		  <item android:drawable="@drawable/button_bg_normol"  />
		</selector>

* 调用：

	  <Button
	    android:id="@+id/bt_about"
	    style="@style/Button_style"
	    android:background="@drawable/test_drawable_selector" <!-- 在这里调用了drawable-selector -->
	    android:layout_width="250dp"
	    android:layout_height="50dp"
	    android:layout_margin="5dp"
	    android:textColor="@color/test_color_selector"
	    android:text="@string/about" />

***
# 总结

* selector是一种很好的方式，实现View状态变化后背景与颜色变化的，可以省去很多逻辑代码，掌握了之后既可以省去很多Java代码，还能写一些漂亮的UI。

* **注意：因为android在解析selector时是从上往下找的，找到一个匹配的即返回。如果第一个item中写了`android：drawable`，android认为满足条件返回了，所以这一条一般应该放到最后**

  原文地址：<http://blog.csdn.net/wenwen091100304/article/details/49667293>
