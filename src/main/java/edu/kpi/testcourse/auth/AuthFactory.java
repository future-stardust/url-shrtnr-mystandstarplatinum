package edu.kpi.testcourse.auth;

import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

/**
 * Factory to create Authentication service.
 */
@Factory
public class AuthFactory {
  private static Auth auth = null;

  /**
   * Creates and/or returns Authentication service singleton.
   *
   * @return Auth instance
   */
  @Singleton
  Auth auth() {
    if (auth == null) {
      auth = new AuthImpl();
    }
    return auth;
  }
}
