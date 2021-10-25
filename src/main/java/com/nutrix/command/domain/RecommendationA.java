package com.nutrix.command.domain;

import com.nutrix.command.application.Notification;
import command.CreateRecommendationC;
import events.RecipeCreatedEvent;
import events.RecommendationCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class RecommendationA {

    @AggregateIdentifier
    private String id;

    private String name;
    private String description;
    private Date createdAt;
    private Date lastModification;
    private Integer nutritionistId;

    public RecommendationA(){
    }

    @CommandHandler
    public RecommendationA(CreateRecommendationC createRecommendationC){
        Notification notification = validateRecommendation(createRecommendationC);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        RecommendationCreatedEvent event = new RecommendationCreatedEvent(
                createRecommendationC.getId(),
                createRecommendationC.getName(),
                createRecommendationC.getDescription(),
                createRecommendationC.getCreatedAt(),
                createRecommendationC.getLastModification(),
                createRecommendationC.getNutritionistId()
        );
        apply(event);
    }

    private Notification validateRecommendation(CreateRecommendationC createRecommendationC) {
        Notification notification = new Notification();
        validateRecommendationId(createRecommendationC.getId(), notification);
        return notification;
    }

    private void validateRecommendationId(String recommendationId, Notification notification) {
        if (recommendationId == null) {
            notification.addError("Recommendation id is missing");
        }
    }

    //Event Sourcing Handlers

    @EventSourcingHandler
    public void on(RecommendationCreatedEvent recommendationCreatedEvent){
        this.id = recommendationCreatedEvent.getId();
        this.name = recommendationCreatedEvent.getName();
        this.description = recommendationCreatedEvent.getDescription();
        this.createdAt = recommendationCreatedEvent.getCreatedAt();
        this.lastModification = recommendationCreatedEvent.getLastModification();
        this.nutritionistId = recommendationCreatedEvent.getNutritionistId();
    }
}
