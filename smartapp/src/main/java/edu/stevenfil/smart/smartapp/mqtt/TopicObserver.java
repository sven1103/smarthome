package edu.stevenfil.smart.smartapp.mqtt;

/**
 * <b><interface short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public interface TopicObserver {

  String interestedTopic();

  void consume(String topic);

}
