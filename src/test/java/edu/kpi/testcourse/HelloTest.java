package edu.kpi.testcourse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloTest {

  @Test
  void simpleAlwaysGreenTest() {
    assertThat(1).isEqualTo(1);
  }

  @Test
  void simpleAlwaysGreenTest2() {
    assertThat(2).isEqualTo(2);
  }

}
