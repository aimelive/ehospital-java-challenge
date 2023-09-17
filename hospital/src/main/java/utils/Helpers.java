package utils;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import patient.Patient;
import patient.PatientService;
import pharmacist.Pharmacist;
import pharmacist.PharmacistService;
import physician.Physician;
import physician.PhysicianService;

public class Helpers {
    public static final Key getSecretKey() {
        String secretKey = "amata-yaraye-amata-yaraye-amata-yaraye";
        return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    private static final String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isEmpty()) {
            throw new IllegalArgumentException("Authentication token is missing!");
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authentication token!");
        }

        return authHeader.substring(7);
    }

    private static final Claims parseJwtToken(String jwtToken) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        return jwtParser.parseClaimsJws(jwtToken).getBody();
    }

    public static final Pharmacist getAuthPharmacist(HttpServletRequest req) {
        Claims claims = parseJwtToken(extractToken(req));
        String pharmacistPhoneNumber = claims.get("phoneNumber", String.class);
        if (pharmacistPhoneNumber == null) {
            throw new IllegalArgumentException("Pharmacist phone number is required! not logged in.");
        }
        Pharmacist pharmacist = PharmacistService.findByPhoneNumber(pharmacistPhoneNumber);

        if (pharmacist == null) {
            throw new IllegalArgumentException("Logged in pharmacist is not found!");
        }
        return pharmacist;
    }

    public static final Patient getAuthPatient(HttpServletRequest req) {
        Claims claims = parseJwtToken(extractToken(req));
        String patientUsername = claims.get("username", String.class);
        if (patientUsername == null) {
            throw new IllegalArgumentException("Patient username is required! not logged in.");
        }
        Patient patient = PatientService.findByUsername(patientUsername);

        if (patient == null) {
            throw new IllegalArgumentException("Logged in patient is not found!");
        }
        return patient;
    }

    public static final Physician getAuthPhysician(HttpServletRequest req) {
        Claims claims = parseJwtToken(extractToken(req));
        String physicianEmail = claims.get("email", String.class);
        if (physicianEmail == null) {
            throw new IllegalArgumentException("Physician email is required! not logged in.");
        }
        Physician physician = PhysicianService.findByEmail(physicianEmail);

        if (physician == null) {
            throw new IllegalArgumentException("Logged in physician is not found!");
        }
        return physician;
    }
}
