package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParam("userId"));

        if (user != null) {
            if (user.login(request.getParam("password"))) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("/index.html");
            } else {
                response.sendRedirect("/user/login_failed.html");
            }
        } else {
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
