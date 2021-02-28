package edu.kpi.testcourse.storage.namespace;

import java.util.Hashtable;

/**
 * Entity to separate logical parts in storage.
 */
public class NamespaceImpl implements Namespace {

  private final String name;
  private final Hashtable<Integer, Segment> hashtable;

  public NamespaceImpl(String name) {
    this.hashtable = new Hashtable<>();
    this.name = name;
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
    boolean alreadyPresent = false;

    if (segment == null) {
      segment = new Segment();
      hashtable.put(hashParts.getHigh(), segment);
    } else {
      alreadyPresent = segment.get(hashParts.getLow()) != null;
    }

    segment.set(hashParts.getLow(), value);
    return alreadyPresent;
  }

  @Override
  public boolean delete(String key) {
    HashParts hashParts = new HashParts(key);

    Segment segment = hashtable.get(hashParts.getHigh());
    if (segment == null) {
      return false;
    }

    return segment.delete(hashParts.getLow());
  }
}
