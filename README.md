# IDInputMethod
自定义身份证输入法, 默认限制输入长度和数字，

![](https://github.com/cxystyle/IDInputMethod/blob/master/images/demo1.jpg)

## 引用
```gradle
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.cxystyle:IDInputMethod:1.0.0'
	}

```
## 使用EditText的食用方法

```xml

  <com.cxystyle.idinputmethod.IDInputEditText
      android:id="@+id/et"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintTop_toTopOf="parent"
      />

  <com.cxystyle.idinputmethod.IDInputMethod
      android:id="@+id/idinputview"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:background="@color/keyboard_background"
      android:focusable="true"
      android:focusableInTouchMode="true"
      android:keyBackground="@drawable/key_background"
      android:keyTextColor="@color/keyboard_key_text_color"
      android:keyTextSize="@dimen/keyboard_text_size"
      android:paddingTop="1px"
      android:shadowColor="@color/keyboard_shadow_color"
      android:shadowRadius="0"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintRight_toRightOf="parent"
      ></com.cxystyle.idinputmethod.IDInputMethod>
```

```java
//获取输入法控件，添加EditText即可快速集成
IDInputMethod idInputView = findViewById(R.id.idinputview);
IDInputEditText idInputEditText = findViewById(R.id.et);

//推荐使用封装好了的IDInputEditText， 继承自AppCompatEditText,所以和使用EditText没有任何区别
idInputView.addEditText(idInputEditText);
//或者使用默认的edittext
idInputView.addEditText(editText);

//两种方式都不影响正常使用， 唯一的区别：
//使用EditText，当输入17位时，可以在任何位置插入X
//使用IDInputEditText  则只允许第18位才能输入X。
```

## 使用其他或者自定义view的食用方法
稍后加，下班了

