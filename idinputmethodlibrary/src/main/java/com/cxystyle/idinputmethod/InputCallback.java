package com.cxystyle.idinputmethod;

import android.view.View;

public interface InputCallback<T extends View> {

    boolean input(Character input, int primaryCode, T view);

}
