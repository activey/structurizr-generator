package com.structurizr.demo.module2;

import com.structurizr.annotations.Tags;
import com.structurizr.demo.module1.Service1;
import com.structurizr.demo.module2.repository.Repository2;
import org.springframework.stereotype.Service;

@Service
@Tags("Service")
public class Service2 {

    private final Repository2 repository2;

    private final Service1 service1;

    public Service2(Repository2 repository2, Service1 service1) {
        this.repository2 = repository2;
        this.service1 = service1;
    }
}
