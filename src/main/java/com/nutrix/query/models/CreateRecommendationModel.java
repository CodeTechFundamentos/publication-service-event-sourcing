package com.nutrix.query.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.util.Date;

@Value
public class CreateRecommendationModel {
    @ApiModelProperty(notes = "Recommendation name",name="name",required=true,example = "Reduce tu consumo de calorías")
    private String name;
    @ApiModelProperty(notes = "Recommendation description",name="description",required=true,example = "Reducir tu consumo de calorías diarias es importante para reducir peso")
    private String description;
    @ApiModelProperty(notes = "Recommendation created date",name="createdAt",required=true,example = "2021-11-11T04:59:29.789+00:00")
    private Date createdAt;
    @ApiModelProperty(notes = "Recommendation last modification date",name="lastModification",required=true,example = "2021-12-18T04:59:29.789+00:00")
    private Date lastModification;
    @ApiModelProperty(notes = "Recommendation nutritionist Id",name="nutritionistId",required=true,example = "13245768")
    private String nutritionistId;
}
