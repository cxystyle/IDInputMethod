package com.anrongtec.idinputmethod;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * 自定义身份证输入框
 *
 * @author cxy
 * @data 2018-12-27
 */
public class IDInputEditText extends android.support.v7.widget.AppCompatEditText
    implements IInputEditText {

  private IInputMethod mInputMethod;

  private ISelectionChangeListener listener;

  public IDInputEditText(Context context) {
    super(context);
  }

  public IDInputEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public IDInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onSelectionChanged(int selStart, int selEnd) {
    super.onSelectionChanged(selStart, selEnd);
    if (listener != null) {
      listener.onSelectionChange(selStart, selEnd);
    }
  }

  @Override
  public void addInputMethod(IInputMethod inputMethod) {
    mInputMethod = inputMethod;
    setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          if (mInputMethod != null) {
            mInputMethod.attachEditText((EditText) v);
          }
        } else {
          if (mInputMethod != null) {
            mInputMethod.detachEditText((EditText) v);
          }
        }
      }
    });
  }

  @Override public void addSelectionChangeListener(ISelectionChangeListener listener) {
    this.listener = listener;
  }
}
