package physician;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.naming.AuthenticationException;

import auth.Gender;
import auth.Role;
import auth.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import utils.Helpers;
import utils.PasswordUtil;
import utils.ResponseEntity;

@Getter
@Setter
public class Physician extends User {
    @NonNull
    private String email;

    public Physician(String name, Integer age, Gender gender, Role role, String password, String email) {
        super(name, age, gender, role, password);
        this.email = email;
    }

    @Override
    public ResponseEntity<String> login(String email, String password) throws AuthenticationException {
        Physician existPhysician = PhysicianService.findByEmail(email);

        if (existPhysician == null) {
            throw new AuthenticationException("Incorrect email or password. Please try again.");
        }

        if (!password.equals(existPhysician.getPassword())) {
            throw new AuthenticationException("Incorrect email or password. Please try again.");
        }

        Claims claims = Jwts.claims().setSubject(existPhysician.email);
        claims.put("role", existPhysician.role.name());
        claims.put("email", existPhysician.email);

        Instant currentTimeInstant = Instant.now();

        String jwtToken = Jwts.builder()
                .claim("role", existPhysician.role.name())
                .claim("email", existPhysician.email)
                .setSubject(existPhysician.email)
                .setId(existPhysician.id)
                .setIssuedAt(Date.from(currentTimeInstant))
                .setExpiration(Date.from(currentTimeInstant.plus(24L, ChronoUnit.HOURS)))
                .signWith(Helpers.getSecretKey())
                .compact();

        return new ResponseEntity<String>(200, "Physician  " + existPhysician.name + "logged in Successfully!",
                jwtToken);

    }

    @Override
    public ResponseEntity<User> register() throws Exception {
        if (!PasswordUtil.isValidPassword(getPassword(), 7, 8)) {
            throw new Exception("Physician's password must be 7-8 characters. Please try again.");
        }

        PhysicianService.create(this);

        return new ResponseEntity<User>(201, "Physician registered successfully!",
                PhysicianService.findByEmail(getEmail()));
    }
}
