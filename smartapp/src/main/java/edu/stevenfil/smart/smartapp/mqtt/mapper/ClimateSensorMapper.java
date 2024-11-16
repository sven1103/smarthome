package edu.stevenfil.smart.smartapp.mqtt.mapper;

import static org.apache.logging.log4j.LogManager.getLogger;

import edu.stevenfil.smart.smartapp.device.sensor.climate.ClimateSensor;
import edu.stevenfil.smart.smartapp.exceptions.SmarthomeException;
import edu.stevenfil.smart.smartapp.mqtt.MessageParsingStrategy;
import edu.stevenfil.smart.smartapp.mqtt.TopicObserver;
import edu.stevenfil.smart.smartapp.mqtt.data.TemperatureHumidityData;
import org.apache.logging.log4j.Logger;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class ClimateSensorMapper implements TopicObserver {

  private final ClimateSensor sensor;

  private final MessageParsingStrategy<TemperatureHumidityData> parsingStrategy;
  private final String topic;

  private static final Logger log = getLogger(ClimateSensorMapper.class);


  public ClimateSensorMapper(String topic, ClimateSensor sensor,
      MessageParsingStrategy<TemperatureHumidityData> parsingStrategy) {
    this.topic = topic;
    this.sensor = sensor;
    this.parsingStrategy = parsingStrategy;
  }


  @Override
  public String interestedTopic() {
    return topic;
  }

  @Override
  public void consume(String message) {
    log.info("Received message: {}", message);
    try {
      var data = parsingStrategy.parse(message);
      updateSensorData(sensor, data);
    } catch (SmarthomeException e) {
      log.error(e);
    }

  }

  private static void updateSensorData(ClimateSensor sensor, TemperatureHumidityData data) {
    log.info("Updating sensor data: {}", data);
    sensor.update(data.humidity(), data.temperature());
    sensor.setBatteryStatus(data.batteryStatus());
  }
}
