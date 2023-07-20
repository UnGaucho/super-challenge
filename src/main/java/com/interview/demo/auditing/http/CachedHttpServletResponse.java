package com.interview.demo.auditing.http;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class CachedHttpServletResponse extends ContentCachingResponseWrapper {

    /**
     * Superclass defaults to 200. Will be overriden when it is intercepted during the filter chain.
     */
    private int cachedStatus = 200;

    /**
     * Create a new ContentCachingResponseWrapper for the given servlet response.
     *
     * @param response the original servlet response
     */
    public CachedHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public int getStatus() {
        cachedStatus = super.getStatus();
        return cachedStatus;
    }

    @Override
    public void setStatus(int sc) {
        cachedStatus = sc;
        super.setStatus(sc);
    }

    @Nullable
    public String getResponseBody() {
        try {
            byte[] arr = getContentAsByteArray();
            return new String(arr, getCharacterEncoding());
        } catch (Exception e){
            return null;
        }
    }

    public int getCachedStatus() {
        return cachedStatus;
    }
}
