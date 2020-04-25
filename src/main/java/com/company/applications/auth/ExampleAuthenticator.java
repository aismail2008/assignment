package com.company.applications.auth;

import com.company.applications.api.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.util.Sets;

import java.util.*;

public class ExampleAuthenticator implements Authenticator<BasicCredentials, User> {
    /**
     * Valid users with mapping user -> roles
     */
    private static final Map<String, Set<String>> VALID_USERS;

    static {
        final Map<String, Set<String>> validUsers = new HashMap<>();
        validUsers.put("guest", Collections.emptySet());
        validUsers.put("good-guy", Collections.singleton("BASIC_GUY"));
        validUsers.put("chief-wizard", Sets.of("ADMIN", "BASIC_GUY"));
        VALID_USERS = Collections.unmodifiableMap(validUsers);
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        // TODO could be replace with real DB credential checks like this line
        //EmployeeDB.getEmployees().stream().filter(p -> p.getFirstName() == credentials.getUsername());
        if (VALID_USERS.containsKey(credentials.getUsername()) && "secret".equals(credentials.getPassword())) {
            return Optional.of(new User(credentials.getUsername(), VALID_USERS.get(credentials.getUsername())));
        }
        return Optional.empty();
    }
}