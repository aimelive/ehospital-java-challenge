package pharmacist;

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

@WebServlet("/pharmacists/select")
public class SelectPharmacistController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Patient authPatient = Helpers.getAuthPatient(req);

            String phoneNumber = req.getParameter("phoneNumber");

            if (phoneNumber == null) {
                throw new IllegalArgumentException("Pharmacist phone number must be provided.");
            }

            Pharmacist pharmacist = PharmacistService.findByPhoneNumber(phoneNumber);

            if (pharmacist == null) {
                throw new IllegalArgumentException(
                        "Pharmacist with phone number: " + phoneNumber + ", not found in our system.");
            }

            Patient patient = PatientService.selectPharmacist(authPatient.getUsername(), pharmacist);

            ResponseEntity<Patient> result = new ResponseEntity<Patient>(200, "Pharmacist selected successfully!",
                    patient);
            HttpResponse.getResponse(res, result);

        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(400, e.getMessage(), null));
        }
    }
}
