package edu.kpi.testcourse.auth;

/**
 * Authentication service implementation.
 */
public class AuthImpl implements Auth {

  @Override
  public AuthStatus createUser(String username, String password) {
    return AuthStatus.InvalidCredentials;
  }

  @Override
  public AuthStatus loginUser(String username, String password) {
    return AuthStatus.InvalidCredentials;
  }
}
