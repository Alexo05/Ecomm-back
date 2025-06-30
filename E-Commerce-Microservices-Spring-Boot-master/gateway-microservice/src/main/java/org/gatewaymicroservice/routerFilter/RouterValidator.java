package org.gatewaymicroservice.routerFilter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/auth/login", "/auth/register", "/eureka", "/auth/confirm-account", "/auth/recuperer-mot-de-passe"
            , "/auth/changer-mot-de-passe","/users/","/auth/reset-password","/auth/change-password"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));


}
