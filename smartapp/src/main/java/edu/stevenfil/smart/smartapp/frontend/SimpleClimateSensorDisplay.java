package edu.stevenfil.smart.smartapp.frontend;

import com.vaadin.flow.component.Svg;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.binder.Binder;
import edu.stevenfil.smart.smartapp.service.ClimateSensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class SimpleClimateSensorDisplay extends Div {

  private static final Logger log = LoggerFactory.getLogger(SimpleClimateSensorDisplay.class);
  private final Binder<ClimateSensorData> binder;

  private DynamicFloatValue temperatureValue;
  private DynamicFloatValue humidityValue;

  private Div temperatureContainer;
  private Div humidityContainer;

  private Div displayTitle;
  private Div displayContent;
  private Div displayFooter;

  public SimpleClimateSensorDisplay(ClimateSensorData sensorData) {
    addClassName("gap-m");
    addClassName("border-m");
    addClassName("border-rounded-m");
    addClassName("border-color-bright");
    addClassNames("flex", "flex-vertical", "flex-grow");
    addClassName("display-item-small");

    displayTitle = new Div();
    displayTitle.addClassName("display-title");

    displayContent = new Div();
    displayContent.addClassName("display-content");

    displayFooter = new Div();
    displayFooter.addClassName("display-footer");

    add(displayTitle, displayContent, displayFooter);

    var title = new Span("Display Title");
    displayTitle.add(title);

    var dynamicTitle = new DynamicStringValue(title);

    var updatedSince = new Span("Updated Since");
    displayFooter.add(updatedSince);

    var trendTemp = new Svg(getClass().getResourceAsStream("/static/images/circle-svgrepo-com.svg"));
    trendTemp.addClassName("image-size-s");
    trendTemp.addClassName("svg-color-bright");

    var trendHumidity = new Svg(getClass().getResourceAsStream("/static/images/circle-svgrepo-com.svg"));
    trendHumidity.addClassName("image-size-s");
    trendHumidity.addClassName("svg-color-bright");

    var temperature = DisplayValue.createNaN();
    temperatureContainer = new DisplayValueWithUnit(temperature, new DisplayUnit("°C"), trendTemp);

    var humidity = DisplayValue.createNaN();
    humidityContainer = new DisplayValueWithUnit(humidity, new DisplayUnit("%"), trendHumidity);

    var trendContainerTemp = new DynamicSvg(trendTemp);
    var trendContainerHumidity = new DynamicSvg(trendHumidity);

    temperatureValue = new DynamicFloatValue(temperature);
    humidityValue = new DynamicFloatValue(humidity);

    displayContent.add(temperatureContainer, humidityContainer);

    binder = new Binder<>(ClimateSensorData.class);
    binder.forField(temperatureValue).bindReadOnly(ClimateSensorData::temperature);
    binder.forField(humidityValue).bindReadOnly(ClimateSensorData::humidity);
    binder.forField(dynamicTitle).bindReadOnly(ClimateSensorData::location);
    binder.forField(trendContainerTemp).bindReadOnly(ClimateSensorData::trendTemperature);
    binder.forField(trendContainerHumidity).bindReadOnly(ClimateSensorData::trendHumidity);
    update(sensorData);
  }

  public void update(ClimateSensorData climateSensorData) {
    binder.setBean(climateSensorData);
  }

 }