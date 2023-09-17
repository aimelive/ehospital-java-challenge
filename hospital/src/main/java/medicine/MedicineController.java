package medicine;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Helpers;
import utils.HttpResponse;
import utils.JsonUtil;
import utils.ResponseEntity;

@WebServlet("/medicines")
public class MedicineController extends HttpServlet {
    // Adding new medicine to the system
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            Helpers.getAuthPharmacist(req);
            Medicine requestBodyMedicine = new JsonUtil().parseBodyJson(req, Medicine.class);
            ResponseEntity<Medicine> result = MedicineService.create(requestBodyMedicine);
            HttpResponse.getResponse(res, result);
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(400, e.getMessage(), null));
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Helpers.getAuthPharmacist(req);

            ResponseEntity<Medicine[]> result = new ResponseEntity<Medicine[]>(200, "Medicines retrieved successfully",
                    MedicineService.findAll());

            HttpResponse.getResponse(res, result);
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(400, e.getMessage(), null));
        }
    }
}
