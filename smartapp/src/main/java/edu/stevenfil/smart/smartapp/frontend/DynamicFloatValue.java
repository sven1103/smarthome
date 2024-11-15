package edu.stevenfil.smart.smartapp.frontend;

import com.vaadin.flow.component.customfield.CustomField;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class DynamicFloatValue extends CustomField<Float> {

  private DisplayValue valueWrapper;

  private Float value;

  public DynamicFloatValue(DisplayValue valueWrapper) {
    this.valueWrapper = valueWrapper;
    value = Float.NaN;
  }

  @Override
  protected Float generateModelValue() {
    try {
      return  Float.parseFloat(valueWrapper.getText());
    } catch (NumberFormatException e) {
      return Float.NaN;
    }
  }

  @Override
  protected void setPresentationValue(Float aFloat) {
    value = aFloat;
    valueWrapper.setValue(value.toString());
  }
}
