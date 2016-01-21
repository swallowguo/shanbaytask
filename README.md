# shanbaytask
这是一个简易分级阅读程序，实现了以下功能：
1. 打开后看到文章列表
2. 点击列表某项，打开文章
3. 文章界面有一个按钮，点击则在文章中高亮在单词列表中出现的单词
4. 文章界面有一个slide-bar，从0到6（对应单词表中每个单词信息的level)，可以拖动过滤高亮的单词
5. 文章内容实现两边对齐
6. 单词实现点击高亮

下图TextFragment界面的示意图，包括slide-bar、生词switch、取词switch和用于显示的TextView。
生词switch负责开启和关闭功能3：高亮在单词列表中出现的单词；
取词switch负责开启和关闭功能6：单词实现点击高亮；
slide-bar可以在任何时候滑动，最终停留的状态将影响高亮单词的等级；
TextView实现了两边对齐排版，效果不错，但是每一段的首行有些不太理想。

![image](https://github.com/swallowguo/shanbaytask/blob/master/picture/TextFragment.png)
