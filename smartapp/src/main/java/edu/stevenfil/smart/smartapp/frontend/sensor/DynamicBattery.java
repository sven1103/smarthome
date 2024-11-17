package edu.stevenfil.smart.smartapp.frontend.sensor;

import com.vaadin.flow.component.Svg;
import com.vaadin.flow.component.customfield.CustomField;
import java.util.Objects;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class DynamicBattery extends CustomField<Float> {


  private final Svg battery;

  public DynamicBattery(Svg battery) {
    this.battery = Objects.requireNonNull(battery);
  }

  @Override
  protected Float generateModelValue() {
    return 0f;
  }

  @Override
  protected void setPresentationValue(Float newPresentationValue) {
    if (newPresentationValue == null || newPresentationValue.isNaN()) {
      battery.setSvg(getClass().getResourceAsStream("/static/images/battery/alt-battery-5-svgrepo-com.svg"));
      return;
    }

    if (newPresentationValue.compareTo(80f) > 0) {
      battery.setSvg(getClass().getResourceAsStream("/static/images/battery/alt-battery-5-svgrepo-com.svg"));
      battery.addClassNames("status-ok");
      return;
    }

    if (newPresentationValue.compareTo(60f) > 0) {
      battery.setSvg(getClass().getResourceAsStream("/static/images/battery/alt-battery-4-svgrepo-com.svg"));
      battery.addClassNames("status-ok");
      return;
    }

    if (newPresentationValue.compareTo(40f) > 0) {
      battery.setSvg(getClass().getResourceAsStream("/static/images/battery/alt-battery-3-svgrepo-com.svg"));
      battery.addClassNames("status-ok");
      return;
    }

    if (newPresentationValue.compareTo(20f) > 0) {
      battery.setSvg(getClass().getResourceAsStream("/static/images/battery/alt-battery-2-svgrepo-com.svg"));
      battery.addClassNames("status-warning");
      return;
    }
    battery.setSvg(getClass().getResourceAsStream("/static/images/battery/alt-battery-0-svgrepo-com.svg"));
    battery.addClassNames("status-alert");
    }
}
