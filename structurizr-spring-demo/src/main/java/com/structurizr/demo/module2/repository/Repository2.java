package com.structurizr.demo.module2.repository;

import com.structurizr.annotations.Uses;
import com.structurizr.demo.module2.model.Entity2;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

@Uses(target = "postgresql", value = "Reads and writes to")
public interface Repository2 extends R2dbcRepository<Entity2, Long> {
}
