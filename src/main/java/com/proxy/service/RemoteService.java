package com.proxy.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RemoteService {
    public static final String USER_AGENT = "User-Agent";
    RestTemplate restTemplate = new RestTemplate();
    public ResponseEntity invokeGet(String header, String uri) {
        HttpHeaders httpHeaders =  new HttpHeaders();
        httpHeaders.set(USER_AGENT, header);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
    }

    public ResponseEntity invokePost(String header, String uri, String body) {
        HttpHeaders httpHeaders =  new HttpHeaders();
        httpHeaders.set(USER_AGENT, header);
        HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
    }
}
