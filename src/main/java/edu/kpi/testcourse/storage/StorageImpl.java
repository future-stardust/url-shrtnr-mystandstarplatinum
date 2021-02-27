package edu.kpi.testcourse.storage;

import edu.kpi.testcourse.storage.namespace.Namespace;
import edu.kpi.testcourse.storage.namespace.NamespaceImpl;
import java.util.ArrayList;

/**
 * Key-value storage implementation.
 */
public class StorageImpl implements Storage {

  private final ArrayList<NamespaceImpl> namespaces;

  public StorageImpl() {
    namespaces = new ArrayList<>();
    namespaces.add(new NamespaceImpl("default"));
  }

  @Override
  public Namespace getOrCreateNamespace(String name) {
    for (Namespace namespace : namespaces) {
      if (namespace.getName().equals(name)) {
        return namespace;
      }
    }
    NamespaceImpl namespace = new NamespaceImpl(name);
    namespaces.add(namespace);
    return namespace;
  }
}
