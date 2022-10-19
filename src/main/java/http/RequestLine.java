package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

    private HttpMethod method;   // 요청 메서드(GET, POST)
    private String path;   // 요청 URL
    private String queryString;   // 쿼리 스트링

    public RequestLine(String requestLine) {
        log.debug("===== request line : {}=====", requestLine);   // 요청 라인

        String[] tokens = requestLine.split(" ");
        method = HttpMethod.valueOf(tokens[0]);

        String[] url = tokens[1].split("\\?");
        path = url[0];

        if (url.length == 2) {
            queryString = url[1];
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }
}
