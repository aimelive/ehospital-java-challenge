package patient;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pharmacist.Pharmacist;
import utils.Helpers;
import utils.HttpResponse;
import utils.ResponseEntity;

@WebServlet("/patients/findByPharmacist")
public class GetPatientsByPharmacistController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Pharmacist authPharmacist = Helpers.getAuthPharmacist(req);

            Patient[] retrievedPatients = PatientService.getPatientsByPharmacist(authPharmacist);

            ResponseEntity<Patient[]> result = new ResponseEntity<Patient[]>(
                    200,
                    "Patients by " + authPharmacist.getName() + " retrieved successfully!",
                    retrievedPatients);

            HttpResponse.getResponse(res, result);

        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(400, e.getMessage(), null));
        }
    }
}
