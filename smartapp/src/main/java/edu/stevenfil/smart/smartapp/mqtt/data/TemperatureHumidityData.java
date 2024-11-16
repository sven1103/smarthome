package edu.stevenfil.smart.smartapp.mqtt.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <b><record short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public record TemperatureHumidityData(@JsonProperty("temperature") Float temperature,
                                      @JsonProperty("humidity") Float humidity,
                                      @JsonProperty("battery") Float batteryStatus,
                                      @JsonProperty("linkquality") int linkQuality) {

    }
