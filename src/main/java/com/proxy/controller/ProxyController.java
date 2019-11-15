package com.proxy.controller;

import com.proxy.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProxyController {
    @Autowired
    private RemoteService service;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/proxy/**", method = RequestMethod.GET)
    public ResponseEntity<String> greeting(@RequestHeader("User-Agent") String agent) {
        String path = extractFilePath(request);
        return service.invokeGet(agent, path);
    }

    @RequestMapping(value = "/proxy/**", method = RequestMethod.POST)
    public ResponseEntity<String> greeting(@RequestHeader("User-Agent") String agent,
                                           @RequestBody String body) {
        String path = extractFilePath(request);
        return service.invokePost(agent, path, body);
    }

    private String extractFilePath(HttpServletRequest request) {
        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher apm = new AntPathMatcher();
        return StringUtils.replace(apm.extractPathWithinPattern(bestMatchPattern, path),":/","://");
    }
}
