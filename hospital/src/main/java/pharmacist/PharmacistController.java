package pharmacist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.HttpResponse;
import utils.ResponseEntity;

@WebServlet("/pharmacists")
public class PharmacistController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpResponse.getResponse(resp, new ResponseEntity<>(
                    HttpServletResponse.SC_OK,
                    "Pharmacists retrieved successfully",
                    PharmacistService.findAll()));
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(resp, new ResponseEntity<>(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong, try again.",
                    null));
        }

    }

}
