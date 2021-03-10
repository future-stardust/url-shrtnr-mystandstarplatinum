package edu.kpi.testcourse.logic.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

public class Uuid64Test {

  @Test
  void shouldConvertUUIDString() {
    String uuidStr = "eb55c9cc-1fc1-43da-9adb-d9c66bb259ad";

    String base64Str = Uuid64.hexToBase64(uuidStr);

    assertThat(base64Str).isEqualTo("61XJzB_BQ9qa29nGa7JZrQ");
  }

  @Test
  void shouldConvertBase64ToUUIDString() {
    String base64Str = "61XJzB_BQ9qa29nGa7JZrQ";

    String uuidStr = Uuid64.base64ToHex(base64Str);

    assertThat(uuidStr).isEqualTo("eb55c9cc-1fc1-43da-9adb-d9c66bb259ad");
  }

  @Test
  void shouldReconvert_whenRandomUUIDGiven() {
    String randomUuid = UUID.randomUUID().toString();

    String reconvertedUuid = Uuid64.base64ToHex(Uuid64.hexToBase64(randomUuid));

    assertThat(reconvertedUuid).isEqualTo(randomUuid);
  }

}
