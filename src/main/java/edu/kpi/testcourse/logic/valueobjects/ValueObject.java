package edu.kpi.testcourse.logic.valueobjects;

/**
 * Objects that contain business logic constraints for some values.
 *
 * @param <T> an object class that is constrained
 */
public interface ValueObject<T> {

  /**
   * Checks all conditions set by business logic.
   *
   * @return true if value is valid, else false
   */
  boolean isValid();

  /**
   * Get value.
   *
   * @return value
   */
  T get();
}
