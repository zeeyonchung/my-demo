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

public class ReadableRequestWrapper extends HttpServletRequestWrapper {
    private Charset encoding;
    private byte[] rawData;

    public ReadableRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        String encoding = request.getCharacterEncoding();
        this.encoding = StringUtils.isEmpty(encoding) ? StandardCharsets.UTF_8 : Charset.forName(encoding);

        // org.apache.catalina.connector.Request getParameterMap()이 호출되면
        // 해당 인스턴스의 필드 ParameterMap<String, String[]> parameterMap에
        // 들어온 값을 복사해두고 나중에 getParameterXX()가 호출되었을 때 이 복사해둔 값을 반환한다.
        // inputStream이 사용되고 나서는 들어온 파라미터 값을 읽을 수 없기 때문에 그 전에 호출해주었다.
        request.getParameterMap();

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
}
