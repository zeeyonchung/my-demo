package com.example.demo.interceptor;

import com.example.demo.util.ReadableRequestWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("<<<<< REQUEST {} [{}] / Parameter Map: {} / Request Body: {}",
                request.getMethod(),
                request.getRequestURI() + extractQueryString(request),
                extractRequestParameters(request),
                extractRequestBody(request));

        return true;
    }

    private String extractQueryString(HttpServletRequest request) {
        String queryString = request.getQueryString();

        if (StringUtils.isNotEmpty(queryString)) {
            return "?" + queryString;
        }

        return "";
    }

    private String extractRequestParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        JsonNode parameters = objectMapper.valueToTree(parameterMap);
        return parameters.toString();
    }

    private String extractRequestBody(HttpServletRequest request) {
        if (!(request instanceof ReadableRequestWrapper)) {
            return "";
        }

        ReadableRequestWrapper wrappingRequest = (ReadableRequestWrapper) request;
        byte[] bodyRawData = wrappingRequest.getContentAsByteArray();

        if (bodyRawData.length > 0) {
            return new String(bodyRawData);
        }

        return "";
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(">>>>> RESPONSE {} [{}] ({}) / Response Body: {}",
                request.getMethod(),
                request.getRequestURI() + extractQueryString(request),
                response.getStatus(),
                extractResponseBody(response));
    }

    private String extractResponseBody(HttpServletResponse response) {
        if (!(response instanceof ContentCachingResponseWrapper)) {
            return "";
        }

        ContentCachingResponseWrapper wrappingResponse = (ContentCachingResponseWrapper) response;
        byte[] bodyRawData = wrappingResponse.getContentAsByteArray();

        if (bodyRawData.length > 0) {
            return new String(bodyRawData);
        }

        return "";
    }
}
