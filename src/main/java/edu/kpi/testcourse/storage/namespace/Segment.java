package edu.kpi.testcourse.storage.namespace;

import java.util.Hashtable;

/**
 * Segment holds part of Namespace data.
 */
public class Segment {

  private final Hashtable<Integer, Container> hashTable;

  Segment() {
    this.hashTable = new Hashtable<>();
  }

  /**
   * Get value by key.
   *
   * @param key to search
   *
   * @return value or null if key is missing
   */
  public byte[] get(int key) {
    try {
      return hashTable.get(key).getData();
    } catch (NullPointerException e) {
      return null;
    }
  }

  /**
   * Set key to value.
   *
   * @param key self-descriptive
   *
   * @param value self-descriptive
   *
   * @return true if key is overwritten, false otherwise
   */
  public boolean set(int key, byte[] value) {
    Container container = new Container(value);

    return hashTable.put(key, container) != null;
  }

  public boolean delete(int key) {
    return hashTable.remove(key) != null;
  }
}
