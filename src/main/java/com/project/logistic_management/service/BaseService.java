package com.project.logistic_management.service;

import com.project.logistic_management.mapper.BaseMapper;
import com.project.logistic_management.repository.BaseRepository;

public class BaseService {
    protected BaseRepository repository;
    protected BaseMapper mapper;

    public BaseService(BaseRepository repo, BaseMapper mapper) {
        this.repository = repo;
        this.mapper = mapper;
    }
}
