package edu.stevenfil.smart.smartapp.mqtt;

import edu.stevenfil.smart.smartapp.config.SmartAppConfig.MqttConfig;
import edu.stevenfil.smart.smartapp.exceptions.StartupException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <b>MQTT Client</b>
 * <p>
 * Simple MQTT client adapter, that registers {@link TopicObserver} to the actual underlying MQTT
 * message broker.
 *
 * @since 1.0.0
 */
@Component
public class SmartAppClient {

  private IMqttAsyncClient mqttClient;

  private MqttConfig mqttConfig;

  private static final Logger log = LoggerFactory.getLogger(SmartAppClient.class);

  @Autowired
  public SmartAppClient(MqttConfig config) {
    this.mqttConfig = Objects.requireNonNull(config);
    try {
      this.mqttClient = new MqttAsyncClient(mqttConfig.brokerUrl(), mqttConfig.clientId());
      this.mqttClient.connect();
      while(!mqttClient.isConnected()) {
        Thread.sleep(100);
      }
      log.info("Connected to MQTT broker");
    } catch (MqttException e) {
      log.error(e.getMessage(), e);
      throw new StartupException("Could not connect to message broker.");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> subscribe(TopicObserver observer) {
    try {
      mqttClient.subscribe(buildFullTopic(mqttConfig.rootTopic(), observer.interestedTopic()), 1, null,
          new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
              Arrays.stream(asyncActionToken.getTopics()).forEach(System.out::println);
              System.out.println("Subscribed to " + observer.interestedTopic());
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
              System.out.println("Failed to subscribe to " + observer.interestedTopic());
            }
          }, (topic, message) -> observer.consume(new String(message.getPayload(), StandardCharsets.UTF_8)));
      return CompletableFuture.completedFuture(null);
    } catch (MqttException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private String buildFullTopic(String root, String topic) {
    return root + "/" + topic;
  }

}
