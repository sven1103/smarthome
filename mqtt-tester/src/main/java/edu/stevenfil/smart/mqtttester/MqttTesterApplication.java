package edu.stevenfil.smart.mqtttester;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MqttTesterApplication {

  public static void main(String[] args) throws MqttException {
    SpringApplication.run(MqttTesterApplication.class, args);
    String broker = "tcp://smartberry.local:1883";
    String clientId = "smart_app_tester2";

    try (MqttClient client = new MqttClient(broker, clientId)) {
      MqttConnectOptions options = new MqttConnectOptions();
      client.connect(options);

      client.setCallback(new MqttCallback() {

        @Override
        public void connectionLost(Throwable throwable) {
          System.out.println("Connection lost: " + throwable.getMessage());
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
          System.out.println("Topic: " + s);
          System.out.println("Message: " + new String(mqttMessage.getPayload()));
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
          System.out.println("Delivery complete: " + iMqttDeliveryToken.toString());
        }
      });

      var topic = "zigbee2mqtt/living_room/humidity_temperature";

      client.subscribe(topic, 0);

      while (true) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }



  }

}
