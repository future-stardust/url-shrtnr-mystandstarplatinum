package edu.kpi.testcourse.logic.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class UrlEntityTest {

  @Test
  void shouldCreateUrlEntity_withDefaultAlias() {
    String url = "http://google.com";
    // UUIDv4 RegEx Pattern without '-' symbol
    String uuidPattern = "([a-f0-9]{8}([a-f0-9]{4}){4}[a-f0-9]{8})";

    var urlEntity = new UrlEntity(url);

    assertThat(
      Pattern.matches(uuidPattern, urlEntity.getAlias())
    ).isSameAs(true);
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
  void shouldThrowError_whenNotInvalidUrl() {
    String url = "http:/google.com";
    String alias = "alias";

    assertThrows(IllegalArgumentException.class, () -> new UrlEntity(url, alias));
  }
}
