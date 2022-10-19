package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCreateController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getParam("userId"),
                request.getParam("password"),
                request.getParam("name"),
                request.getParam("email")
        );
        DataBase.addUser(user);
        log.debug("user : {}", user);

        response.sendRedirect("/index.html");
    }
}
