package edu.kpi.testcourse;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MainTest {
  @Test
  void testGetStorageReturnsSameObject() {
    var storage1 = Main.getStorage();

    var storage2 = Main.getStorage();

    assertThat(storage1).isSameAs(storage2);
  }

  @Test
  void testGetNamespaceReturnsSameObject() {
    var ns1 = Main.getDefaultStorage();

    var ns2 = Main.getDefaultStorage();

    assertThat(ns1).isSameAs(ns2);
  }

  @Test
  void testGetAuthReturnsSameObject() {
    var auth1 = Main.getAuth();

    var auth2 = Main.getAuth();

    assertThat(auth1).isSameAs(auth2);
  }
}
