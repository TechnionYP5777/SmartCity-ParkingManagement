package gui.manager.app;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

class IntegerTextField extends JFXTextField {
    private final IntegerProperty val;
    private final int minVal;
    private final int maxVal;
 
    // Getters & Setters
    public int  getValue() {
    	return val.getValue();
    }
    public void setValue(int intNum) {
    	val.setValue(intNum);
    }
    public IntegerProperty valueProperty() {
    	return val;
    }
    
	IntegerTextField(int minVal, int maxVal, int initialVal) {
      if (minVal > maxVal) 
        throw new IllegalArgumentException(
          "IntegerTextField min value " + minVal + " greater than max value " + maxVal
        );
      if (maxVal < minVal) 
        throw new IllegalArgumentException(
          "IntegerTextField max value " + minVal + " less than min value " + maxVal
        );
      if (minVal > initialVal || initialVal > maxVal) 
        throw new IllegalArgumentException(
          "IntegerTextField initialValue " + initialVal + " not between " + minVal + " and " + maxVal
        );
 
      // initialize the field values.
      this.minVal = minVal;
      this.maxVal = maxVal;
      val = new SimpleIntegerProperty(initialVal);
      setText(initialVal + "");
 
      final IntegerTextField IntegerTextField = this;
 
      // make sure the value property is clamped to the required range
      // and update the field's text to be in sync with the value.
      val.addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> __, Number oldVal, Number newVal) {
          if (newVal == null)
			IntegerTextField.setText("");
		else {
            if (newVal.intValue() < IntegerTextField.minVal) {
              val.setValue(IntegerTextField.minVal);
              return;
            }
 
            if (newVal.intValue() > IntegerTextField.maxVal) {
              val.setValue(IntegerTextField.maxVal);
              return;
            }
 
            if (newVal.intValue() != 0 || textProperty().get() != null && !"".equals(textProperty().get()))
				IntegerTextField.setText(newVal + "");
          }
        }
      });
 
      // restrict key input to numerals.
      this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
        @Override public void handle(KeyEvent ¢) {
          if (!"0123456789".contains(¢.getCharacter()))
			¢.consume();
        }
      });
      
      // ensure any entered values lie inside the required range.
      this.textProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue<? extends String> __, String oldValue, String newValue) {
          if (newValue == null || "".equals(newValue)) {
            val.setValue(0);
            textProperty().setValue("0");
            return;
          }
 
          final int intValue = Integer.parseInt(newValue);
 
          if (IntegerTextField.minVal > intValue || intValue > IntegerTextField.maxVal)
			textProperty().setValue(oldValue);
          val.set(Integer.parseInt(textProperty().get()));
        }
      });
    }
  }
