package edu.kpi.testcourse.storage.namespace;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SegmentTest {

  @Test
  void testGetNonExistingKey() {
    var segment = new Segment();

    var result = segment.get(1);

    assertThat(result).isNull();
  }

  @Test
  void testGetExistingKey() {
    var segment = new Segment();
    var data = new byte[10];
    segment.set(1, data);

    var result = segment.get(1);

    assertThat(result).isNotNull().isEqualTo(data);
  }

  @Test
  void testSetNonExistingKey() {
    var segment = new Segment();

    var result = segment.set(1, new byte[10]);

    assertThat(result).isFalse();
  }

  @Test
  void testSetExistingKey() {
    var segment = new Segment();
    segment.set(1, new byte[10]);

    var result = segment.set(1, new byte[20]);

    assertThat(result).isTrue();
  }

  @Test
  void testDeleteNonExistingKey() {
    var segment = new Segment();

    var result = segment.delete(1);

    assertThat(result).isFalse();
  }

  @Test
  void testDeleteExistingKey() {
    var segment = new Segment();
    segment.set(1, new byte[10]);

    var result = segment.delete(1);
    var result2 = segment.get(1);

    assertThat(result).isTrue();
    assertThat(result2).isNull();
  }

}
