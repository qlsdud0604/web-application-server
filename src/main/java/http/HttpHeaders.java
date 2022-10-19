package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private static final Logger log = LoggerFactory.getLogger(HttpHeaders.class);

    private static final String CONTENT_LENGTH = "Content-Length";

    private Map<String, String> headers = new HashMap<String, String>();

    public void add(String headerLine) {
        log.debug("header : {}", headerLine);

        String[] tokens = headerLine.split(":");
        headers.put(tokens[0].trim(), tokens[1].trim());
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public int getContentLength() {
        return getIntHeader(CONTENT_LENGTH);
    }

    public int getIntHeader(String name) {
        String value = getHeader(name);
        return value == null ? 0 : Integer.parseInt(value);
    }
}
