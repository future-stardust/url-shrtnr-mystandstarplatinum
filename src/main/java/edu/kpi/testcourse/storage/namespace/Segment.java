package edu.kpi.testcourse.storage.namespace;

import edu.kpi.testcourse.storage.LockedSerializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Segment holds part of Namespace data.
 */
public class Segment implements LockedSerializable {

  private final Hashtable<Integer, Container> hashTable;
  private transient Lock lock;

  Segment() {
    this.hashTable = new Hashtable<>();
    this.lock = new ReentrantLock();
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

    this.lock();
    boolean result = hashTable.put(key, container) != null;
    this.unlock();

    return result;
  }

  /**
   * Delete key.
   *
   * @param key to delete
   * @return true if key was deleted, false if it did not exist
   */
  public boolean delete(int key) {
    this.lock();
    boolean result = hashTable.remove(key) != null;
    this.unlock();

    return result;
  }

  @Override
  public void lock() {
    this.lock.lock();
  }

  @Override
  public void unlock() {
    this.lock.unlock();
  }

  /**
   * We need to re-initialize lock after loading segment from disk.
   */
  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    this.lock = new ReentrantLock();
  }
}
