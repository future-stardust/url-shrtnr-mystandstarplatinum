package edu.kpi.testcourse.storage;

import io.micronaut.context.annotation.Factory;
import java.util.concurrent.locks.ReentrantLock;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to init storage.
 */
@Factory
public class StorageFactory {
  private static final Logger logger = LoggerFactory.getLogger(StorageFactory.class);
  private static final ReentrantLock initLock = new ReentrantLock();
  private static Storage storage = null;

  @Singleton
  Storage storage() {
    if (storage == null) {
      boolean locked = initLock.tryLock();
      if (locked) {  // we need to init storage in this thread
        try {
          storage = new StorageImpl();
        } finally {
          initLock.unlock();
        }

      } else {  // another thread is already initializing storage
        logger.info("[Thread {}] waiting for storage init", Thread.currentThread().getId());
        initLock.lock(); // here we wait until another thread will release lock
        initLock.unlock(); // and simply release it because work is already done
      }
    }
    return storage;
  }
}
