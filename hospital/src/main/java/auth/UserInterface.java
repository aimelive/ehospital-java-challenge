package auth;

import javax.naming.AuthenticationException;

import utils.ResponseEntity;

public interface UserInterface {
    public abstract ResponseEntity<User> register() throws Exception;

    public abstract ResponseEntity<String> login(String email, String password) throws AuthenticationException;
}