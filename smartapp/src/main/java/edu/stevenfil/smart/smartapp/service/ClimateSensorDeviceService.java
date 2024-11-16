package edu.stevenfil.smart.smartapp.service;

import edu.stevenfil.smart.smartapp.device.sensor.climate.ClimateSensor;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
@Service
public class ClimateSensorDeviceService {

  private final List<ClimateSensor> sensors;

  @Autowired
  public ClimateSensorDeviceService(List<ClimateSensor> climateSensors) {
    this.sensors = climateSensors;
  }

  private static ClimateSensorData convert(ClimateSensor sensor) {
    return new ClimateSensorData(sensor.getFriendlyName(), sensor.temperature().orElse(Float.NaN),
        sensor.humidity().orElse(Float.NaN), sensor.getBatteryStatus().orElse(Float.NaN),
        sensor.trendTemperature(), sensor.trendHumidity(), sensor.getLocation(), sensor.getLastUpdate().orElse(null));
  }

  public List<ClimateSensorData> queryAllSensors() {
    return sensors.stream().map(ClimateSensorDeviceService::convert).toList();
  }


}
