package utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class HttpResponse {
    public static <T> void getResponse(
            HttpServletResponse res,
            ResponseEntity<T> entity) throws IOException {

        res.setStatus(entity.statusCode);
        res.setContentType("application/json");

        OutputStream out = res.getOutputStream();
        out.write(new JsonUtil().toJson(entity).getBytes());
        out.flush();
    }
}
