package com.anrongtec.idinputmethod;

import android.widget.EditText;

public interface IInputMethod {

  void attachEditText(EditText editText);

  void detachEditText(EditText editText);
}
