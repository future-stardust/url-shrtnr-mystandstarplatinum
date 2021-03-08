package edu.kpi.testcourse.storage.namespace;

import edu.kpi.testcourse.storage.FileSystemLayer;
import edu.kpi.testcourse.storage.StorageConfig;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanContext;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Entity to separate logical parts in storage.
 */
public class NamespaceImpl implements Namespace {

  private final String name;
  private final Hashtable<Integer, Segment> hashtable;
  private final Set<Integer> modifiedSegments;
  private final Lock housekeepingLock;
  private final ApplicationContext appContext = ApplicationContext.run();

  /**
   * Creates namespace with given name.
   *
   * @param name name of the namespace, please use common rules/restrictions of naming variables.
   */
  public NamespaceImpl(String name) {
    this.hashtable = new Hashtable<>();
    this.name = name;
    this.modifiedSegments = new HashSet<>();
    this.housekeepingLock = new ReentrantLock();
  }

  /**
   * Create namespace from data read from the disk.
   *
   * @param segments self-descriptive
   * @param name self-descriptive
   */
  public NamespaceImpl(Hashtable<Integer, Segment> segments, String name) {
    this.hashtable = segments;
    this.name = name;
    this.modifiedSegments = new HashSet<>();
    this.housekeepingLock = new ReentrantLock();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public byte[] get(String key) {
    HashParts hashParts = new HashParts(key);
    Segment segment;

    segment = hashtable.get(hashParts.getHigh());
    if (segment == null) {
      return null;
    }

    return segment.get(hashParts.getLow());
  }

  @Override
  public boolean set(String key, byte[] value) {
    HashParts hashParts = new HashParts(key);

    Segment segment = hashtable.get(hashParts.getHigh());

    if (segment == null) {
      segment = new Segment();
      hashtable.put(hashParts.getHigh(), segment);
    }

    housekeepingLock.lock();
    modifiedSegments.add(hashParts.getHigh());
    housekeepingLock.unlock();

    return segment.set(hashParts.getLow(), value);
  }

  @Override
  public boolean delete(String key) {
    HashParts hashParts = new HashParts(key);

    Segment segment = hashtable.get(hashParts.getHigh());
    if (segment == null) {
      return false;
    }

    boolean deleted = segment.delete(hashParts.getLow());
    if (deleted) {
      housekeepingLock.lock();
      modifiedSegments.add(hashParts.getHigh());
      housekeepingLock.unlock();
    }
    return deleted;
  }

  /**
   * Should be used to obtain modified segments of this namespace and save them to the disk.
   * Internal container of modified segments is cleared upon returning it's contents.
   *
   * @return array containing modified segments.
   */
  Integer[] popModified() {
    housekeepingLock.lock();
    Object[] temp = this.modifiedSegments.toArray();
    Integer[] modifiedSegments = Arrays.copyOf(temp, temp.length, Integer[].class);
    this.modifiedSegments.clear();
    housekeepingLock.unlock();
    return modifiedSegments;
  }

  public String getDataPath() {
    StorageConfig config = appContext.getBean(StorageConfig.class);
    return Paths.get(config.getDataDir(), name).toString();
  }

  /**
   * Get segment (for housekeeping purposes).
   *
   * @param number of segment.
   *
   * @return Segment or null.
   */
  Segment getSegment(Integer number) {
    return hashtable.get(number);
  }
}
