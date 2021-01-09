package com.example.demo.util;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ReadableRequestWrapper extends HttpServletRequestWrapper {
    private Charset encoding;
    private Map<String, String[]> parameterMap = new HashMap<>();
    private byte[] rawData;

    public ReadableRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        String encoding = request.getCharacterEncoding();
        this.encoding = StringUtils.isEmpty(encoding) ? StandardCharsets.UTF_8 : Charset.forName(encoding);

        this.parameterMap.putAll(request.getParameterMap());

        ServletInputStream inputStream = request.getInputStream();
        this.rawData = IOUtils.toByteArray(inputStream);
    }

    public byte[] getContentAsByteArray() {
        return this.rawData;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);
        return new ReadableInputStream(byteArrayInputStream);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }

    private static class ReadableInputStream extends ServletInputStream {
        private final InputStream inputStream;

        private ReadableInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            // do nothing
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }

    @Override
    public String getParameter(String name) {
        String[] values = parameterMap.get(name);

        if (values == null || values.length < 1) {
            return null;
        }

        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(parameterMap);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(parameterMap.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = parameterMap.get(name);

        if (values == null || values.length < 1) {
            return null;
        }

        String[] result = new String[values.length];
        System.arraycopy(values, 0, result, 0, values.length);

        return result;
    }
}
