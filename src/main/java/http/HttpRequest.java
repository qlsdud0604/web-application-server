package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String method;   // 요청 메서드(GET, POST)
    private String path;   // 요청 URL
    private Map<String, String> headers = new HashMap<String, String>();   // 요청 헤더
    private Map<String, String> params = new HashMap<String, String>();   // 요청 파라메터(쿼리 스트링, 바디)

    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader((new InputStreamReader(in, "UTF-8")));
            String line = br.readLine();
            if (line == null) {
                return;
            }

            processRequestLine(line);   // 요청 라인으로부터 요청 메서드, 요청 URL, 요청 파라미터를 분리

            line = br.readLine();
            while (!line.equals("")) {   // 요청 헤더 분리
                log.debug("header : {}", line);

                String[] tokens = line.split(":");
                headers.put(tokens[0].trim(), tokens[1].trim());
                line = br.readLine();
            }

            if (method.equals("POST")) {
                String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                params = HttpRequestUtils.parseQueryString(body);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    private void processRequestLine(String line) {
        log.debug("===== request line : {} =====", line);   // 요청 라인

        String[] tokens = line.split(" ");
        method = tokens[0];

        if (method.equals("POST")) {
            path = tokens[1];
            return;
        }

        int index = tokens[1].indexOf("?");
        if (index == -1) {
            path = tokens[1];
        } else {
            path = tokens[1].substring(0, index);
            params = HttpRequestUtils.parseQueryString(tokens[1].substring(index + 1));
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getParam(String name) {
        return params.get(name);
    }
}
