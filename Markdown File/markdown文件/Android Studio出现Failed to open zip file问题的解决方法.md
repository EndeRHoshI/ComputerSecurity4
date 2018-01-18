## Android Studio出现Failed to open zip file问题的解决方法
***
最近从github上下载的项目遇到这样的编译问题：
![](http://ogtt75s5d.bkt.clouddn.com/Android%20Studio%E5%87%BA%E7%8E%B0Failed%20to%20open%20zip%20file%E9%97%AE%E9%A2%98%E7%9A%84%E8%A7%A3%E5%86%B3%E6%96%B9%E6%B3%951.png)

在网上搜索了一下，发现问题出在gradle-wrapper.properties

一般出现这种问题的项目都是因为使用了某个版本的gradle-x.x-all.zip，而gradle-x.x-all.zip无法直接在studio中下载下来。

解决这个问题的办法有好几个：

1. 可以把gradle-wrapper.properties里修改了gradle的版本，与之前没有报错的gradle版本一致，然后再修改项目build.gradle文件中的com.android.tools.build:gradle的版本号，具体是多少也是参照以前那些没有报错的项目。

2. 直接在网上找到gradle-3.3-all.zip下载下来，不要解压缩，放在类似下面的目录中 C:\Users\Administrator\.gradle\wrapper\dists\gradle-3.3-all\55gk2rcmfc6p2dg9u9ohc3hw9，这个办法没有试过，按理说是可以的。下载地址在这里找： http://services.gradle.org/distributions/ ，里面各个版本都有。

3. 最简单的方法，也是我采用的方法，就是改一下gradle-wrapper.properties中的distributionUrl将distributionUrl=https\://services.gradle.org/distributions/gradle-x.x-all.zip改成distributionUrl=http\://services.gradle.org/distributions/gradle-x.x-all.zip

其实这里的区别就是https和http。因为我发现 http://services.gradle.org/distributions/ 中也是没带s的，所以就这样试了下，但奇怪的是每个版本的包文件其实下载地址也是带s的，在浏览器中可以直接下载，那么问题应该出在studio了。

原文地址：http://www.jcodecraeer.com/a/anzhuokaifa/Android_Studio/2017/0317/7691.html
