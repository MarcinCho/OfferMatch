package com.marcincho.projects_sm.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.marcincho.projects_sm.entity.Project;
import com.marcincho.projects_sm.repository.ProjectRepository;
import com.marcincho.projects_sm.service.ProjectService;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin
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

    @PutMapping("/project/{id}")
    public boolean updateProject(@PathVariable String id, @RequestBody Project project) {
        return projectService.updateProject(project);
    }

}
