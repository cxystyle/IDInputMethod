package com.anrongtec.idinputmethod;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 身份证输入法控件
 *
 * @author cxy
 * @data 2018-12-27
 */
public class IDInputMethod extends KeyboardView implements IInputMethod {

  private static final String DIGITS = "0123456789Xx";

  private static final int KEY_X_CODE = 88;

  private static final int MAX_INPUT_LENGTH = 18;

  private int inputLength;

  private Keyboard mKeyboard;

  private Drawable keyEnableBackground, keyDisableBackground, mKeyBackground;

  private Paint mPaint;

  private EditText mEditText;

  private int focusStart, focusEnd;

  public IDInputMethod(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public IDInputMethod(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public IDInputMethod(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(Context context) {
    keyEnableBackground =
        ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.key_background, null);
    keyDisableBackground =
        ResourcesCompat.getDrawable(getContext().getResources(), R.color.keyboard_key_background,
            null);

    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setTextSize(56);
    mPaint.setTextAlign(Paint.Align.CENTER);
    mPaint.setAlpha(255);

    setVisibility(GONE);

    mKeyboard = new Keyboard(context, R.xml.keyboard);

    setKeyboard(mKeyboard);
    setEnabled(true);
    setPreviewEnabled(false);

    setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
      @Override public void onPress(int primaryCode) {

      }

      @Override public void onRelease(int primaryCode) {

      }

      @Override public void onKey(int primaryCode, int[] keyCodes) {
        if (mEditText == null) {
          return;
        }
        int focusPosition = mEditText.getSelectionStart();
        int focusEndPosition = mEditText.getSelectionEnd();
        Editable editable = mEditText.getText();
        switch (primaryCode) {
          case Keyboard.KEYCODE_DELETE:
            if (editable != null && editable.length() > 0) {
              if (focusEndPosition > focusPosition) {
                editable.delete(focusPosition, focusEndPosition);
              } else if (focusPosition > 0) {
                editable.delete(focusPosition - 1, focusPosition);
              }
            }
            break;
          case Keyboard.KEYCODE_CANCEL:
            setVisibility(View.GONE);
            break;
          case Keyboard.KEYCODE_DONE:
            setVisibility(View.GONE);
            break;
          case KEY_X_CODE:
            if (!isLastOneToInput()) {
              return;
            }
          default:
            editable.insert(focusPosition, Character.toString(((char) primaryCode)));
            break;
        }
      }

      @Override public void onText(CharSequence text) {
        //toast("onText:" + text);
      }

      @Override public void swipeLeft() {
        //toast("swipeLeft");
      }

      @Override public void swipeRight() {
        //toast("swipeRight");
      }

      @Override public void swipeDown() {
        //toast("swipeDown");
      }

      @Override public void swipeUp() {
        //toast("swipeUp");
      }
    });
  }

  @Override
  public void attachEditText(EditText editText) {
    mEditText = editText;

    hideSoftInput();

    setEditText(editText);

    setShowSoftInputOnFocus();

    show();

    invalidate();
  }

  @Override
  public void detachEditText(EditText editText) {
    hide();
  }

  private void show() {
    if (getVisibility() != View.VISIBLE) {
      setVisibility(View.VISIBLE);
    }
  }

  private void hide() {
    if (getVisibility() != View.GONE) {
      setVisibility(View.GONE);
    }
  }

  @SuppressLint("ClickableViewAccessibility") private void setEditText(EditText editText) {

    inputLength = mEditText.getText().toString().length();
    mEditText.setKeyListener(DigitsKeyListener.getInstance(DIGITS));
    mEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(MAX_INPUT_LENGTH) });
    mEditText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override public void afterTextChanged(Editable s) {
        inputLength = s.length();

        invalidate();
      }
    });

    mEditText.setOnTouchListener(new OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        if (mEditText.hasFocus()) {
          show();
        }
        return false;
      }
    });

    if (editText instanceof IInputEditText) {
      ((IInputEditText) editText).addSelectionChangeListener(new ISelectionChangeListener() {
        @Override public void onSelectionChange(int start, int end) {
          focusStart = start;
          focusEnd = end;

          invalidate();
        }
      });
    } else {
      focusStart = MAX_INPUT_LENGTH - 1;
    }
  }

  //private void toast(String str) {
  //  Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
  //}

  private void setShowSoftInputOnFocus() {
    if (android.os.Build.VERSION.SDK_INT <= 10) {//4.0以下
      mEditText.setInputType(InputType.TYPE_NULL);
    } else {
      ((AppCompatActivity) mEditText.getContext()).getWindow()
          .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
      try {
        Class<EditText> cls = EditText.class;
        Method setShowSoftInputOnFocus;
        setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
            boolean.class);
        setShowSoftInputOnFocus.setAccessible(true);
        setShowSoftInputOnFocus.invoke(mEditText, false);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void hideSoftInput() {
    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
        .hideSoftInputFromWindow(
            ((Activity) mEditText.getContext()).getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
  }

  @Override public void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Keyboard keyboard = getKeyboard();
    if (keyboard == null) return;
    List<Keyboard.Key> keys = keyboard.getKeys();

    if (keys != null && keys.size() > 0) {

      for (Keyboard.Key key : keys) {

        if (key.codes[0] == KEY_X_CODE || isNumKey(key.codes[0])) {
          if (key.label == null) {
            key.label = "";
          }
          if (isInputMax() || (key.codes[0] == KEY_X_CODE && !isLastOneToInput())) {
            mKeyBackground = keyDisableBackground;

            mPaint.setColor(
                ResourcesCompat.getColor(getResources(), R.color.keyboard_key_text_color_disable,
                    null));
          } else {
            int[] currentDrawableState = key.getCurrentDrawableState();
            mKeyBackground = keyEnableBackground;
            mKeyBackground.setState(currentDrawableState);

            mPaint.setColor(
                ResourcesCompat.getColor(getResources(), R.color.keyboard_key_text_color, null));
          }

          mKeyBackground.setBounds(key.x, key.y + 1, key.x + key.width, key.y + key.height);
          mKeyBackground.draw(canvas);

          drawText(canvas, key);
        }
      }
    }
  }

  private void drawText(Canvas canvas, Keyboard.Key key) {
    Rect rect = new Rect(key.x, key.y, key.x + key.width, key.y + key.height);
    Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
    int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
    // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
    mPaint.setTextAlign(Paint.Align.CENTER);
    canvas.drawText(key.label.toString(), rect.centerX(), baseline, mPaint);
  }

  /**
   * 是否为输入最后一个数字
   * 条件为 已输入17个数字，并且光标在最后
   */
  private boolean isLastOneToInput() {
    return MAX_INPUT_LENGTH - 1 == inputLength ? (focusStart == MAX_INPUT_LENGTH - 1 ? true : false)
        : false;
  }

  /**
   * 是否为输入最大值
   */
  private boolean isInputMax() {
    return MAX_INPUT_LENGTH == inputLength ? true : false;
  }

  private boolean isNumKey(int code) {
    if (code >= 48 && code <= 57) {
      return true;
    }
    return false;
  }

  private CharSequence adjustCase(CharSequence label) {
    if (mKeyboard.isShifted() && label != null && label.length() < 3
        && Character.isLowerCase(label.charAt(0))) {
      label = label.toString().toUpperCase();
    }
    return label;
  }
}
