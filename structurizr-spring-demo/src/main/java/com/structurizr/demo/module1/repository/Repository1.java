package com.structurizr.demo.module1.repository;

import com.structurizr.annotations.Uses;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

@Uses(target = "postgresql", value = "Reads and writes to")
public interface Repository1 extends R2dbcRepository {
}
