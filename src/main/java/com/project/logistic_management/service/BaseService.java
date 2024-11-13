package com.project.logistic_management.service;

/*
* R: Repository interface
* M: Base mapper
*/
public class BaseService<R, M> {
    protected R repository;
    protected M mapper;

    public BaseService(R repo, M mapper) {
        this.repository = repo;
        this.mapper = mapper;
    }
}
