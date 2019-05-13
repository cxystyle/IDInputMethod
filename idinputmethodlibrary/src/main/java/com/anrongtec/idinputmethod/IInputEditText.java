package com.anrongtec.idinputmethod;

public interface IInputEditText {

  void addInputMethod(IInputMethod inputMethod);

  void addSelectionChangeListener(ISelectionChangeListener listener);
}
