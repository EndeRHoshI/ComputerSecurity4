# Markdown常用语法

简化并且整理了一些网上文章中的MarkDown的常用语法，在有道云笔记编辑模式下可以看到效果是怎么实现的。

原文附在结尾

2016/11/18

***

## 1.标题

最高阶标题
=============

第二阶标题
-------------

类 Atx 形式则是在行首插入 1 到 6 个 # ，对应到标题 1 到 6 阶，可以选择性地「闭合」类 atx 样式的标题，行尾的 # 数量也不用和开头一样（行首的井字符数量决定标题的阶数）

# 这是 H1

## 这是 H2

###### 这是 H6

***

## 2.区块引用 Blockquotes

> This is a blockquote with two paragraphs. Lorem ipsum dolor sit amet,
> consectetuer adipiscing elit. Aliquam hendrerit mi posuere lectus.
> Vestibulum enim wisi, viverra nec, fringilla in, laoreet vitae, risus.
>
> Donec sit amet nisl. Aliquam semper ipsum sit amet velit. Suspendisse
> id sem consectetuer libero luctus adipiscing.

Markdown 也允许你偷懒只在整个段落的第一行最前面加上 >

> This is a blockquote with two paragraphs. Lorem ipsum dolor sit amet,
consectetuer adipiscing elit. Aliquam hendrerit mi posuere lectus.
Vestibulum enim wisi, viverra nec, fringilla in, laoreet vitae, risus.
>
> Donec sit amet nisl. Aliquam semper ipsum sit amet velit. Suspendisse
id sem consectetuer libero luctus adipiscing.

区块引用可以嵌套（例如：引用内的引用），只要根据层次加上不同数量的 > ：

> ## 这是一个标题。
>
> 1.   这是第一行列表项。
> 2.   这是第二行列表项。
>
> 给出一些例子代码：
>
>     return shell_exec("echo $input | $markdown_script");

***

## 3.列表

Markdown 支持有序列表和无序列表。

无序列表使用星号、加号或是减号作为列表标记：

*   Red
*   Green
*   Blue

等同于：

-   Red
-   Green
-   Blue

有序列表则使用数字接着一个英文句点：

1.  Bird
2.  McHale
3.  Parish

列表项目可以包含多个段落，每个项目下的段落都必须缩进 4 个空格或是 1 个制表符：

1.  This is a list item with two paragraphs. Lorem ipsum dolor
    sit amet, consectetuer adipiscing elit. Aliquam hendrerit
    mi posuere lectus.

    Vestibulum enim wisi, viverra nec, fringilla in, laoreet
    vitae, risus. Donec sit amet nisl. Aliquam semper ipsum
    sit amet velit.

2.  Suspendisse id sem consectetuer libero luctus adipiscing.

如果你每行都有缩进，看起来会看好很多，当然，再次地，如果你很懒惰，没有每行缩进，Markdown 也允许：
*   This is a list item with two paragraphs.

    This is the second paragraph in the list item. You're
only required to indent the first line. Lorem ipsum dolor
sit amet, consectetuer adipiscing elit.

*   Another item in the same list.

如果要在列表项目内放进引用，那 > 就需要缩进：

*   A list item with a blockquote:

    > This is a blockquote
    > inside a list item.

如果要放代码区块的话，该区块就需要缩进两次，也就是 8 个空格或是 2 个制表符：

*   一列表项包含一个列表区块：

        <代码写在这>

当然，项目列表很可能会不小心产生，像是下面这样的写法：

1986. What a great season.

换句话说，也就是在行首出现数字-句点-空白，要避免这样的状况，你可以在句点前面加上反斜杠转义。

1986\. What a great season.

***

## 4.代码区块

和程序相关的写作或是标签语言原始码通常会有已经排版好的代码区块，通常这些区块我们并不希望它以一般段落文件的方式去排版，而是照原来的样子显示，Markdown会用 \<pre> 和 \<code> 标签来把代码区块包起来。

要在 Markdown 中建立代码区块很简单，只要简单地缩进 4 个空格或是 1 个制表符就可以，例如，下面的输入：

这是一个普通段落：

    这是一个代码区块。

Markdown 会转换成：

<p>这是一个普通段落：</p>

<pre><code>这是一个代码区块。
</code></pre>

