package edu.stevenfil.smart.smartapp.mqtt;

import edu.stevenfil.smart.smartapp.exceptions.SmarthomeException;

/**
 * <b><interface short description - 1 Line!></b>
 *
 * <p><More detailed description - When to use, what it solves, etc.></p>
 *
 * @since <version tag>
 */
public interface MessageParsingStrategy<T> {

  T parse(String message) throws SmarthomeException;

}
