package edu.stevenfil.smart.smartapp.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import edu.stevenfil.smart.smartapp.frontend.sensor.SimpleClimateSensorDisplay;
import edu.stevenfil.smart.smartapp.service.ClimateSensorData;
import edu.stevenfil.smart.smartapp.service.ClimateSensorDeviceService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;


@Route("")
public class HomeUI extends Div {

  private final ClimateSensorDeviceService climateSensorDeviceService;

  private Div leftColumn;
  private Div rightColumn;
  private Div mainColumn;

  @Autowired
  public HomeUI(ClimateSensorDeviceService service) {
    this.climateSensorDeviceService = service;

    addClassName("main");

    this.leftColumn = new Div();
    leftColumn.addClassName("left-column");
    this.rightColumn = new Div();
    rightColumn.addClassName("right-column");
    this.mainColumn = new Div();
    mainColumn.addClassName("main-column");

    add(leftColumn, mainColumn, rightColumn);

    UI.getCurrent().setPollInterval(100000);

    setSensorData(service.queryAllSensors());

    UI.getCurrent().addPollListener(listener -> setSensorData(service.queryAllSensors()));
  }

  void setSensorData(Collection<ClimateSensorData> sensors) {
    mainColumn.removeAll();
    mainColumn.add(sensors.stream().map(SimpleClimateSensorDisplay::new)
        .toArray(SimpleClimateSensorDisplay[]::new));
  }


}
