package edu.stevenfil.smart.smartapp.frontend.sensor;

import com.vaadin.flow.component.html.Div;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class DisplayValue extends Div {

  public DisplayValue(String value) {
    addClassName("font-color-blue");
    addClassName("font-xl");
    setText(value);
  }

  public static DisplayValue createNaN() {
    return new DisplayValue("NaN");
  }

  void setValue(String value) {
    setText(value);
  }

}
