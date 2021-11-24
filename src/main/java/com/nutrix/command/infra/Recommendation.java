package com.nutrix.command.infra;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Recommendation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    @Id
    @ApiModelProperty(notes = "Recommendation Id",name="recommendationId",required=true,example = "3af448f3-3656-4146-a764-0b289c68a426")
    private String id;
    @Column(name="name")
    @ApiModelProperty(notes = "Recommendation name",name="name",required=true,example = "Reduce tu consumo de calorías")
    private String name;
    @Column(name="description")
    @ApiModelProperty(notes = "Recommendation description",name="description",required=true,example = "Reducir tu consumo de calorías diarias es importante para reducir peso")
    private String description;
    @Column(name="createdAt")
    @ApiModelProperty(notes = "Recommendation created date",name="createdAt",required=true,example = "2021-11-11T04:59:29.789+00:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name="lastModification")
    @ApiModelProperty(notes = "Recommendation last modification date",name="lastModification",required=true,example = "2021-12-18T04:59:29.789+00:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;
    @Column(name="nutritionistId")
    @ApiModelProperty(notes = "Recommendation nutritionist Id",name="nutritionistId",required=true,example = "4d36e656-38fa-486e-804b-1cd18eeaadb8")
    private String nutritionistId;
}
