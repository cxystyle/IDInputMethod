package com.test.idinputmethod;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cxystyle.idinputmethod.IDInputEditText;
import com.cxystyle.idinputmethod.IDInputMethod;
import com.cxystyle.idinputmethod.InputCallback;

public class MainActivity extends AppCompatActivity {

  IDInputMethod idInputView;

  IDInputEditText et;

  EditText et2, et3;

  TextView tv, tv2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    idInputView = findViewById(R.id.idinputview);

    et = findViewById(R.id.et);
    et2 = findViewById(R.id.et2);
    et3 = findViewById(R.id.et3);
    tv = findViewById(R.id.tv);
    tv2 = findViewById(R.id.tv2);

    //推荐使用封装好了的IDInputEditText， 继承自AppCompatEditText,所以和使用EditText没有任何区别
    idInputView.addEditText(et);

    //使用默认的edittext
    idInputView.addEditText(et2);

    //两种方式都不影响正常使用。   也可以参IDInputEditText自己去封装。 唯一的区别：
    //使用EditText，当输入17位时，可以在任何位置插入X
    //使用IDInputEditText  则只允许第18位才能输入X。


    //**************************************************************************************
    //使用非EditText

    idInputView.addView(tv);
    idInputView.addView(tv2);

    idInputView.addInputListener(new InputCallback<TextView>() {
      @Override
      public boolean input(Character input, int primaryCode, TextView view) {
        String s = view.getText().toString();
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
          //删除按钮
          if (!TextUtils.isEmpty(s) && s.length() > 1) {
            view.setText(s.substring(0, s.length() - 1));
          } else {
            view.setText("");
          }
        } else if (primaryCode == Keyboard.KEYCODE_DONE) {
          //确定按钮
        } else {
          //1-9 X
          view.setText(s + (input == null ? "" : input));
        }

//        Toast.makeText(MainActivity.this, "input" + input + ", primaryCode" + primaryCode, Toast.LENGTH_SHORT).show();
        //返回值：true则表示自己重写所有按键效果， false则表示继续调用默认按键效果
        return false;
      }
    });



  }
}
