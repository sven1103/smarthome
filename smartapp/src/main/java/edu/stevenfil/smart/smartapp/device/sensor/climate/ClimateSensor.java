package edu.stevenfil.smart.smartapp.device.sensor.climate;

import edu.stevenfil.smart.smartapp.device.sensor.Sensor;
import java.util.Optional;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class ClimateSensor extends Sensor {

  private static final float INIT_TEMPERATURE = Float.NaN;
  private static final float INIT_HUMIDITY = Float.NaN;
  private float[] humidityRecords = new float[10];
  private float[] temperatureRecords = new float[10];
  private float humidity;
  private float temperature;

  public ClimateSensor() {
    super();
  }

  public ClimateSensor(float humidity, float temperature) {
    this();
    this.humidity = humidity;
    this.temperature = temperature;
  }

  private static float[] add(float humidity, float[] records) {
    if (records == null || records.length == 0) {
      return records;
    }
    if (records.length == 1) {
      records[0] = humidity;
      return records;
    }
    for (int i = 1; i <= records.length - 1; i++) {
      records[i - 1] = records[i];
    }
    records[records.length - 1] = humidity;
    return records;
  }

  private static String trend(float[] records) {
    if (recordTrend(records) == 1) {
      return "up";
    }
    if (recordTrend(records) == -1) {
      return "down";
    }
    return "stable";
  }

  private static int recordTrend(float[] records) {
    if (records == null || records.length == 0) {
      return 0;
    }
    if (records.length == 1) {
      return 0;
    }
    var difference = records[records.length - 1] - records[records.length - 2];
    if (difference > 0) {
      return 1;
    }
    if (difference < 0) {
      return -1;
    }
    return 0;
  }

  public Optional<Float> humidity() {
    if (Float.isNaN(humidity)) {
      return Optional.empty();
    }
    return Optional.of(humidity);
  }

  public Optional<Float> temperature() {
    if (Float.isNaN(temperature)) {
      return Optional.empty();
    }
    return Optional.of(temperature);
  }

  /**
   * Sets the humidity. Will NOT update the records of humidity values. Use {@link #update(float, float)}
   * instead.
   *
   * @param humidity the humidity value of the sensor
   */
  public void setHumidity(float humidity) {
    this.humidity = humidity;
  }

  /**
   * Sets the temperature. Will NOT update the records of temperature values. Use {@link #update(float, float)}
   * instead.
   *
   * @param temperature the temperature value of the sensor
   */
  public void setTemperature(float temperature) {
    this.temperature = temperature;
  }

  public void update(float humidity, float temperature) {
    this.humidity = humidity;
    this.temperature = temperature;
    this.humidityRecords = add(humidity, humidityRecords);
    this.temperatureRecords = add(temperature, temperatureRecords);
    update();
  }

  public String trendTemperature() {
    return trend(temperatureRecords);
  }

  public String trendHumidity() {
    return trend(humidityRecords);
  }


}
