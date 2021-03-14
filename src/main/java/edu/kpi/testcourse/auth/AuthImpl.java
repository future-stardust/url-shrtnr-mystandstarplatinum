package edu.kpi.testcourse.auth;

import edu.kpi.testcourse.Main;
import edu.kpi.testcourse.storage.namespace.Namespace;
import java.util.Arrays;

/**
 * Authentication service implementation.
 */
public class AuthImpl implements Auth {
  private final Namespace authStorage = Main.getAuthStorage();

  @Override
  public AuthStatus createUser(String username, String password) {
    AuthStatus status;
    if ((status = checkCredentials(username, password)) != AuthStatus.Ok) {
      return status;
    }
    if ((status = registerUser(username, password)) != AuthStatus.Ok) {
      return status;
    }
    return status;
  }

  @Override
  public AuthStatus loginUser(String username, String password) {
    AuthStatus status;
    if ((status = checkCredentials(username, password)) != AuthStatus.Ok) {
      return status;
    }
    if ((status = validateUser(username, password)) != AuthStatus.Ok) {
      return status;
    }
    return status;
  }

  private boolean checkStringNotEmpty(String string) {
    return string != null && !string.isEmpty();
  }

  /**
   * Checks username contents.
   *
   * @param username username
   * @return InvalidUsername or Ok
   */
  private AuthStatus checkUsername(String username) {
    if (!checkStringNotEmpty(username)) {
      return AuthStatus.InvalidUsername;
    }
    return AuthStatus.Ok;
  }

  /**
   * Checks password contents.
   *
   * @param password password
   * @return InvalidPassword or Ok
   */
  private AuthStatus checkPassword(String password) {
    if (!checkStringNotEmpty(password)) {
      return AuthStatus.InvalidPassword;
    }
    return AuthStatus.Ok;
  }

  /**
   * Checks credentials contents.
   *
   * @param username username
   * @param password password
   * @return InvalidCredentials, InvalidUsername, InvalidPassword or Ok
   */
  public AuthStatus checkCredentials(String username, String password) {
    final AuthStatus usernameStatus = checkUsername(username);
    final AuthStatus passwordStatus = checkPassword(password);

    if ((usernameStatus != AuthStatus.Ok) && (passwordStatus != AuthStatus.Ok)) {
      return AuthStatus.InvalidCredentials;
    }
    if (usernameStatus != AuthStatus.Ok) {
      return AuthStatus.InvalidUsername;
    }
    if (passwordStatus != AuthStatus.Ok) {
      return AuthStatus.InvalidPassword;
    }
    return AuthStatus.Ok;
  }

  /**
   * Check that user exists.
   *
   * @param username username
   * @return true if user exists
   */
  public boolean isUserExists(String username) {
    try {
      return authStorage.get(username) != null;
    } catch (NullPointerException e) {
      return false;
    }
  }

  /**
   * Register user with given name and password.
   *
   * @param username username
   * @param password password
   * @return AuthStatus value
   */
  private AuthStatus registerUser(String username, String password) {
    if (isUserExists(username)) {
      return AuthStatus.ExistingUser;
    }
    byte[] notYetHashedPassword = password.getBytes();
    authStorage.set(username, notYetHashedPassword);
    return AuthStatus.Ok;
  }

  /**
   * Check user`s credentials correctness.
   *
   * @param username username
   * @param password password
   * @return AuthStatus value
   */
  private AuthStatus validateUser(String username, String password) {
    if (!isUserExists(username)) {
      return AuthStatus.InvalidUsername;
    }
    byte[] notYetHashedPassword = password.getBytes();
    byte[] retrievedNotYetHashedPassword = authStorage.get(username);
    if (!Arrays.equals(retrievedNotYetHashedPassword, notYetHashedPassword)) {
      return AuthStatus.InvalidPassword;
    }
    return AuthStatus.Ok;
  }
}
