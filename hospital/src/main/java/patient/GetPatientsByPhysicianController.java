package patient;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import physician.Physician;
import utils.Helpers;
import utils.HttpResponse;
import utils.ResponseEntity;

@WebServlet("/patients/findByPhysician")
public class GetPatientsByPhysicianController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Physician autPhysician = Helpers.getAuthPhysician(req);
            Patient[] results = PatientService.getPatientsByPhysician(autPhysician);
            HttpResponse.getResponse(res,
                    new ResponseEntity<Patient[]>(200, "Patient retrieved successfully", results));

        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null));
        }
    }

}
