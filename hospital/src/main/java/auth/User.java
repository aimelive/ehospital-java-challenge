package auth;

import java.util.UUID;

import javax.naming.AuthenticationException;

// import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import utils.ResponseEntity;

@Getter
@Setter
public class User implements UserInterface {
    public String id;
    @NonNull
    public String name;
    @NonNull
    public Gender gender;
    @NonNull
    protected Role role;
    private Integer age;
    // @Expose(serialize = false)
    private String password;

    public User(String name, Integer age, Gender gender, Role role, String password) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.password = password;
    }

    @Override
    public ResponseEntity<User> register() throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public ResponseEntity<String> login(String identifier, String password) throws AuthenticationException {
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
}
