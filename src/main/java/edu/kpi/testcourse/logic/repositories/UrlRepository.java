package edu.kpi.testcourse.logic.repositories;

import edu.kpi.testcourse.logic.entities.UrlEntity;

/**
 * The Data Access Object for the UrlEntity.
 */
public interface UrlRepository {

  /**
   * Saves the UrlEntity in some storage.
   *
   * @param email of the user that creates UrlEntity
   *
   * @param urlEntity is the entity to be saved
   *
   * @return true if the save operation was successful and no entity exists with the same alias
   */
  boolean save(String email, UrlEntity urlEntity);

  /**
   * Finds the one URL Entity instance by alias.
   *
   * @param alias by which the search is made
   *
   * @return UrlEntity instance or null if there is no entity with given alias
   */
  UrlEntity read(String alias);

  /**
   * Searches for all user URL Entities by email.
   *
   * @param email by which the search is made
   *
   * @return List of URL Entities
   */
  UrlEntity[] readMany(String email);

  /**
   * Deletes URL Entity.
   *
   * @param email of the user that owns this UrlEntity
   *
   * @param alias of the entity that should be delete
   *
   * @return true if we found data for the delete and successfully delete it
   */
  boolean delete(String email, String alias);
}
