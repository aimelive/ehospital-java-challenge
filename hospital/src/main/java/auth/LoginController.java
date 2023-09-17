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

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String requestBody = RequestBodyUtil.getBody(req);
            User user = null;
            String identifier = "";

            if (requestBody.contains("\"username\":")) {
                identifier = RequestBodyUtil.getValueFromJsonKey(requestBody, "username");
                user = new JsonUtil().fromJson(requestBody, Patient.class);
            } else if (requestBody.contains("\"email\":")) {
                identifier = RequestBodyUtil.getValueFromJsonKey(requestBody, "email");
                user = new JsonUtil().fromJson(requestBody, Physician.class);
            } else if (requestBody.contains("\"phoneNumber\":")) {
                identifier = RequestBodyUtil.getValueFromJsonKey(requestBody, "phoneNumber");
                user = new JsonUtil().fromJson(requestBody, Pharmacist.class);
            } else {
                throw new IllegalArgumentException("Incorrect credentials, try again.");
            }

            ResponseEntity<String> result = user.login(identifier, user.getPassword());
            HttpResponse.getResponse(res, result);
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(res, new ResponseEntity<>(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    null));
        }
    }

}
