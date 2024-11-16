package edu.stevenfil.smart.smartapp.device.sensor;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class Sensor {

  private String description;

  private float batteryStatus;

  private String friendlyName;

  private OnUpdateAction action;

  private String location;

  private Instant lastUpdate;

  public Sensor() {
    this("", Float.NaN, "", new DefaultUpdateAction());
  }

  public Sensor(String description, float batteryStatus, String friendlyName,
      OnUpdateAction action) {
    this.description = description;
    this.batteryStatus = batteryStatus;
    this.friendlyName = friendlyName;
    this.action = action;
  }

  public void updateDescription(String description) {
    this.description = description;
    update();
  }

  /**
   * Calls {@link OnUpdateAction#execute()} on the registered action.
   */
  protected void update() {
    lastUpdate = Instant.now();
    action.execute();
  }

  public String getDescription() {
    return description;
  }

  public Optional<Float> getBatteryStatus() {
    if (Float.isNaN(batteryStatus)) {
      return Optional.empty();
    }
    return Optional.of(batteryStatus);
  }

  public void setBatteryStatus(float batteryStatus) {
    this.batteryStatus = batteryStatus;
    update();
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
    update();
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
    update();
  }

  public Optional<Instant> getLastUpdate() {
    return Optional.ofNullable(lastUpdate);
  }

  public void setOnUpdateAction(OnUpdateAction action) {
    this.action = Objects.requireNonNull(action);
  }

  public void updateBatteryStatus(float batteryStatus) {
    this.batteryStatus = batteryStatus;
    update();
  }
}
