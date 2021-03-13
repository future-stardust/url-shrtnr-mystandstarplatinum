package edu.kpi.testcourse.auth;

/**
 * Authentication service.
 */
public interface Auth {

  /**
   * Creates new user.
   *
   * @param username user`s email
   * @param password user`s password
   * @return auth service status
   */
  AuthStatus createUser(String username, String password);

  /**
   * Validates user login.
   *
   * @param username user`s email
   * @param password user`s password
   * @return auth service status
   */
  AuthStatus loginUser(String username, String password);
}
