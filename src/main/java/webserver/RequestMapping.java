package webserver;

import controller.Controller;
import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static Map<String, Controller> controllers = new HashMap<String, Controller>();

    static {
        controllers.put("/user/create", new UserCreateController());
        controllers.put("/user/login", new UserLoginController());
        controllers.put("/user/list", new UserListController());
    }

    public static Controller getController(String requestUrl) {
        return controllers.get(requestUrl);
    }
}
