package com.interview.demo.auditing.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

/**
 * Small servlet request decorator that caches the servlet body
 */
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    private final String cachedMethod;

    private final String cachedRequestUri;

    private final String cachedParams;

    /**
     * Caches request data as ServletRequest usually gets recycled.
     * @param request the original HttpServletRequest
     * @throws IOException
     */
    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
        this.cachedParams = getRequestParameters(request);
        this.cachedMethod = request.getMethod();
        this.cachedRequestUri = request.getRequestURI();
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBodyServletInputStream(this.cachedBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        // Create a reader from cachedContent and return it
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    private String getRequestParameters(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        request.getParameterMap().forEach((name, values) -> {
            for (String value : values) {
                builder.append(name).append("=").append(value).append("&");
            }
        });

        String params = builder.toString();
        if (params.endsWith("&")) {
            params = params.substring(0, params.length() - 1);
        }

        return params;
    }


    public String getRequestBody() throws IOException {
        BufferedReader reader = getReader();
        return reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String getCachedMethod() {
        return cachedMethod;
    }

    public String getCachedRequestUri() {
        return cachedRequestUri;
    }

    public String getCachedParams() {
        return cachedParams;
    }



    private static class CachedBodyServletInputStream extends ServletInputStream {

        private final InputStream cachedBodyInputStream;

        public CachedBodyServletInputStream(byte[] cachedBody) {
            this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
        }

        @Override
        public boolean isFinished() {
            try {
                return cachedBodyInputStream.available() == 0;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return cachedBodyInputStream.read();
        }
    }
}