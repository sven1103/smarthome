package edu.stevenfil.smart.mqtttester;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.SpringApplication;

/**
 * <b><class short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public class MqttAsyncTester {

  public static void main(String[] args) throws MqttException {

    SpringApplication.run(MqttTesterApplication.class, args);
    String broker = "tcp://smartberry.local:1883";
    String clientId = "smart_app_tester2";

    try (MqttAsyncClient client = new MqttAsyncClient(broker, clientId)) {
      MqttConnectOptions options = new MqttConnectOptions();

      client.setCallback(new MqttCallback() {

        @Override
        public void connectionLost(Throwable cause) {
          System.out.println("Connection lost");
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
          System.out.println("Received message: " + new String(message.getPayload()));
          System.out.println("Topic: " + topic);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
          System.out.println("Delivery complete");
        }
      });

      client.connect(options, new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
          try {
            MqttAsyncTester.subscribe(client, "zigbee2mqtt/living_room/humidity_temperature");
          } catch (MqttException e) {
            throw new RuntimeException(e);
          }
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
          System.out.println(exception.getMessage());
        }
      });

      while (true) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public static void subscribe(IMqttAsyncClient client, String topic) throws MqttException {
    client.subscribe(topic, 0,
        (topic1, message) -> System.out.println(new String(message.getPayload())));
  }
}
