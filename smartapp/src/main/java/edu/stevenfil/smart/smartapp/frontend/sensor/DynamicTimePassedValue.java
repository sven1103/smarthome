package edu.stevenfil.smart.smartapp.frontend.sensor;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.customfield.CustomField;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class DynamicTimePassedValue extends CustomField<Instant> {

  private final HtmlContainer container;

  public DynamicTimePassedValue(HtmlContainer container) {
    this.container = Objects.requireNonNull(container);
  }

  @Override
  protected Instant generateModelValue() {
    return null;
  }

  @Override
  protected void setPresentationValue(Instant lastUpdated) {
    if (lastUpdated == null) {
      return;
    }
    Duration sinceLastUpdate = Duration.between(lastUpdated, Instant.now());
    var renderedDuration = renderDuration(sinceLastUpdate);
    container.setText(timeInfoMessage(renderedDuration));
  }

  private static String renderDuration(Duration duration) {
    if (duration.toMinutes() < 1) {
      return "< 1 min.";
    } else if (duration.toMinutes() < 2) {
      return "%s min.".formatted(duration.toMinutes());
    }
      return "%s min.".formatted(duration.toMinutes());
  }

  private static String timeInfoMessage(String formattedDuration) {
    return "last update: %s".formatted(formattedDuration);
  }
}
