package edu.kpi.testcourse.storage.namespace;

/**
 * Class that encapsulates logic of getting segment and key inside segment.
 */
public class HashParts {

  private final int low;
  private final int high;

  HashParts(int low, int high) {
    this.low = low;
    this.high = high;
  }

  public int getHigh() {
    return high;
  }

  public int getLow() {
    return low;
  }

  private static final int HIGH_MASK = 0xffff0000;
  private static final int LOW_MASK = 0x0000ffff;

  /**
   * Splits hash of string to most and least significant bits.
   *
   * @param key string, self-descriptive.
   *
   * @return HashParts where low is 16 least significant bits and high is 16 most significant bits
   */
  static HashParts from(String key) {
    int hash = key.hashCode();
    int low = hash & LOW_MASK;
    int high = hash & HIGH_MASK;

    return new HashParts(low, high);
  }
}
