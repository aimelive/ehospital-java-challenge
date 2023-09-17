package auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import patient.Patient;
import pharmacist.Pharmacist;
import physician.Physician;
import utils.HttpResponse;
import utils.JsonUtil;
import utils.RequestBodyUtil;
import utils.ResponseEntity;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String requestBody = RequestBodyUtil.getBody(req);
            Role role = Role.valueOf(RequestBodyUtil.getValueFromJsonKey(requestBody, "role").toUpperCase());

            User user = null;

            if (role.equals(Role.PATIENT)) {
                user = new JsonUtil().fromJson(requestBody, Patient.class);
            } else if (role.equals(Role.PHYSICIAN)) {
                user = new JsonUtil().fromJson(requestBody, Physician.class);
            } else if (role.equals(Role.PHARMACIST)) {
                user = new JsonUtil().fromJson(requestBody, Pharmacist.class);
            } else {
                throw new IllegalArgumentException("Invalid user role");
            }

            ResponseEntity<User> result = user.register();
            HttpResponse.getResponse(res, result);
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage(),
                    null));
        }
    }

}
