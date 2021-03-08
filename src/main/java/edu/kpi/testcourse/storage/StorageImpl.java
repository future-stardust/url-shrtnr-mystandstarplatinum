package edu.kpi.testcourse.storage;

import edu.kpi.testcourse.storage.namespace.Namespace;
import edu.kpi.testcourse.storage.namespace.NamespaceImpl;
import io.micronaut.context.ApplicationContext;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Key-value storage implementation.
 */
public class StorageImpl implements Storage {
  private final ArrayList<Namespace> namespaces;
  private static final Logger logger = LoggerFactory.getLogger(StorageImpl.class);

  /**
   * Get injected config and create storage.
   */
  public StorageImpl() {
    logger.info("[Thread {}] Initializing storage", Thread.currentThread().getId());

    ApplicationContext appContext = ApplicationContext.run();
    StorageConfig config = appContext.getBean(StorageConfig.class);
    logger.info("[Thread {}] dataDir   {}", Thread.currentThread().getId(), config.getDataDir());
    logger.info("[Thread {}] backupDir {}", Thread.currentThread().getId(), config.getBackupDir());

    namespaces = new ArrayList<>();
    logger.info("[Thread {}] Storage initialized", Thread.currentThread().getId());
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
    logger.info("[Thread {}] namespace {} created",
        Thread.currentThread().getId(),
        namespace.getName()
    );
    return namespace;
  }
}
