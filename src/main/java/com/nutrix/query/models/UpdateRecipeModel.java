package com.nutrix.query.models;

import lombok.Value;

import java.util.Date;

@Value
public class UpdateRecipeModel {
    private String name;
    private String description;
    private String preparation;
    private String ingredients;
    private Integer favorite;
    private Date createdAt;
    private Date lastModification;
    private Integer nutritionistId;
}
