package edu.kpi.testcourse.logic.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UrlEntityTest {

  @Test
  void shouldCreateUrlEntity_withDefaultAlias() {
    String url = "http://google.com";

    var urlEntity = new UrlEntity(url);

    assertThat(urlEntity.getAlias()).isNotEmpty();
    assertThat(urlEntity.getUrl()).isSameAs(url);
  }

  @Test
  void shouldCreateUrlEntity_whenPassValidAlias() {
    String url = "http://google.com";
    String alias = "myAlias";

    var urlEntity = new UrlEntity(url, alias);

    assertThat(urlEntity.getUrl()).isSameAs(url);
    assertThat(urlEntity.getAlias()).isSameAs(alias);
  }

  @Test
  void shouldThrowError_whenNotAlphanumericAlias() {
    String url = "http://google.com";
    String alias = "invalid$22e512::@alias";

    assertThrows(IllegalArgumentException.class, () -> new UrlEntity(url, alias));
  }

  @Test
  void shouldThrowError_whenInvalidUrl() {
    String url = "httm:/google.com";
    String alias = "alias";

    assertThrows(IllegalArgumentException.class, () -> new UrlEntity(url, alias));
  }
}
