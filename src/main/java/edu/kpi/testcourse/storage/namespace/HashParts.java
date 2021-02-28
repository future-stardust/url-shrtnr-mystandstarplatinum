package edu.kpi.testcourse.storage.namespace;

/**
 * Class that encapsulates logic of getting segment and key inside segment.
 * low is 16 least significant bits and high is 16 most significant bits
 */
public class HashParts {

  private final int low;
  private final int high;

  /**
   * Splits hash of string to most and least significant bits.
   *
   * @param key string, self-descriptive.
   */
  HashParts(String key) {
    int hash = key.hashCode();
    low = hash & LOW_MASK;
    high = hash & HIGH_MASK;
  }

  public int getHigh() {
    return high;
  }

  public int getLow() {
    return low;
  }

  private static final int HIGH_MASK = 0xffff0000;
  private static final int LOW_MASK = 0x0000ffff;
}
