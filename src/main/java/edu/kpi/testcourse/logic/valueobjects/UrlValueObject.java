package edu.kpi.testcourse.logic.valueobjects;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Value Object that describes long URL business logic constraints.
 */
public class UrlValueObject implements ValueObject<String> {

  private final String value;

  /**
   * URL Value Object constructor.
   *
   * @param value is an long URL
   */
  public UrlValueObject(String value) {
    this.value = value;
  }

  /**
   * Checks if a valid URL passed through the constructor.
   *
   * @return true if the URL is valid, else - false
   */
  @Override
  public String get() {
    return value;
  }

  /**
   * URL getter.
   *
   * @return URL
   */
  @Override
  public boolean isValid() {
    try {
      new URL(value).toURI();
    } catch (MalformedURLException | URISyntaxException e) {
      return false;
    }
    return true;
  }
}
