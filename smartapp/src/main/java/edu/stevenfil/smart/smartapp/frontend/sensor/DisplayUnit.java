package edu.stevenfil.smart.smartapp.frontend.sensor;

import com.vaadin.flow.component.html.Div;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class DisplayUnit extends Div {

  public DisplayUnit(String unit) {
    addClassName("font-size-l");
    addClassName("font-subtle-bright");
    setText(unit);
  }

  void setUnit(String unit) {
    setText(unit);
  }

}
