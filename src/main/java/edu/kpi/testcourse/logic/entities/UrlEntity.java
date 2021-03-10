package edu.kpi.testcourse.logic.entities;

import edu.kpi.testcourse.logic.valueobjects.AliasValueObject;
import edu.kpi.testcourse.logic.valueobjects.UrlValueObject;
import java.util.UUID;

/**
 * Entity that describes the URL.
 */
public class UrlEntity {

  private final AliasValueObject alias;
  private final UrlValueObject url;

  /**
   * Creates the entity instance with custom alias.
   *
   * @param url is the long destination URL
   *
   * @param alias is a reduction for the long URL
   *
   * @throws IllegalArgumentException if alias or url is malformed
   */
  public UrlEntity(String url, String alias) throws IllegalArgumentException {
    var aliasValObj = new AliasValueObject(alias);
    var urlValObj = new UrlValueObject(url);

    if (!urlValObj.isValid() || !aliasValObj.isValid()) {
      throw new IllegalArgumentException();
    }

    this.url = urlValObj;
    this.alias = aliasValObj;
  }

  /**
   * Creates the entity with random alphanumeric alias.
   *
   * @param url is the long destination URL
   *
   * @throws IllegalArgumentException if url is malformed
   */
  public UrlEntity(String url) throws IllegalArgumentException {
    this(url, Uuid64.hexToBase64(UUID.randomUUID().toString()));
  }

  /**
   * Getter for the alias.
   *
   * @return alias
   */
  public String getAlias() {
    return alias.get();
  }

  /**
   * Getter for long destination URL.
   *
   * @return long URL
   */
  public String getUrl() {
    return url.get();
  }
}
