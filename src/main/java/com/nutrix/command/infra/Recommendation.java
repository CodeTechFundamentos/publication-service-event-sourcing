package com.nutrix.command.infra;

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
    private String id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name="lastModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;
    @Column(name="nutritionistId")
    private Integer nutritionistId;
}
