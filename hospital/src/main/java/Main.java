
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.Tomcat;

import utils.HttpResponse;
import utils.ResponseEntity;

@WebServlet("/hello")
public class Main extends HttpServlet {

    public static void main(String[] args) {
        String webappDir = "src/main/webapp/";
        String contextPath = "/";

        Tomcat tomcat = new Tomcat();

        tomcat.addWebapp(contextPath, new File(webappDir).getAbsolutePath());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpResponse.getResponse(resp, new ResponseEntity<>(
                    HttpServletResponse.SC_OK,
                    "Welcome to E-hospital api.",
                    null));
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.getResponse(resp, new ResponseEntity<>(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong, try again.",
                    null));
        }

    }

}
