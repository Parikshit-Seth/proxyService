package com.proxy.service;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Service
public class RemoteService {
    private HttpComponentsClientHttpRequestFactory clientHttpRequestFactory;
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    public RemoteService() {
        clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        restTemplate = new RestTemplate(clientHttpRequestFactory);
    }

    public ResponseEntity invokeGet() {
        return restTemplate.exchange(getURI(), HttpMethod.GET, new HttpEntity<>(getHeader()), String.class);
    }

    public ResponseEntity invokePost(String body) {
        return restTemplate.exchange(getURI(), HttpMethod.POST, new HttpEntity<>(body, getHeader()), String.class);
    }

    private HttpHeaders getHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                httpHeaders.set(header, request.getHeader(header));
            }
        }
        return httpHeaders;
    }

    private String getURI() {
        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher apm = new AntPathMatcher();
        return StringUtils.replace(apm.extractPathWithinPattern(bestMatchPattern, path), ":/", "://");
    }
}
