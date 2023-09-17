package patient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.naming.AuthenticationException;

import auth.Gender;
import auth.Role;
import auth.User;
import consultation.Consultation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import medicine.Medicine;
import pharmacist.Pharmacist;
import physician.Physician;
import utils.Helpers;
import utils.PasswordUtil;
import utils.ResponseEntity;

@Getter
@Setter
public class Patient extends User {
    @NonNull
    private String username;
    private Medicine[] medicines;
    private Consultation consultation;
    private Physician selectedPhysician;
    private Pharmacist selectedPharmacist;

    public Patient(String name, Integer age, Gender gender, Role role, String password, String username) {
        super(name, age, gender, role, password);
        this.username = username;
    }

    @Override
    public ResponseEntity<String> login(String username, String password) throws AuthenticationException {
        Patient existPatient = PatientService.findByUsername(username);

        if (existPatient == null) {
            throw new AuthenticationException("Incorrect username or password. Please try again.");
        }

        if (!password.equals(existPatient.getPassword())) {
            throw new AuthenticationException("Incorrect username or password. Please try again.");
        }

        Claims claims = Jwts.claims().setSubject(existPatient.username);
        claims.put("role", existPatient.role.name());
        claims.put("username", existPatient.username);

        Instant currentTimeInstant = Instant.now();

        String jwtToken = Jwts.builder()
                .claim("role", existPatient.role.name())
                .claim("username", existPatient.username)
                .setSubject(existPatient.username)
                .setId(existPatient.id)
                .setIssuedAt(Date.from(currentTimeInstant))
                .setExpiration(Date.from(currentTimeInstant.plus(24L, ChronoUnit.HOURS)))
                .signWith(Helpers.getSecretKey())
                .compact();

        return new ResponseEntity<String>(200, "Patient " + existPatient.name + " logged in Successfully!",
                jwtToken);

    }

    @Override
    public ResponseEntity<User> register() throws Exception {
        if (!PasswordUtil.isValidPassword(getPassword(), 4, 6)) {
            throw new Exception("Patient's password must be 4-6 characters. Please try again.");
        }

        PatientService.create(this);
        return new ResponseEntity<User>(201, "Patient registered successfully!",
                PatientService.findByUsername(getUsername()));
    }

    public void assignNewMedicine(Medicine newMedicine) {
        if (this.medicines == null) {
            this.medicines = new Medicine[] { newMedicine };
        } else {
            Medicine[] newMedicines = new Medicine[this.medicines.length + 1];
            System.arraycopy(this.medicines, 0, newMedicines, 0, this.medicines.length);
            newMedicines[newMedicines.length - 1] = newMedicine;
            this.medicines = newMedicines;
        }
    }

}
