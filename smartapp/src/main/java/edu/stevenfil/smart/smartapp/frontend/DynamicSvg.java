package edu.stevenfil.smart.smartapp.frontend;

import com.vaadin.flow.component.Svg;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Image;
import java.util.Objects;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class DynamicSvg extends CustomField<String> {

  private Svg image;

  private String trend;

  public DynamicSvg(Svg image) {
    this.image = Objects.requireNonNull(image);
  }

  @Override
  protected String generateModelValue() {
    return trend;
  }

  @Override
  protected void setPresentationValue(String trend) {
    if (trend != null) {
      this.trend = trend;
      setTrend(image, trend);
    }
  }

  private void setTrend(Svg image, String trend) {
    if (Objects.equals(trend, "up")) {
      image.setSvg(getClass().getResourceAsStream("/static/images/caret-up-svgrepo-com.svg"));
    }
    if (Objects.equals(trend, "down")) {
      image.setSvg(getClass().getResourceAsStream("/static/images/caret-down-svgrepo-com.svg"));
    }
    if (Objects.equals(trend, "stable")) {
      image.setSvg(getClass().getResourceAsStream("/static/images/circle-svgrepo-com.svg"));
    }
  }
}
