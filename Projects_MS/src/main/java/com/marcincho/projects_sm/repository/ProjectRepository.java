package com.marcincho.projects_sm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcincho.projects_sm.entity.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {

}
