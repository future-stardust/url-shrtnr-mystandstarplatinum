package edu.kpi.testcourse.storage;

import java.io.Serializable;

/**
 * Serializable + simplified Lock.
 * All mutations should be made after locking the object.
 */
public interface LockedSerializable extends Serializable {

  /**
   * lock object mutations.
   */
  void lock();

  /**
   * unlock object mutations.
   */
  void unlock();
}
