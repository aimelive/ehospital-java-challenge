package physician;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import patient.Patient;
import patient.PatientService;
import utils.Helpers;
import utils.HttpResponse;
import utils.ResponseEntity;

import javax.servlet.http.HttpServlet;

@WebServlet("/physicians/select")
public class SelectPhysicianController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Patient authPatient = Helpers.getAuthPatient(req);

            String email = req.getParameter("email");

            if (email == null) {
                throw new IllegalArgumentException("Physician email must be provided.");
            }

            Physician physician = PhysicianService.findByEmail(email);

            if (physician == null) {
                throw new IllegalArgumentException("Physician with email: " + email + ", not found in our system.");
            }

            Patient patient = PatientService.selectPhysician(authPatient.getUsername(), physician);

            ResponseEntity<Patient> result = new ResponseEntity<Patient>(200, "Physician selected successfully!",
                    patient);

            HttpResponse.getResponse(res, result);
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(400, e.getMessage(), null));
        }
    }
}
