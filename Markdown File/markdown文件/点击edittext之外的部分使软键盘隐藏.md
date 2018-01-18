### 点击指定组件之外的其他组件使软键盘隐藏的方法
***
* 首先写一个让软键盘隐藏的方法
      /**
  	 * 隐藏软键盘的方法，用于点击EditText之外的部分时使软键盘隐藏
  	 * @param activity
  	 */
  	public static void hideSoftKeyboard(Activity activity) {
  		InputMethodManager inputMethodManager = (InputMethodManager) activity
  				.getSystemService(Activity.INPUT_METHOD_SERVICE);
  		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
  				.getWindowToken(), 0);
  	}
* 然后为其他组件注册监听，当点击到其他组件时调用隐藏软键盘的方法
      public void setupUI(View view) {
          //Set up touch listener for non-text box views to hide keyboard.
          if(!(view instanceof EditText)) {
              view.setOnTouchListener(new OnTouchListener() {
                  public boolean onTouch(View v, MotionEvent event) {
                      hideSoftKeyboard(Main.this);  //Main.this是我的activity名
                      return false;
                  }
              });
          }
          //If a layout container, iterate over children and seed recursion.
          if (view instanceof ViewGroup) {
              for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                  View innerView = ((ViewGroup) view).getChildAt(i);
                  setupUI(innerView);
              }
          }
      }
**注意**：上述方法可以为所有子控件注册监听，具体实现根据项目要求重写
* 最后在`onCreat()`之类的方法中调用`setupUI()`就可以为其他组件注册监听器，实现隐藏了
