package com.nutrix.query.models;

import lombok.Value;

import java.util.Date;

@Value
public class UpdateRecommendationModel {
    private String name;
    private String description;
    private Date lastModification;
}
