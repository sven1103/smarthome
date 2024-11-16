package edu.stevenfil.smart.smartapp.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class Device {

  @JsonProperty("category")
  String category;

  @JsonProperty("type")
  String type;

  @JsonProperty("friendly_name")
  String friendlyName;

  @JsonProperty("location")
  String location;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Device device = (Device) o;
    return Objects.equals(category, device.category) && Objects.equals(type,
        device.type) && Objects.equals(friendlyName, device.friendlyName);
  }

  public String getCategory() {
    return category;
  }

  public String getLocation() {
    return location;
  }

  public String getType() {
    return type;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, type, friendlyName);
  }

  @Override
  public String toString() {
    return "Device{" +
        "category='" + category + '\'' +
        ", type='" + type + '\'' +
        ", friendlyName='" + friendlyName + '\'' +
        '}';
  }
}
