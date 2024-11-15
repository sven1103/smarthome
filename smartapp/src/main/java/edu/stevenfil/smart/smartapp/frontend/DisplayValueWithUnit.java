package edu.stevenfil.smart.smartapp.frontend;

import com.vaadin.flow.component.Svg;
import com.vaadin.flow.component.html.Div;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class DisplayValueWithUnit extends Div {

  DisplayValueWithUnit(DisplayValue value, DisplayUnit unit) {
    addClassNames("flex", "content-centered", "font-size-xl", "align-items-baseline");
    addClassName("gap-s");
    addDisplayValue(value, unit);
  }

  DisplayValueWithUnit(DisplayValue value, DisplayUnit unit, Svg trend) {
    this(value, unit);
    addTrend(trend);
    addDisplayValue(value, unit);
  }

  private void addDisplayValue(DisplayValue value, DisplayUnit unit) {
    add(value, unit);
  }

  private void addTrend(Svg trend) {
    add(trend);
  }

}
