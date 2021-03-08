package edu.kpi.testcourse.storage;

import edu.kpi.testcourse.storage.dummy.DummyWorkloadScheduler;
import edu.kpi.testcourse.storage.namespace.Namespace;
import edu.kpi.testcourse.storage.namespace.NamespaceBackgroundSave;
import edu.kpi.testcourse.storage.namespace.NamespaceImpl;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanContext;
import io.micronaut.scheduling.TaskScheduler;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Key-value storage implementation.
 */
public class StorageImpl implements Storage {
  private final ArrayList<NamespaceImpl> namespaces;
  private static final Logger logger = LoggerFactory.getLogger(StorageImpl.class);
  private final Lock nsLock = new ReentrantLock();
  private final ApplicationContext appContext;

  /**
   * Get injected config and create storage.
   */
  public StorageImpl() {
    logger.info("[Thread {}] Initializing storage", Thread.currentThread().getId());

    this.appContext = ApplicationContext.run();
    BeanContext beanContext = BeanContext.run();

    StorageConfig config = appContext.getBean(StorageConfig.class);
    FileSystemLayer fsLayer = beanContext.getBean(FileSystemLayer.class);

    fsLayer.mkdir(config.getDataDir());
    fsLayer.mkdir(config.getBackupDir());

    namespaces = new ArrayList<>();
    logger.info("[Thread {}] Storage initialized", Thread.currentThread().getId());

    DummyWorkloadScheduler.schedule();
  }

  @Override
  public Namespace getOrCreateNamespace(String name) {
    nsLock.lock();
    for (Namespace namespace : namespaces) {
      if (namespace.getName().equals(name)) {
        nsLock.unlock();
        return namespace;
      }
    }
    NamespaceImpl namespace = new NamespaceImpl(name);
    namespaces.add(namespace);
    nsLock.unlock();

    StorageConfig config = appContext.getBean(StorageConfig.class);
    TaskScheduler scheduler = appContext.getBean(TaskScheduler.class);

    NamespaceBackgroundSave job = new NamespaceBackgroundSave(namespace);
    scheduler.scheduleWithFixedDelay(
        Duration.ofSeconds(config.getDataSyncPeriodSeconds()),
        Duration.ofSeconds(config.getDataSyncPeriodSeconds()),
        job
    );

    logger.info("[Thread {}] namespace {} created",
        Thread.currentThread().getId(),
        namespace.getName()
    );
    return namespace;
  }
}
