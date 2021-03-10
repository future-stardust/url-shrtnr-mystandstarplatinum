package edu.kpi.testcourse.logic.valueobjects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UrlTest {

  @Test
  void shouldCreateValidValObj_whenCorrectUrl() {
    String url = "http://google.com";

    var urlValObj = new UrlValueObject(url);

    assertThat(urlValObj.isValid()).isTrue();
    assertThat(urlValObj.get()).isSameAs(url);
  }

  @ParameterizedTest
  @ValueSource(strings = {"dfsdfsdfsdf", "htlp://google.com", "https://not url"})
  void shouldCreateInvalidValObj_whenIncorrectUrl(String url) {
    var urlValObj = new UrlValueObject(url);

    assertThat(urlValObj.isValid()).isFalse();
    assertThat(urlValObj.get()).isSameAs(url);
  }

}
