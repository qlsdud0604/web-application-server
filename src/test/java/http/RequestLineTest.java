package http;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestLineTest {

    @Test
    public void create_method() {
        RequestLine requestLine = new RequestLine("GET /index.html HTTP/1.1");
        assertEquals("GET", requestLine.getMethod());
        assertEquals("/index.html", requestLine.getPath());
    }

    @Test
    public void create_path_and_params() {
        RequestLine requestLine = new RequestLine("GET /user/create?userId=kolon&password=kolon&name=kolon HTTP/1.1");
        assertEquals("GET", requestLine.getMethod());
        assertEquals("/user/create", requestLine.getPath());
        assertEquals("kolon", requestLine.getParams().get("userId"));
    }

}