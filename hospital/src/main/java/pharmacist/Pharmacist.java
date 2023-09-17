package pharmacist;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.naming.AuthenticationException;

import auth.Gender;
import auth.Role;
import auth.User;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import utils.Helpers;
import utils.PasswordUtil;
import utils.ResponseEntity;

@Getter
@Setter
public class Pharmacist extends User {
    @NonNull
    private String phoneNumber;

    public Pharmacist(String name, Integer age, Gender gender, Role role, String password, String phoneNumber) {
        super(name, age, gender, role, password);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public ResponseEntity<String> login(String phoneNumber, String password) throws AuthenticationException {
        Pharmacist existPharmacist = PharmacistService.findByPhoneNumber(phoneNumber);

        if (existPharmacist == null) {
            throw new AuthenticationException("Incorrect phone number or password. Please try again.");
        }

        if (!password.equals(existPharmacist.getPassword())) {
            throw new AuthenticationException("Incorrect phone number or password. Please try again.");
        }

        // Claims claims = Jwts.claims().setSubject(existPharmacist.phoneNumber);
        // claims.put("role", existPharmacist.role.name());
        // claims.put("phoneNumber", existPharmacist.phoneNumber);

        Instant currentTimeInstant = Instant.now();

        String jwtToken = Jwts.builder()
                .claim("role", existPharmacist.role.name())
                .claim("phoneNumber", existPharmacist.phoneNumber)
                .setSubject(existPharmacist.phoneNumber)
                .setId(existPharmacist.id)
                .setIssuedAt(Date.from(currentTimeInstant))
                .setExpiration(Date.from(currentTimeInstant.plus(24L, ChronoUnit.HOURS)))
                .signWith(Helpers.getSecretKey())
                .compact();

        return new ResponseEntity<String>(200, "Pharmacist " + existPharmacist.name + " logged in Successfully!",
                jwtToken);

    }

    @Override
    public ResponseEntity<User> register() throws Exception {
        if (!PasswordUtil.isValidPassword(getPassword(), 9, 10)) {
            throw new Exception("Pharmacist's password must be 9-10 characters. Please try again.");
        }

        if (getPhoneNumber() == null) {
            throw new Exception("Pharmacist's phone number is required. Please try again.");
        }

        PharmacistService.create(this);

        return new ResponseEntity<User>(201, "Pharmacist registered successfully!",
                PharmacistService.findByPhoneNumber(getPhoneNumber()));
    }

}
