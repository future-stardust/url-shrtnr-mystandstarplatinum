package edu.kpi.testcourse.logic.valueobjects;

/**
 * Value Object that describes alias business logic constraints.
 */
public class AliasValueObject implements ValueObject<String> {
  private final String value;

  /**
   * Alias Value Object constructor.
   *
   * @param value is an alias
   */
  public AliasValueObject(String value) {
    this.value = value;
  }

  /**
   * Checks if a valid alias passed through the constructor.
   *
   * @return true if the alias is valid, else - false
   */
  @Override
  public boolean isValid() {
    return value != null && value.chars().allMatch(Character::isLetterOrDigit);
  }

  /**
   * Alias getter.
   *
   * @return alias
   */
  @Override
  public String get() {
    return value;
  }
}
