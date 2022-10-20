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

    private RequestLine requestLine;   // 요청 라인
    private HttpHeaders headers;   // 요청 헤더
    private RequestParams params = new RequestParams();   // 요청 파라메터(쿼리 스트링, 바디)

    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader((new InputStreamReader(in, "UTF-8")));
            requestLine = new RequestLine(createRequestLine(br));
            params.addQueryString(requestLine.getQueryString());
            headers = processHeaders(br);
            params.addBody(IOUtils.readData(br, headers.getContentLength()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    private String createRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            throw new IllegalStateException();
        }
        return line;
    }

    private HttpHeaders processHeaders(BufferedReader br) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String line;

        while (!(line = br.readLine()).equals("")) {
            headers.add(line);
        }
        return headers;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        return headers.getHeader(name);
    }

    public String getParam(String name) {
        return params.getParam(name);
    }

    public HttpCookie getCookies() {
        return new HttpCookie(getHeader("Cookie"));
    }

    public HttpSession getSession() {
        return HttpSessions.getSession(getCookies().getCookie("SESSIONID"));
    }
}
