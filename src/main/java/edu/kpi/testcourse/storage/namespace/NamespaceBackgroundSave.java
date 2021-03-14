package edu.kpi.testcourse.storage.namespace;

import edu.kpi.testcourse.storage.FileSystemLayer;
import io.micronaut.context.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Periodic task to flush modified segments to disk.
 */
public class NamespaceBackgroundSave implements Runnable {
  private final NamespaceImpl ns;
  private static final Logger logger = LoggerFactory.getLogger(NamespaceBackgroundSave.class);
  BeanContext beanContext = BeanContext.run();

  public NamespaceBackgroundSave(NamespaceImpl ns) {
    this.ns = ns;
  }

  @Override
  public void run() {
    final long start = System.currentTimeMillis();
    logger.info("background save started for {}", ns.getName());

    FileSystemLayer fsLayer = beanContext.getBean(FileSystemLayer.class);
    String basePath = ns.getDataPath();

    if (fsLayer.exists(basePath) && !fsLayer.isDirectory(basePath)) {
      logger.error("{} dir is not a directory, cannot save data of {}", basePath, ns.getName());
      return;
    }
    if (!fsLayer.exists(basePath)) {
      logger.info("Creating {} for {} namespace", basePath, ns.getName());
      fsLayer.mkdir(ns.getDataPath());
    }

    Integer[] segments = this.ns.popModified();
    logger.info("NS {}: saving {} segments to disk", ns.getName(), segments.length);

    for (Integer segmentId : segments) {
      String name = String.format("%d.ser", segmentId);
      Segment segment = ns.getSegment(segmentId);

      if (segment == null) {
        logger.error("NS {}: Attempt to save non-existing segment {}", ns.getName(), segmentId);
        return;
      }

      fsLayer.safeWriteSerializable(basePath, name, segment);
    }

    logger.info("background save for {} took {} ms",
        ns.getName(),
        System.currentTimeMillis() - start);
  }
}