package com.marcincho.projects_sm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcincho.projects_sm.entity.Project;
import com.marcincho.projects_sm.repository.ProjectRepository;
import com.marcincho.projects_sm.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public void deleteProject(String projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getById(String projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

}
