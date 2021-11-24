package com.nutrix.command.infra;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe{
    @Id
    @ApiModelProperty(notes = "Recipe Id",name="recipeId",required=true,example = "b9253062-f9a1-4a0b-9d95-89908cc6237f")
    private String id;
    @Column(name="name")
    @ApiModelProperty(notes = "Recipe name",name="name",required=true,example = "Ensala de frutas")
    private String name;
    @Column(name="description")
    @ApiModelProperty(notes = "Recipe description",name="description",required=true,example = "Receta de ensalada de frutas para desayunar")
    private String description;
    @Column(name="preparation")
    @ApiModelProperty(notes = "Recipe preparation",name="preparation",required=true,example = "Se cortan las frutas en cuadros y se añaden a un bowl")
    private String preparation;
    @Column(name="ingredients")
    @ApiModelProperty(notes = "Recipe ingredients",name="ingredients",required=true,example = "Piña, Sandía, Fresa")
    private String ingredients;
    @Column(name="favorite")
    @ApiModelProperty(notes = "Recipe favorites",name="favorite",required=true,example = "100")
    private Integer favorite;
    @Column(name="createdAt")
    @ApiModelProperty(notes = "Recipe created date",name="createdAt",required=true,example = "2021-11-11T04:59:29.789+00:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name="lastModification")
    @ApiModelProperty(notes = "Recipe last modification date",name="lastModification",required=true,example = "2021-12-15T04:59:29.789+00:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;
    @Column(name="nutritionistId")
    @ApiModelProperty(notes = "Recipe nutritionist Id",name="nutritionistId",required=true,example = "4d36e656-38fa-486e-804b-1cd18eeaadb8")
    private String nutritionistId;
}
