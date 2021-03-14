package edu.kpi.testcourse.auth;

/**
 * Authentication service statuses.
 */
public enum AuthStatus {
  Ok,
  ExistingUser,
  InvalidUsername,
  InvalidPassword,
  InvalidCredentials
}
