package com.test.idinputmethod;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.anrongtec.idinputmethod.IDInputEditText;
import com.anrongtec.idinputmethod.IDInputMethod;

public class MainActivity extends AppCompatActivity {

  IDInputMethod idInputView;

  IDInputEditText et;

  EditText et2, et3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    idInputView = findViewById(R.id.idinputview);

    et = findViewById(R.id.et);
    et2 = findViewById(R.id.et2);
    et3 = findViewById(R.id.et3);

    //推荐使用封装好了的IDInputEditText， 继承自EditText,所以和使用EditText没有任何区别
    et.addInputMethod(idInputView);

    //使用默认EditText.
    et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          idInputView.attachEditText(et2);
        } else {
          idInputView.detachEditText(et2);
        }
      }
    });

    //两种方式都不影响正常使用。   也可以参IDInputEditText自己去封装。 唯一的区别：
    //使用EditText，当输入17位时，可以在任何位置插入X
    //使用IDInputEditText  则只允许第18位才能输入X。 详情见IDInputEditText的源码

  }
}
