package com.example.demo.filter;

import com.example.demo.util.ReadableRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WrappingRequestResponseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequestWrapper wrappingRequest = new ReadableRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(httpServletResponse);

        filterChain.doFilter(wrappingRequest, wrappingResponse);

        wrappingResponse.copyBodyToResponse();
    }
}
