package com.structurizr.demo.module1;

import com.structurizr.annotations.Tags;
import com.structurizr.annotations.Uses;
import com.structurizr.demo.module1.repository.Repository1;
import org.springframework.stereotype.Service;

@Service
@Tags("Service")
public class Service1 {
    @Uses(
            value = "Calls component",
            technology = "JVM",
            tags = "Asynchronous"
    )
    private final Component1 component1;

    private final Repository1 repository1;

    public Service1(Component1 component1, Repository1 repository1) {
        this.component1 = component1;
        this.repository1 = repository1;
    }
}
