package webserver;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import controller.Controller;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;


public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            if (request.getCookies().getCookie("SESSIONID") == null) {
                response.addHeader("Set-Cookie", "SESSIONID=" + UUID.randomUUID());
            }

            Controller controller = RequestMapping.getController(request.getPath());

            if (controller == null) {
                String path = getDefaultPath(request.getPath());
                response.forward(path);
            } else {
                controller.service(request, response);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }

    private String getSessionId(String cookieValue) {
        Map<String, String> cookies = new HashMap<String, String>();
        cookies = HttpRequestUtils.parseCookies(cookieValue);

        return cookies.get("SESSIONID");
    }
}
