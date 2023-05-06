package com.structurizr.demo.module2.infrastructure.rest;

import com.structurizr.annotations.UsedBy;
import com.structurizr.demo.module2.Service2;
import org.springframework.web.bind.annotation.RestController;

@RestController
@UsedBy(value = "Calls the controller", source = "nginx", technology = "HTTPS", tags = "Asynchronous")
public class RestController2 {

    private final Service2 service2;

    public RestController2(Service2 service2) {
        this.service2 = service2;
    }
}
