package edu.kpi.testcourse.storage.dummy;

import edu.kpi.testcourse.storage.Storage;
import edu.kpi.testcourse.storage.StorageConfig.TestingConfig.RandomFillConfig;
import edu.kpi.testcourse.storage.namespace.Namespace;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Insert to storage.
 */
class RandomFill extends DummyWorkload {
  ApplicationContext applicationContext = ApplicationContext.run();
  BeanContext beanContext = BeanContext.run();
  private static final Logger logger = LoggerFactory.getLogger(RandomFill.class);

  @Override
  public boolean shouldBeScheduled() {
    RandomFillConfig config = applicationContext.getBean(RandomFillConfig.class);
    return config.getEnabled();
  }

  @Override
  public int getPeriod() {
    RandomFillConfig config = applicationContext.getBean(RandomFillConfig.class);
    return config.getPeriodSeconds();
  }

  @Override
  public void run() {
    logger.info("Random fill tick start");
    RandomFillConfig config = applicationContext.getBean(RandomFillConfig.class);
    Storage st = beanContext.getBean(Storage.class);

    for (String name : config.getNamespaces()) {
      Namespace ns = st.getOrCreateNamespace(name);
      for (int i = 0; i < config.getInsertionCount(); i++) {
        String key = String.format("%d:%d", i, System.currentTimeMillis());
        byte[] value = new byte[10];
        ns.set(key, value);
      }
    }
    logger.info("Random fill tick end");
  }
}
