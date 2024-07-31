package com.marcincho.projects_sm.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcincho.projects_sm.entity.Project;
import com.marcincho.projects_sm.repository.ProjectRepository;
import com.marcincho.projects_sm.service.ProjectService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin("http://127.0.0.1:5173")
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/project")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/project")
    public Project postProject(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    @GetMapping("/project/{id}")
    public Optional<Project> getProject(@PathVariable String id) {
        return projectService.getById(id);
    }

    @DeleteMapping("/project/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }

}
