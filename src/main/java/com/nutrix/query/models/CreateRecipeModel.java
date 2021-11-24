package com.nutrix.query.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.util.Date;

@Value
public class CreateRecipeModel {
    @ApiModelProperty(notes = "Recipe name",name="name",required=true,example = "Ensala de frutas")
    private String name;
    @ApiModelProperty(notes = "Recipe description",name="description",required=true,example = "Receta de ensalada de frutas para desayunar")
    private String description;
    @ApiModelProperty(notes = "Recipe preparation",name="preparation",required=true,example = "Se cortan las frutas en cuadros y se añaden a un bowl")
    private String preparation;
    @ApiModelProperty(notes = "Recipe ingredients",name="ingredients",required=true,example = "Piña, Sandía, Fresa")
    private String ingredients;
    @ApiModelProperty(notes = "Recipe favorites",name="favorite",required=true,example = "0")
    private Integer favorite;
    @ApiModelProperty(notes = "Recipe created date",name="createdAt",required=true,example = "2021-11-11T04:59:29.789+00:00")
    private Date createdAt;
    @ApiModelProperty(notes = "Recipe last modification date",name="lastModification",required=true,example = "2021-12-15T04:59:29.789+00:00")
    private Date lastModification;
    @ApiModelProperty(notes = "Recipe nutritionist Id",name="nutritionistId",required=true,example = "13245768")
    private String nutritionistId;
}
