package com.epam.training.gen.ai.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @GetMapping(value = "/version")
    public String getVersion(@Value("${info.app.version}") String version,
                             @Value("${info.app.name}") String name) {
        return name + " : " + version;
    }
}
