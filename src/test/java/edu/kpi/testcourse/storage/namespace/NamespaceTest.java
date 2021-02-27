package edu.kpi.testcourse.storage.namespace;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class NamespaceTest {

  @Test
  void testSetNonExistingKey() {
    var ns = new NamespaceImpl("default");

    var result = ns.set("iDoNotExist", new byte[10]);

    assertThat(result).isFalse();
  }

  @Test
  void testSetExistingKey() {
    var ns = new NamespaceImpl("default");
    ns.set("iExist", new byte[10]);

    var result = ns.set("iExist", new byte[20]);

    assertThat(result).isTrue();
  }

  @Test
  void testGetNonExistingKey() {
    var ns = new NamespaceImpl("default");

    var result = ns.get("iDoNotExist");

    assertThat(result).isNull();
  }

  @Test
  void testGetExistingKey() {
    var ns = new NamespaceImpl("default");
    var data = new byte[10];
    ns.set("iExist", data);

    var result = ns.get("iExist");

    assertThat(result).isEqualTo(data);
  }

  @Test
  void testDeleteNonExistingKey() {
    var ns = new NamespaceImpl("default");

    var result = ns.delete("iDoNotExist");

    assertThat(result).isFalse();
  }

  @Test
  void testDeleteExistingKey() {
    var ns = new NamespaceImpl("default");
    var data = new byte[10];
    ns.set("iExist", data);

    var result = ns.delete("iExist");
    var result2 = ns.get("iExist");

    assertThat(result).isTrue();
    assertThat(result2).isNull();
  }

  @Test
  void testFails() {
    assertThat(true).isFalse();
  }
}
