package medicine;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import patient.Patient;
import patient.PatientService;
import utils.Helpers;
import utils.HttpResponse;
import utils.RequestBodyUtil;
import utils.ResponseEntity;

@WebServlet("/pharmacists/provide-medicine")
public class ProvideMedicineController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Helpers.getAuthPharmacist(req);

            String requestBody = RequestBodyUtil.getBody(req);

            Patient patient = PatientService.findByUsername(
                    RequestBodyUtil.getValueFromJsonKey(requestBody, "patientUsername"));

            if (patient == null) {
                throw new IllegalArgumentException("Patient not found, please try again.");
            }
            if (patient.getSelectedPharmacist() == null) {
                HttpResponse.getResponse(res, new ResponseEntity<>(
                        401,
                        "Not authorized, Patient has not selected you as pharmacist",
                        null));
                return;
            }
            if (patient.getConsultation() == null) {
                HttpResponse.getResponse(res, new ResponseEntity<>(
                        401,
                        "Not authorized, Patient has not consulted a physician yet.",
                        null));
                return;
            }

            Medicine medicine = MedicineService.findOne(RequestBodyUtil.getValueFromJsonKey(requestBody, "name"));
            if (medicine == null) {
                throw new IllegalArgumentException("Medicine not found! please try again.");
            }

            Patient updatedPatient = PatientService.addMedicine(patient.getUsername(), medicine);

            ResponseEntity<Patient> result = new ResponseEntity<Patient>(
                    200,
                    "Patient has given new medicine successfully",
                    updatedPatient);

            HttpResponse.getResponse(res, result);

        } catch (Exception e) {
            HttpResponse.getResponse(res, new ResponseEntity<>(400, e.getMessage(), null));
        }
    }

}
