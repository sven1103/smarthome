package edu.stevenfil.smart.smartapp;

import static org.apache.logging.log4j.LogManager.getLogger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.stevenfil.smart.smartapp.config.SmartAppConfig;
import edu.stevenfil.smart.smartapp.config.SmartAppConfig.MqttConfig;
import edu.stevenfil.smart.smartapp.device.sensor.climate.ClimateSensor;
import edu.stevenfil.smart.smartapp.exceptions.SmarthomeException;
import edu.stevenfil.smart.smartapp.mqtt.SmartAppClient;
import edu.stevenfil.smart.smartapp.mqtt.data.TemperatureHumidityData;
import edu.stevenfil.smart.smartapp.mqtt.mapper.ClimateSensorMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
@Configuration
public class ApplicationConfig {

  private static final Logger log = getLogger(ApplicationConfig.class);


  @Bean
  public MqttConfig mqttConfig(SmartAppConfig smartAppConfig) {
    return smartAppConfig.mqttConfig();
  }

  @Bean
  public List<ClimateSensor> climateSensors(SmartAppConfig smartAppConfig, SmartAppClient smartAppClient) {
    var device = smartAppConfig.devices().stream().findFirst().get();
    var climateSensor = new ClimateSensor();
    climateSensor.setLocation(device.getLocation());
    climateSensor.setFriendlyName(device.getFriendlyName());
    var mapper = new ClimateSensorMapper(device.getFriendlyName(), climateSensor, (String message) -> {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        return objectMapper.readValue(message, TemperatureHumidityData.class);
      } catch (JsonProcessingException e) {
        log.error(e);
        return new TemperatureHumidityData(0f, 0f, 0f, 0);
      }
    });
    smartAppClient.subscribe(mapper);
    var sensors = new ArrayList<ClimateSensor>();
    sensors.add(climateSensor);
    return sensors;
  }

}
