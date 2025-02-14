package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {
    private static final Logger log = LoggerFactory.getLogger(RequestParams.class);

    private Map<String, String> params = new HashMap<String, String>();

    public void addQueryString(String queryString) {
        putParams(queryString);
    }
    
    public void addBody(String body) {
        putParams(body);
    }

    public String getParam(String name) {
        return params.get(name);
    }

    private void putParams(String data) {
        if (data == null || data.isEmpty()) {
            return;
        }

        params.putAll(HttpRequestUtils.parseQueryString(data));
    }
}
