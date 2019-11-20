package com.proxy.controller;

import com.proxy.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyController {
    @Autowired
    private RemoteService service;

    @RequestMapping(value = "/proxy/**", method = RequestMethod.GET)
    public ResponseEntity<String> greeting() {
        return service.invokeGet();
    }

    @RequestMapping(value = "/proxy/**", method = RequestMethod.POST)
    public ResponseEntity<String> greeting(@RequestBody String body) {
        return service.invokePost(body);
    }
}
