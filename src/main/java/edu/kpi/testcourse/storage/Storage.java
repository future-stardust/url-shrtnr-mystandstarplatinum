package edu.kpi.testcourse.storage;

import edu.kpi.testcourse.storage.namespace.Namespace;

/**
 * Key-value storage.
 */
public interface Storage {

  /**
   * Creates namespace. Many calls to method return same Namespace object.
   *
   * @param name name of namespace to create
   *
   * @return namespace from list
   */
  Namespace getOrCreateNamespace(String name);
}
