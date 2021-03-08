package edu.kpi.testcourse.storage;

import edu.kpi.testcourse.storage.dummy.DummyWorkloadScheduler;
import edu.kpi.testcourse.storage.namespace.Namespace;
import edu.kpi.testcourse.storage.namespace.NamespaceBackgroundSave;
import edu.kpi.testcourse.storage.namespace.NamespaceImpl;
import io.micronaut.context.ApplicationContext;
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
  private final ApplicationContext appContext = ApplicationContext.run();

  /**
   * Create empty storage.
   */
  public StorageImpl() {
    logger.info("[Thread {}] Initializing storage", Thread.currentThread().getId());
    namespaces = new ArrayList<>();
    postCreate();
    logger.info("[Thread {}] Storage initialized", Thread.currentThread().getId());
  }

  /**
   * Create storage from namespaces which already exist on disk.
   *
   * @param namespaces to create storage from.
   */
  StorageImpl(ArrayList<NamespaceImpl> namespaces) {
    logger.info("[Thread {}] Initializing storage", Thread.currentThread().getId());
    this.namespaces = namespaces;
    postCreate();
    logger.info("[Thread {}] Storage initialized", Thread.currentThread().getId());
  }

  /**
   * Steps that should be done after storage creation.
   * For now:
   * - schedule background save for created namespaces
   * - schedule dummy workload
   */
  private void postCreate() {
    StorageConfig config = appContext.getBean(StorageConfig.class);

    TaskScheduler scheduler = appContext.getBean(TaskScheduler.class);
    for (NamespaceImpl namespace : namespaces) {
      NamespaceBackgroundSave job = new NamespaceBackgroundSave(namespace);
      scheduler.scheduleWithFixedDelay(
          Duration.ofSeconds(config.getDataSyncPeriodSeconds()),
          Duration.ofSeconds(config.getDataSyncPeriodSeconds()),
          job
      );
    }

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
