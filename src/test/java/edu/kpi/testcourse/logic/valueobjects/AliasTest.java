package edu.kpi.testcourse.logic.valueobjects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AliasTest {

  @Test
  void shouldCreateValidValObj_whenAliasIsAlphanumeric() {
    String alias = "alias124";

    var aliasValObj = new AliasValueObject(alias);

    assertThat(aliasValObj.isValid()).isTrue();
    assertThat(aliasValObj.get()).isSameAs(alias);
  }

  @ParameterizedTest
  @ValueSource(strings = {"Z-@9mqSAH?Q=bAhp", "aX}~&U+%_4uzN}w]", "bGu2~;mnhWx%N3f"})
  void shouldCreateInvalidValObj_whenAliasIsNotAlphanumeric() {
    String alias = UUID.randomUUID().toString();

    var aliasValObj = new AliasValueObject(alias);

    assertThat(aliasValObj.isValid()).isFalse();
    assertThat(aliasValObj.get()).isSameAs(alias);
  }
}
