package edu.kpi.testcourse.storage.namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Periodic task to flush modified segments to disk.
 */
public class NamespaceBackgroundSave implements Runnable {
  private final NamespaceImpl ns;
  private static final Logger logger = LoggerFactory.getLogger(NamespaceBackgroundSave.class);

  public NamespaceBackgroundSave(NamespaceImpl ns) {
    this.ns = ns;
  }

  @Override
  public void run() {
    long start = System.currentTimeMillis();
    logger.info("background save started for {}", ns.getName());
    try {
      Thread.sleep(150);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    logger.info("background save for {} took {} ms",
        ns.getName(),
        System.currentTimeMillis() - start);
  }
}
