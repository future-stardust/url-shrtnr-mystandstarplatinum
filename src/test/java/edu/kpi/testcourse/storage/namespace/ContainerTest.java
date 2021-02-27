package edu.kpi.testcourse.storage.namespace;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ContainerTest {

  @Test
  void testContainer() {
    var bytes = new byte[10];

    var data = new Container(bytes).getData();

    assertThat(data).isSameAs(bytes);
  }
}
