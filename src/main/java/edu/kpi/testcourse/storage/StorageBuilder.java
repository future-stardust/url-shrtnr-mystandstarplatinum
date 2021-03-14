package edu.kpi.testcourse.storage;

import edu.kpi.testcourse.storage.namespace.NamespaceImpl;
import edu.kpi.testcourse.storage.namespace.Segment;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanContext;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Build storage from existing data or create empty.
 */
public class StorageBuilder {
  private static final Logger logger = LoggerFactory.getLogger(StorageBuilder.class);
  private final BeanContext beanContext = BeanContext.run();
  private final ApplicationContext appContext = ApplicationContext.run();

  /**
   * Build storage from existing data or create empty.
   *
   * @return created storage
   */
  public Storage build() {
    final long start = System.currentTimeMillis();
    logger.info("Loading storage");
    StorageConfig config = appContext.getBean(StorageConfig.class);
    FileSystemLayer fsl = beanContext.getBean(FileSystemLayer.class);

    String dataDir = config.getDataDir();
    String backupDir = config.getBackupDir();

    if (!fsl.exists(backupDir)) {
      logger.info("{} does not exist, creating", backupDir);
      fsl.mkdir(backupDir);
    }

    if (!fsl.isDirectory(backupDir)) {
      logger.error("{} is not a directory, invalid configuration", dataDir);
      System.exit(2);
    }

    if (!fsl.exists(dataDir)) {
      logger.info("{} does not exist, creating empty storage", dataDir);
      fsl.mkdir(dataDir);
      return new StorageImpl();
    }

    if (!fsl.isDirectory(dataDir)) {
      logger.error("{} is not a directory, invalid configuration", dataDir);
      System.exit(2);
    }

    String[] namespaceNames = fsl.list(dataDir);
    ArrayList<NamespaceImpl> namespaces = new ArrayList<>(namespaceNames.length);
    logger.info("loading {} namespace from disk", namespaceNames.length);
    for (String name : namespaceNames) {
      namespaces.add(buildNs(name));
    }

    logger.info("Storage loaded in {} ms", System.currentTimeMillis() - start);
    return new StorageImpl(namespaces);
  }

  /**
   * Build namespace from data on the disk.
   *
   * @param name of the namespace.
   * @return built namespace.
   */
  private NamespaceImpl buildNs(String name) {
    final long start = System.currentTimeMillis();
    StorageConfig config = appContext.getBean(StorageConfig.class);
    FileSystemLayer fsl = beanContext.getBean(FileSystemLayer.class);

    String dataDir = config.getDataDir();
    String nsDataDir = Paths.get(dataDir, name).toString();

    if (!fsl.isDirectory(nsDataDir)) {
      logger.error("{} is not directory, broken data", nsDataDir);
      System.exit(3);
    }

    String[] segmentNames = fsl.list(nsDataDir);
    Hashtable<Integer, Segment> segments = new Hashtable<>();
    logger.info("Loading {} segments for {}", segmentNames.length, name);
    for (String segmentName : segmentNames) {
      String segmentIdRaw = segmentName.split("\\.")[0];
      int segmentId;
      try {
        segmentId = Integer.parseInt(segmentIdRaw);
      } catch (NumberFormatException e) {
        logger.error("Namespace {}: {} is invalid number, potentially broken data, skipping",
            name,
            segmentIdRaw
        );
        continue;
      }
      Segment segment;
      try {
        segment = fsl.readToObject(nsDataDir, segmentName);
      } catch (IOException e) {
        logger.error("Namespace {}: IOException reading {}: {}",
            name,
            segmentName,
            e.getMessage()
        );
        continue;
      } catch (ClassNotFoundException e) {
        logger.error("Namespace {}: ClassNotFoundException reading {}: {}",
            name,
            segmentName,
            e.getMessage()
        );
        continue;
      }
      segments.put(segmentId, segment);
    }

    logger.info("Namespace {} loaded in {}", name, System.currentTimeMillis() - start);
    return new NamespaceImpl(segments, name);
  }
}
