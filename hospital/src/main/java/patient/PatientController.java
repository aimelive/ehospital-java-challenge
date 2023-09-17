package patient;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.HttpResponse;
import utils.ResponseEntity;

@WebServlet("/patients")
public class PatientController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpResponse.getResponse(resp, new ResponseEntity<>(
                    HttpServletResponse.SC_OK,
                    "Patients retrieved successfully",
                    PatientService.findAll()));
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(resp, new ResponseEntity<>(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong, try again.",
                    null));
        }

    }

}
