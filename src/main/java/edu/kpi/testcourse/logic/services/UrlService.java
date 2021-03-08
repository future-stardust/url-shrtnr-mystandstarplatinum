package edu.kpi.testcourse.logic.services;

import edu.kpi.testcourse.logic.entities.UrlEntity;
import java.util.List;

/**
 * Service for the URL related business logic.
 * Contains URL Repository.
 */
public interface UrlService {

  /**
   * Creates and saves the URL Entity with a random alias.
   *
   * @param email of the user that creates URL Entity
   *
   * @param url for which creates a URL Entity
   *
   * @return the URL Entity
   */
  UrlEntity create(String email, String url);

  /**
   * Creates and saves the URL Entity with a custom alias.
   *
   * @param email of the user that creates URL Entity
   *
   * @param url for which creates a URL Entity
   *
   * @param alias for the URL Entity
   *
   * @return the URL Entity
   */
  UrlEntity create(String email, String url, String alias);

  /**
   * Fetch one URL Entity by the alias.
   *
   * @param alias by which the search is made
   *
   * @return the URL Entity
   */
  UrlEntity getByAlias(String alias);

  /**
   * Fetch URL Entities by the creator email.
   *
   * @param email of the creator
   *
   * @return URL Entities list
   */
  List<UrlEntity> getByEmail(String email);

  /**
   * Deletes URL Entity.
   *
   * @param email of the user that owns the URL Entity
   *
   * @param alias of the entity that should be delete
   *
   * @return true if the delete operation was successful
   */
  boolean delete(String email, String alias);
}
