package com.marcincho.projects_sm.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "projects")
public class Project {

    private String id;

    private String title;

    private String type;

    private String description;

    private String location;

    private String price;

    private String name;

    private String email;

    private String phoneNumber;

    private String file;

}
