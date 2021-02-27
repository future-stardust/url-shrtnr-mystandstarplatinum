package edu.kpi.testcourse.storage;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


class StorageImplTest {

  @Test
  void checkGetNamespace() {
    StorageImpl storage = new StorageImpl();

    var ns = storage.getOrCreateNamespace("ns1");

    assertThat(ns.getName()).isEqualTo("ns1");
  }

  @Test
  void checkSameNameReturnsSameNamespaceObject() {
    StorageImpl storage = new StorageImpl();
    var nsOne = storage.getOrCreateNamespace("ns1");

    var nsOneDuplicate = storage.getOrCreateNamespace("ns1");

    assertThat(nsOneDuplicate).isSameAs(nsOne);
  }

  @Test
  void checkOtherNameReturnsDifferentNamespaceObject() {
    StorageImpl storage = new StorageImpl();
    var nsOne = storage.getOrCreateNamespace("ns1");

    var nsTwo = storage.getOrCreateNamespace("ns2");

    assertThat(nsTwo).isNotSameAs(nsOne);
  }

}