代码区块中，一般的 Markdown 语法不会被转换，像是星号便只是星号，这表示你可以很容易地以 Markdown 语法撰写 Markdown 语法相关的文件。

***

分隔线

你可以在一行中用三个以上的星号、减号、底线来建立一个分隔线，行内不能有其他东西。你也可以在星号或是减号中间插入空格。下面每种写法都可以建立分隔线：

* * *

***

*****

- - -

---------------------------------------

## 5.区段元素

Markdown 支持两种形式的链接语法： 行内式和参考式两种形式。

不管是哪一种，链接文字都是用 [方括号] 来标记。

要建立一个行内式的链接，只要在方块括号后面紧接着圆括号并插入网址链接即可，如果你还想要加上链接的 title 文字，只要在网址后面，用双引号把 title 文字包起来即可，例如：

>This is [an example](http://example.com/ "Title") inline link.
>
>[This link](http://example.net/) has no title attribute.

如果你是要链接到同样主机的资源，你可以使用相对路径：

>See my [About](/about/) page for details.

参考式的链接是在链接文字的括号后面再接上另一个方括号，而在第二个方括号里面要填入用以辨识链接的标记：

>This is [an example] [id] reference-style link.

接着，在文件的任意处，你可以把这个标记的链接内容定义出来。

[id]: http://example.com/  "Optional Title Here"

>链接内容定义的形式为：
>
>* 方括号（前面可以选择性地加上至多三个空格来缩进），里面输入链接文字
>* 接着一个冒号
>* 接着一个以上的空格或制表符
>* 接着链接的网址
>* 选择性地接着 title 内容，可以用单引号、双引号或是括弧包着

下面这三种链接的定义都是相同：

[foo]: http://example.com/  "Optional Title Here"
[foo]: http://example.com/  'Optional Title Here'
[foo]: http://example.com/  (Optional Title Here)

你也可以把 title 属性放到下一行，也可以加一些缩进，若网址太长的话，这样会比较好看：

[id]: http://example.com/longish/path/to/resource/here
    "Optional Title Here"

链接辨别标签可以有字母、数字、空白和标点符号，但是并不区分大小写，因此下面两个链接是一样的：

[link text][a]

[link text][A]

隐式链接标记功能让你可以省略指定链接标记，这种情形下，链接标记会视为等同于链接文字，要用隐式链接标记只要在链接文字后面加上一个空的方括号，如果你要让 "Google" 链接到 google.com，你可以简化成：

[Google][]

然后定义链接内容：

[Google]: http://google.com/

由于链接文字可能包含空白，所以这种简化型的标记内也允许包含多个单词：

Visit [Daring Fireball][] for more information.

然后接着定义链接：

[Daring Fireball]: http://daringfireball.net/

链接的定义可以放在文件中的任何一个地方，我比较偏好直接放在链接出现段落的后面，你也可以把它放在文件最后面，就像是注解一样。
***
## 6.强调

Markdown 使用星号（\*）和底线（\_）作为标记强调字词的符号，被 \* 或 \_ 包围的字词会被转成用 \<em> 标签包围，用两个 \* 或 \_ 包起来的话，则会被转成 \<strong>，例如：

*single asterisks*

_single underscores_

**double asterisks**

__double underscores__

你可以随便用你喜欢的样式，唯一的限制是，你用什么符号开启标签，就要用什么符号结束。

强调也可以直接插在文字中间：

un*frigging*believable

但是如果你的 * 和 _ 两边都有空白的话，它们就只会被当成普通的符号。

如果要在文字前后直接插入普通的星号或底线，你可以用反斜线：

\*this text is surrounded by literal asterisks\*

中划线，删除线的用法如下：

~~这个是删除线~~

***
## 7.代码

如果要标记一小段行内代码，你可以用反引号把它包起来（`），例如：

Use the `printf()` function.

如果要在代码区段内插入反引号，你可以用多个反引号来开启和结束代码区段：

``There is a literal backtick (`) here.``

代码区段的起始和结束端都可以放入一个空白，起始端后面一个，结束端前面一个，这样你就可以在区段的一开始就插入反引号：

A single backtick in a code span: `` ` ``

A backtick-delimited string in a code span: `` `foo` ``

***
## 8.图片

很明显地，要在纯文字应用中设计一个「自然」的语法来插入图片是有一定难度的。

Markdown 使用一种和链接很相似的语法来标记图片，同样也允许两种样式： 行内式和参考式。

>行内式的图片语法看起来像是：
>
>![Alt text](/path/to/img.jpg)
>
>![Alt text](/path/to/img.jpg "Optional title")
>
>详细叙述如下：
>
>* 一个惊叹号 !
>* 接着一个方括号，里面放上图片的替代文字
>* 接着一个普通括号，里面放上图片的网址，最后还可以用引号包住并加上 选择性的 'title' 文字。

参考式的图片语法则长得像这样：

>![Alt text][id]
>
>「id」是图片参考的名称，图片参考的定义方式则和连结参考一样：
>
>[id]: url/to/image  "Optional title attribute"

到目前为止， Markdown 还没有办法指定图片的宽高，如果你需要的话，你可以使用普通的 \<img> 标签。

图片测试：
>![Alt text](https://github.com/EndeRHoshI/Photo/raw/master/%E5%A4%B4%E5%83%8F.png "恐龙-github")
>
>这里的图片是使用了自己创建的github图床的图片，在github中上传图片后，在github中打开图片，然后将图片地址中的blob改为raw即可。
>
>七牛云图床更为简单，只需要在上传图片后，在图片右侧的操作项下选择“复制外链”，就可以得到外链了
>
>![Alt text](http://ogtt75s5d.bkt.clouddn.com/%E5%A4%B4%E5%83%8F.png "恐龙-七牛云")
>
>储存空间->内容管理->右侧的“操作”->复制外链
>
>![Alt text](http://ogtt75s5d.bkt.clouddn.com/%E4%B8%83%E7%89%9B%E4%BA%91%E7%A4%BA%E4%BE%8B.png "示例图片")
>
>**注意：** 储存空间是公开的才能获取外链
***

## 9.其它

* ### 自动链接

  Markdown 支持以比较简短的自动链接形式来处理网址和电子邮件信箱，只要是用尖括号包起来， Markdown 就会自动把它转成链接。一般网址的链接文字就和链接地址一样，例如：

  ><http://example.com/>

  邮址的自动链接也很类似，只是 Markdown 会先做一个编码转换的过程，把文字字符转成 16 进位码的 HTML 实体，这样的格式可以糊弄一些不好的邮址收集机器人，例如：

  ><address@example.com>

* ### 反斜杠

  Markdown 可以利用反斜杠来插入一些在语法中有其它意义的符号，例如：如果你想要用星号加在文字旁边的方式来做出强调效果（但不用 \<em> 标签），你可以在星号的前面加上反斜杠：

  >\*literal asterisks\*

  Markdown 支持以下这些符号前面加上反斜杠来帮助插入普通的符号：

  >\   反斜线
  >
  >`   反引号
  >
  >\*   星号
  >
  > _   底线
  >
  > {}  花括号
  >
  > []  方括号
  >
  > ()  括弧
  >
  > \#   井字号
  >
  > \+   加号
  >
  > \-   减号
  >
  > \.   英文句点
  >
  > !   惊叹号

***

## 10.Markdown 免费编辑器

* ### Windows 平台

  * [MarkdownPad][]
  * [MarkPad][]
* ### Linux 平台

  * ReText
* ### Mac 平台

  * [Mou][]
* ### 在线编辑器

  * [Markable.in][]
  * [Dillinger.io][]
* ### 浏览器插件

  * MaDe (Chrome)
  * [Minimalist Markdown Editor]
* ### 高级应用

  * Sublime Text 2 + MarkdownEditing / 教程

[MarkdownPad]:http://markdownpad.com/
[MarkPad]:http://code52.org/DownmarkerWPF/
[Mou]:http://25.io/mou/
[Dillinger.io]:http://dillinger.io/
[Markable.in]:http://ttscoff.github.io/MarkdownEditing/
[Minimalist Markdown Editor]:https://chrome.google.com/webstore/detail/minimalist-markdown-edito/pghodfjepegmciihfhdipmimghiakcjf?utm_source=chrome-app-launcher-info-dialog

***
内容已经简化，完整版：<http://wowubuntu.com/markdown/index.html#precode>

简化版：<http://wowubuntu.com/markdown/basic.html>
