## 登陆界面的session检查流程
***
* `onCreate()`中首先`checkLogin()`检查登陆状态
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        checkLogin();
        // hold welcome page
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            //在这里作页面的就绪检查或者版本检查
            checkVersion();
            checkPageReady();
            //然后把作为flag的mHoldFinish设置为true表示已经完成hold了
            mHoldFinish = true;
          }
        }, DEFAULT_HOLD_DURATION);//这里的duration可以设置比较合适的值
      }
* `checkLogin()`中拿到首选项中的sessionid进行非空判断，非空的话调用检查登录状态的接口进行下一步检查
      /**
       * 检查登录状态
       */
      private void checkLogin() {
        String sessionId = Pref.getAppPreference().getSessionId();

        if (TextUtils.isEmpty(sessionId)) {
          postLoginCheckResult(false);//空的话post一个false的检查结果
          return;
        }
        checkSession(sessionId);
      }
* post检查结果的方法如下
      /**
       * 触发登录检查结束事件
       * @param result 登录检查结果
       */
      private void postLoginCheckResult(boolean result) {
        mLoginCheckFinish = true;//表示登陆检查已经完成
        mLoginCheckSuccess = result;//这个是检查的结果，影响后边的跳转
        checkPageReady();
      }
* 检查页面是否就绪（登录检查+页面停留，结束）
      private void checkPageReady() {
        //hold的flag或者检查未完成都不能跳转
        if (!mHoldFinish || !mLoginCheckFinish) {
          return;
        }
        onPageReady();
      }
* 页面就绪回调
      private void onPageReady() {
        //检查成功的话跳转到主页面，否则调到登陆页面
        startActivity(new Intent(mLoginCheckSuccess ?
        		Actions.MAIN : Actions.LOGIN));
        finish();
      }
* 接口的请求方法如下：
      /**
       * 检查SessionId是否可用
       * @param sessionId SessionId
       */
      private void checkSession(@NotNull String sessionId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Keys.SESSION, sessionId);

        HttpUtil.post(mRequestQueue, ServerPath.CHECK_SESSION, params,
          new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject data) {
              //收到的JSON包非空且result字段为true则表示成功，传给post方法
              boolean checkResult = data != null && data.optBoolean(Keys.RESULT);
              postLoginCheckResult(checkResult);
            }
          },
          new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              接收出错的话设置false
              postLoginCheckResult(false);
            }
          }
        );
      }
