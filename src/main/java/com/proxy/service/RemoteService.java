package com.proxy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

@Service
public class RemoteService {
    public static final String USER_AGENT = "User-Agent";

    @Autowired
    private HttpServletRequest request;

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity invokeGet(String header) {
        HttpHeaders httpHeaders =  new HttpHeaders();
        httpHeaders.set(USER_AGENT, header);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(getURI(), HttpMethod.GET, request, String.class);
    }

    public ResponseEntity invokePost(String header, String body) {
        HttpHeaders httpHeaders =  new HttpHeaders();
        httpHeaders.set(USER_AGENT, header);
        HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange(getURI(), HttpMethod.POST, request, String.class);
    }

    private String getURI() {
        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher apm = new AntPathMatcher();
        return StringUtils.replace(apm.extractPathWithinPattern(bestMatchPattern, path),":/","://");
    }
}
