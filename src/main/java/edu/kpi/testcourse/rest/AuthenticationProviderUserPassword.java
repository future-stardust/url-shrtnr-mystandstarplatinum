package edu.kpi.testcourse.rest;

import edu.kpi.testcourse.Main;
import edu.kpi.testcourse.auth.Auth;
import edu.kpi.testcourse.auth.AuthStatus;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.ArrayList;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;

/**
 * Micronaut authentication bean that contains authorization logic: ensures that a user is
 * registered in the system and password is right.
 */
@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      @Nullable HttpRequest<?> httpRequest,
      AuthenticationRequest<?, ?> authenticationRequest
  ) {
    return Flowable.create(emitter -> {
      Auth auth = Main.getAuth();
      String username = (String) authenticationRequest.getIdentity();
      String password = (String) authenticationRequest.getSecret();
      AuthStatus authStatus = auth.loginUser(username, password);
      if (authStatus == AuthStatus.Ok) {
        emitter
          .onNext(new UserDetails((String) authenticationRequest.getIdentity(), new ArrayList<>()));
        emitter.onComplete();
      } else {
        emitter.onError(new AuthenticationException(new AuthenticationFailed()));
      }
    }, BackpressureStrategy.ERROR);
  }
}
