package edu.kpi.testcourse.storage.namespace;

/**
 * Wraps byte[] to be available in generics.
 */
public class Container {

  private final byte[] data;

  Container(byte[] data) {
    this.data = data;
  }

  public byte[] getData() {
    return data;
  }
}
