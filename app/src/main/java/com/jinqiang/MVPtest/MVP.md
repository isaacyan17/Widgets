## Android MVP 实践

>示例代码上传在我的repo [Widget](https://github.com/cherishyan/Widgets)中，并长期更新一些有意思的小组件或者功能。Star~

---

#### 1.  MVP or MVC

从年初的时候就了解过MVP的概念，但是一直没有付诸实践，最近需要将项目架构从MVC -> MVP ，由此记录下实践中的心得。

* 如果不太了解MVC 和 MVP的概念和区别，建议搜索学习一下，比如：
  [MVC，MVP 和 MVVM 的图示](http://www.ruanyifeng.com/blog/2015/02/mvcmvp_mvvm.html)
  
  引用腾讯Bugly的话：
  
  > MVC简单来讲，就是 Activity 或者 Fragment 直接与数据层交互，activity 通过 apiProvider 进行网络访问，或者通过 CacheProvider 读取本地缓存，然后在返回或者回调里对 Activity 的界面进行响应刷新。 
    这样的结构在初期看来没什么问题，甚至可以很快的开发出来一个展示功能，但是业务一旦变得复杂了怎么办？
    我们作一个设想，假如一次数据访问可能需要同时访问 api 和 cache，或者一次数据请求需要请求两次 api。对于 activity 来说，它既与界面的展示，事件等有关系，又与业务数据层有着直接的关系，无疑 activity 层会极剧膨胀，变得极难阅读和维护。
    在这种结构下, activity 同时承担了 view 层和 controller 层的工作，所以我们需要给 activity 减负
  
* 不要为了使用而使用

Android项目的复杂度决定了需要使用的框架。如果一个Activity实现了上千行的代码，这个类就很难维护，而且不方便阅读。
但是一个业务并不复杂的项目使用MVP模式反而会增加代码量。所以我一直认为使用何种框架，根据业务需求和项目复杂度来定。

---

#### 2. 搭建MVP分层结构

我们之前使用的MVC框架中，会在Activity或者Fragment中实现大量IO操作(无论是api调用还是cache，都可以看成IO过程)，以及一些业务逻辑处理。
随着功能越来越多，View层会急剧膨胀，这时我们需要将逻辑处理和数据处理分离出来，也就是说我们想达到的目的是View层只负责界面的显隐，而逻辑过程(Presenter)和数据过程(Model)将在其他地方处理。

* 这里看网上的一个工程目录

![图片选自网络](http://upload-images.jianshu.io/upload_images/1115152-13eb96584c88b513.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 `contract,presenter,model` 是我们要关注的包。
 
 contract是连接view层和presenter层的接口：
 
 这里举一个修改用户信息的例子(修改密码，昵称，图片等)
 
 
 ```
 public interface IndividualContract {
     interface View {
 
 
         //         设置用户名称显示
         void setNickName(String name);
 
         //         设置用户性别显示
         void setUserSex(String sex);
 
         //         设置用户签名显示
         void setUserSign(String sign);
 
         //         设置用户账号显示
         void setUserCode(String code);
 
         //         设置用户头像显示
         void setUserHead(String code);
 
         void showDialog();
 
         void hideDialog();
 
     }
 
     interface Presenter {
 
         void initData();
 
         void changeNikeName(String title, String sign);
 
         void changeSex(String title, String gender);
 
         void changeSign(String title, String sign);
 
         void changeHead(String title, String button);
 
         //         设置用户头像显示
         void  setUserHead();
 
         void updateUser();
 
         void hideDialog();
 
         void updateResult(int requestCode, Intent data);
     }
 }
 ```
 
 在`IndividualContract`中，我们实现了修改用户信息过程中需要实现的Presenter逻辑处理，View层的显示处理。
 
 这还看不出来什么，我们直接看看Presenter的实现：
 
 ```
 public class IndividualPresenter implements IndividualContract.Presenter {
     private IndividualContract.View mView;
     private Activity mActivity;
     ChangeUserInfoBean mChangeUserInfoBean;
     public Context getContext(){
         return  mActivity;
     }
 
     public IndividualPresenter(IndividualContract.View view, Activity activity) {
         this.mView = view;
         this.mActivity = activity;
         mChangeUserInfoBean = new ChangeUserInfoBean(this);
     }
     ...
 }
 ```
 
 `IndividualPresenter`实现了` IndividualContract.Presenter`，Activity中需要做的逻辑处理将会在IndividualPresenter实现。
 
 注意实例化的时候，我们传入了view层的对象`IndividualContract.View view`和activity(当然这不是固定的)，有什么用？
 
 比如我们需要在修改头像的时候显示一个ProgressDialog，修改成功以后隐藏它，我们就需要通知View层去做Dialog的显隐，而intent跳转就需要activity的实例，如：
 
```
   @Override
     public void setUserHead() {
         mView.setUserHead( mChangeUserInfoBean.getmUser().getHeadImg());
     }
 ```

view层由Activity或者Fragment实现，到这里我们概括一下：

Contract实现了View层和Presenter层分别要去做的事情，View层实现业务逻辑完成以后的界面处理，Presenter层处理各种逻辑处理，并通知View层处理各种逻辑处理过程中需要展示的界面。

以上一直没有提到数据处理的部分，即代码中我定义的`ChangeUserInfoBean`。单独说数据层是因为在最初Presenter层处理的时候，我没有将数据和逻辑分离出来，导致Presenter同样臃肿。

比如本地对用户信息做了修改以后，我们希望将修改信息上传至服务器，在Presenter中 ：

```
 @Override
    public void updateUser() {
        mView.showDialog();
        mChangeUserInfoBean.changeUser();
    }
```

在`ChangeUserInfoBean`中，我们做网络请求等处理，完成修改用户信息逻辑。**网络请求数据，或者内存持有数据，都应该交给数据层，即Presenter中都是逻辑处理，Model中实现数据持有或者数据API获取。**

至于View层的实现：

```
public class IndividualCenterActivity extends BaseActivity implements IndividualContract.View {
    
        private IndividualContract.Presenter mPresenter;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.individual);
            mPresenter = new IndividualPresenter(this,this);
        }
        
        ...
    
    
        @Override
        public void setUserHead(String headURL) {
            ImageLoader.getInstance().displayImage(headURL, mHeadView, options);
        }
    
        @Override
        public void showDialog() {
            showProgressDialog();
        }
    
        @Override
        public void hideDialog() {
            hideProgressDialog();
        }
}
```

