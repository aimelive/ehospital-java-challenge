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
import utils.ResponseEntity;

@WebServlet("/medicines/findByPatient")
public class GetPatientMedicineController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Patient authPatient = Helpers.getAuthPatient(req);

            Medicine[] retrievedMedicines = PatientService.getMedicinesByPatient(authPatient);

            ResponseEntity<Medicine[]> result = new ResponseEntity<Medicine[]>(200,
                    "Medicines by patient " + authPatient.getUsername() + " retrieved successfully!",
                    retrievedMedicines);

            HttpResponse.getResponse(res, result);
        } catch (Exception e) {
            HttpResponse.getResponse(res, new ResponseEntity<>(400, e.getMessage(), null));
        }
    }

}
