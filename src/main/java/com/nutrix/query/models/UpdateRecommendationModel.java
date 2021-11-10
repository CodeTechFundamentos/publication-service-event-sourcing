package com.nutrix.query.models;

import lombok.Value;

import java.util.Date;

@Value
public class UpdateRecommendationModel {
    private String name;
    private String description;
    private Date createdAt;
    private Date lastModification;
    private Integer nutritionistId;
}
