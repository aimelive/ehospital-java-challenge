package consultation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import patient.Patient;
import patient.PatientService;
import physician.Physician;
import utils.Helpers;
import utils.HttpResponse;
import utils.RequestBodyUtil;
import utils.ResponseEntity;

@WebServlet("/consultation")
public class ConsultationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Physician autPhysician = Helpers.getAuthPhysician(req);

            String requestBody = RequestBodyUtil.getBody(req);

            Consultation consultation = new Consultation(
                    RequestBodyUtil.getValueFromJsonKey(requestBody,
                            "disease"),
                    RequestBodyUtil.getValueFromJsonKey(requestBody,
                            "description"),
                    autPhysician);

            String patientUsername = RequestBodyUtil.getValueFromJsonKey(requestBody, "patientUsername");
            if (patientUsername == null) {
                throw new IllegalArgumentException("Patient username is required!");
            }
            Patient patient = PatientService.findByUsername(patientUsername);
            if (patient == null) {
                throw new IllegalArgumentException("Patient not found!");
            }

            Patient updatedPatient = PatientService.assignConsultation(patientUsername, consultation);

            ResponseEntity<Patient> result = new ResponseEntity<Patient>(
                    200,
                    "Consultation added successfully!",
                    updatedPatient);
            HttpResponse.getResponse(res, result);
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Patient authPatient = Helpers.getAuthPatient(req);

            Consultation consultation = PatientService.getConsultationByPatient(authPatient);
            ResponseEntity<Consultation> result = new ResponseEntity<Consultation>(
                    200, "Consultation retrieved successfully!", consultation);
            HttpResponse.getResponse(res, result);

        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(400, e.getMessage(), null));
        }
    }

}
