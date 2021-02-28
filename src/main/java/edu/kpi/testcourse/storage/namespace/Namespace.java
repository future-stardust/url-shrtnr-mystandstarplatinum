package edu.kpi.testcourse.storage.namespace;

/**
 * Basic operations for manipulating data in key-value storage.
 */
public interface Namespace {

  public String getName();

  /**
   * Get value by key.
   *
   * @param key to search
   *
   * @return array of bytes for given key or null if key is not found
   *
   * @throws NullPointerException if `key` is null
   */
  public byte[] get(String key);

  /**
   * Set key to value.
   *
   * @param key   to insert
   *
   * @param value to insert
   *
   * @return true if key was overridden, false if key is new for the storage
   *
   * @throws NullPointerException if either `key` or 'value' is `null`
   */
  public boolean set(String key, byte[] value);

  /**
   * Delete value by key.
   *
   * @param key to delete
   *
   * @return true if key was deleted, false if key was not found
   *
   * @throws NullPointerException if `key` is null
   */
  public boolean delete(String key);
}
