package edu.kpi.testcourse.storage.namespace;

import java.io.Serializable;

/**
 * Wraps byte[] to be available in generics.
 */
public class Container implements Serializable {

  private final byte[] data;

  Container(byte[] data) {
    this.data = data;
  }

  public byte[] getData() {
    return data;
  }
}
