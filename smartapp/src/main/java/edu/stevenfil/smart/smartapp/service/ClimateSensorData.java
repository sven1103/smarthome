package edu.stevenfil.smart.smartapp.service;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public record ClimateSensorData(String friendlyName, float temperature, float humidity,
                                float battery, String trendTemperature, String trendHumidity, String location) {

}