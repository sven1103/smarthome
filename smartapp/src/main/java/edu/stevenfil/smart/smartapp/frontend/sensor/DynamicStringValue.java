package edu.stevenfil.smart.smartapp.frontend.sensor;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.customfield.CustomField;
import java.util.Objects;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class DynamicStringValue extends CustomField<String> {

  private final HtmlContainer container;

  public DynamicStringValue(HtmlContainer container) {
    this.container = Objects.requireNonNull(container);
  }

  @Override
  protected String generateModelValue() {
    return container.getText();
  }

  @Override
  protected void setPresentationValue(String newPresentationValue) {
    container.setText(newPresentationValue);
  }
}
