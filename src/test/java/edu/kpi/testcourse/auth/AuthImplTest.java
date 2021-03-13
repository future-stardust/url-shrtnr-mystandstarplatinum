package edu.kpi.testcourse.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AuthImplTest {

  @Test
  void testCreateUser() {
    AuthImpl auth = new AuthImpl();

    AuthStatus creationStatus = auth.createUser("vasya", "pupkin");
    assertThat(creationStatus).isEqualTo(AuthStatus.Ok);
  }

  @Test
  void testLoginUser() {
    AuthImpl auth = new AuthImpl();

    var creationStatus = auth.createUser("vasya", "pupkin");

    AuthStatus loginStatus = auth.loginUser("vasya", "pupkin");
    assertThat(loginStatus).isEqualTo(AuthStatus.Ok);
  }
}
