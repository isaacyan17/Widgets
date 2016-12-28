## 小控件，方法合集
---

#### Python脚本替换Android资源(包名,图片,文件内容)

* 详见： [Python4Android.md](./app/src/main/java/com/jinqiang/py4android/Python4Android.md)

---

#### Android引导页动画(RxJava)

> 最近在考虑用新的形式构建APP的欢迎页和引导页，发现一个不错的过渡动画，主要是图片的灰度和放大效果,进入后延迟一秒开始动画。
使用RxJava的Timer操作符实现延迟。

* 详见：[WelcomeActivity](./app/src/main/java/com/jinqiang/welcomebanner/WelcomeActivity.java)

---

#### H5引导页
> 前端的fullpage展示已经在非常多的站点上使用了，这里尝试将Android的引导页换成fullpage的网页动画，这里是示例图片，添加更多动画元素以后效果应该非常不错。

* 首先我替换了原生的webview控件，改成了腾讯x5的[webviewSDK](http://x5.tencent.com/)，低配的手机上加强了性能体验(虽然感觉冷启动过程好像长了点...)

* 在asserts文件夹中建立一个web项目文件夹，如`web_page`，然后用前端开发工具(我是用的sublime text3)实现fullpage引导页。

* Tencent webview调用本地HTML资源：

```
mWeb.loadUrl("file:///android_asset/web_page/page.html");
```

* 详见: [WebWelcomeActivity](./app/src/main/java/com/jinqiang/welcomebanner/WebWelcomeActivity.java)

---

#### Android MVP 实践

* 详见 [Android MVP.md](./app/src/main/java/com/jinqiang/MVPtest/MVP.md)