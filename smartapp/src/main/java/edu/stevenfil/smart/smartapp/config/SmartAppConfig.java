package edu.stevenfil.smart.smartapp.config;

import static org.apache.logging.log4j.LogManager.getLogger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import edu.stevenfil.smart.smartapp.exceptions.StartupException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
@Component
public class SmartAppConfig {

  private static final Logger log = getLogger(SmartAppConfig.class);
  private static final String PROPERTY_DEVICES = "devices";
  private static final String PROPERTY_ROOT_TOPIC = "topic_root";
  private static final String PROPERTY_BROKER_URL = "broker_url";
  private static final String PROPERTY_CLIENT_ID = "client_id";
  private final List<Device> devices = new ArrayList<>();
  private MqttConfig mqttConfig;

  protected SmartAppConfig(List<Device> devices, MqttConfig mqttConfig) {
    this.devices.addAll(devices);
    this.mqttConfig = mqttConfig;
  }

  @Autowired
  public SmartAppConfig(@Value("${smarthome.config.path}") String configPath) {
    log.info("Loading config from '{}'", configPath);

    try {
      var configContent = readConfig(Paths.get(configPath));
      this.devices.addAll(configContent.devices);
      this.mqttConfig = configContent.mqttConfig;
    } catch (IOException e) {
      log.error(e);
      throw new StartupException("Loading config from file '%s' failed.".formatted(configPath));
    }
    log.info("Loaded config from '{}'", configPath);
    log.info("Loaded devices '{}'", devices);
    log.info("Loaded mqttConfig '{}'", mqttConfig);
  }

  public MqttConfig mqttConfig() {
    return mqttConfig;
  }

  public List<Device> devices() {
    return devices;
  }

  private static SmartAppConfig readConfig(Path configPath) throws IOException {
    var content = Files.readString(configPath);
    var mapper = setupMapper();
    var tree = mapper.readTree(content);
    var devices = loadDevices(mapper, tree, PROPERTY_DEVICES);
    var mqttConfig = loadMqttConfig(mapper, tree);
    return new SmartAppConfig(devices, mqttConfig);
  }

  private static ObjectMapper setupMapper() {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }

  private static MqttConfig loadMqttConfig(ObjectMapper mapper, JsonNode tree) {
    var rootTopic = loadRootTopic(mapper, tree, PROPERTY_ROOT_TOPIC);
    var brokerUrl = loadUrl(mapper, tree, PROPERTY_BROKER_URL);
    var clientId = loadClientId(mapper, tree, PROPERTY_CLIENT_ID);
    return new MqttConfig(brokerUrl, clientId, rootTopic);
  }

  private static String loadClientId(ObjectMapper mapper, JsonNode tree, String propertyClientId) {
    if (tree.has(propertyClientId)) {
      return tree.get(propertyClientId).asText("smarthome-app");
    } else {
      return "smarthome-app";
    }
  }

  private static String loadUrl(ObjectMapper mapper, JsonNode tree, String propertyBrokerUrl) {
    if (tree.has(propertyBrokerUrl)) {
      return tree.get(propertyBrokerUrl).asText();
    } else {
      throw new StartupException("Missing broker url property '%s'".formatted(propertyBrokerUrl));
    }
  }

  private static String loadRootTopic(ObjectMapper mapper, JsonNode tree,
      String propertyRootTopic) {
    if (tree.has(propertyRootTopic)) {
      return tree.get(propertyRootTopic).asText();
    } else {
      log.warn("Root topic '{}' not found. Using ''", propertyRootTopic);
      return "";
    }
  }

  private static List<Device> loadDevices(ObjectMapper mapper, JsonNode root, String propertyName) {
    try {
      if (root.has(propertyName)) {
        return mapper.convertValue(root.get(propertyName), new TypeReference<List<Device>>() {
        });
      } else {
        throw new StartupException(
            "The config was missing the property: '%s'".formatted(propertyName));
      }
    } catch (IllegalArgumentException e) {
      log.error(e);
      throw new StartupException("Error parsing config file '%s'".formatted(propertyName));
    }
  }

  public record MqttConfig(String brokerUrl, String clientId, String rootTopic) {

  }

}
