package com.marcincho.projects_sm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.marcincho.projects_sm.entity.Project;

@Service
public interface ProjectService {

    public void deleteProject(String projectId);

    public List<Project> getAllProjects();

    public Optional<Project> getById(String projectId);

    public Project saveProject(Project project);

    public boolean updateProject(Project project);

}
