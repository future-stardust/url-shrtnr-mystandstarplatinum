package edu.kpi.testcourse.auth;

import static org.assertj.core.api.Assertions.assertThat;

import edu.kpi.testcourse.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class AuthImplTest {

  @AfterEach
  void deleteDummiesFromAuthStorage() {
    Main.getAuthStorage().delete("vasya");
    Main.getAuthStorage().delete("dude");
  }

  @Test
  void testCreateUser() {
    AuthImpl auth = new AuthImpl();

    AuthStatus creationStatus = auth.createUser("vasya", "pupkin");
    assertThat(creationStatus).isEqualTo(AuthStatus.Ok);
  }

  @Test
  void testCreateUser_whenExists() {
    AuthImpl auth = new AuthImpl();

    AuthStatus creationStatus = auth.createUser("vasya", "pupkin");

    creationStatus = auth.createUser("vasya", "pupkin");
    assertThat(creationStatus).isEqualTo(AuthStatus.ExistingUser);
  }

  @Test
  void testLoginUser() {
    AuthImpl auth = new AuthImpl();

    var creationStatus = auth.createUser("vasya", "pupkin");

    AuthStatus loginStatus = auth.loginUser("vasya", "pupkin");
    assertThat(loginStatus).isEqualTo(AuthStatus.Ok);
  }

  @Test
  void testLoginUser_whenNotRegistered() {
    AuthImpl auth = new AuthImpl();

    var creationStatus = auth.createUser("dude", "1337");

    AuthStatus loginStatus = auth.loginUser("vasya", "pupkin");
    assertThat(loginStatus).isNotEqualTo(AuthStatus.Ok);
  }

  @Test
  void testIsUserExists() {
    AuthImpl auth = new AuthImpl();

    var creationStatus = auth.createUser("vasya", "pupkin");

    boolean result = auth.isUserExists("vasya");
    assertThat(result).isEqualTo(true);
  }

  @Test
  void testIsUserExists_whenItIsNot() {
    AuthImpl auth = new AuthImpl();

    var creationStatus = auth.createUser("vasya", "pupkin");

    boolean result = auth.isUserExists("dude");
    assertThat(result).isEqualTo(false);
  }

  @Test
  void testCheckCredentials() {
    AuthImpl auth = new AuthImpl();

    var credentialsStatus = auth.checkCredentials("vasya", "pupkin");
    assertThat(credentialsStatus).isEqualTo(AuthStatus.Ok);
  }

  @Test
  void testCheckCredentials_whenNoUsername() {
    AuthImpl auth = new AuthImpl();

    var credentialsStatus = auth.checkCredentials(null, "pupkin");
    assertThat(credentialsStatus).isEqualTo(AuthStatus.InvalidUsername);

    credentialsStatus = auth.checkCredentials("", "pupkin");
    assertThat(credentialsStatus).isEqualTo(AuthStatus.InvalidUsername);
  }

  @Test
  void testCheckCredentials_whenNoPassword() {
    AuthImpl auth = new AuthImpl();

    var credentialsStatus = auth.checkCredentials("vasya", null);
    assertThat(credentialsStatus).isEqualTo(AuthStatus.InvalidPassword);

    credentialsStatus = auth.checkCredentials("vasya", "");
    assertThat(credentialsStatus).isEqualTo(AuthStatus.InvalidPassword);
  }

  @Test
  void testCheckCredentials_whenEmptyOrNull() {
    AuthImpl auth = new AuthImpl();

    var credentialsStatus = auth.checkCredentials("", null);
    assertThat(credentialsStatus).isEqualTo(AuthStatus.InvalidCredentials);

    credentialsStatus = auth.checkCredentials(null, "");
    assertThat(credentialsStatus).isEqualTo(AuthStatus.InvalidCredentials);

    credentialsStatus = auth.checkCredentials(null, null);
    assertThat(credentialsStatus).isEqualTo(AuthStatus.InvalidCredentials);

    credentialsStatus = auth.checkCredentials("", "");
    assertThat(credentialsStatus).isEqualTo(AuthStatus.InvalidCredentials);
  }
}
