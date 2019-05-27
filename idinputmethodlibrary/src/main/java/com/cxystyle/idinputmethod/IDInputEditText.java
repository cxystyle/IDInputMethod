package com.cxystyle.idinputmethod;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 自定义身份证输入框
 *
 * @author cxy
 * @data 2018-12-27
 */
public class IDInputEditText extends androidx.appcompat.widget.AppCompatEditText
        implements IInputEditText {

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

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (listener != null) {
            listener.onSelectionChange(selStart, selEnd);
        }
    }

    @Override
    public void addSelectionChangeListener(ISelectionChangeListener listener) {
        this.listener = listener;
    }
}
