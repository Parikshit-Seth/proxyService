package com.proxy.controller;

import com.proxy.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProxyController {
    @Autowired
    private RemoteService service;

    @RequestMapping(value = "/proxy/**", method = RequestMethod.GET)
    public ResponseEntity<String> greeting(@RequestHeader("User-Agent") String agent) {
        return service.invokeGet(agent);
    }

    @RequestMapping(value = "/proxy/**", method = RequestMethod.POST)
    public ResponseEntity<String> greeting(@RequestHeader("User-Agent") String agent,
                                           @RequestBody String body) {
        return service.invokePost(agent, body);
    }
}
