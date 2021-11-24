package com.nutrix.command.domain;

import com.nutrix.command.application.Notification;
import command.CreateRecommendationC;
import command.DeleteRecommendationC;
import command.UpdateRecommendationC;
import events.RecipeCreatedEvent;
import events.RecommendationCreatedEvent;
import events.RecommendationDeletedEvent;
import events.RecommendationUpdatedEvent;
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
    private String nutritionistId;

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

    @CommandHandler
    public void on(UpdateRecommendationC updateRecommendationC){
        Notification notification = validateUpdateRecommendation(updateRecommendationC);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        RecommendationUpdatedEvent event = new RecommendationUpdatedEvent(
                updateRecommendationC.getId(),
                updateRecommendationC.getName(),
                updateRecommendationC.getDescription(),
                updateRecommendationC.getCreatedAt(),
                updateRecommendationC.getLastModification(),
                updateRecommendationC.getNutritionistId()
        );
        apply(event);
    }

    @CommandHandler
    public void on(DeleteRecommendationC deleteRecommendationC){
        Notification notification = validateDeleteRecommendation(deleteRecommendationC);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        RecommendationDeletedEvent event = new RecommendationDeletedEvent(
                deleteRecommendationC.getId()
        );
        apply(event);
    }

    private Notification validateRecommendation(CreateRecommendationC createRecommendationC) {
        Notification notification = new Notification();
        validateRecommendationId(createRecommendationC.getId(), notification);
        return notification;
    }

    private Notification validateUpdateRecommendation(UpdateRecommendationC updateRecommendationC) {
        Notification notification = new Notification();
        validateRecommendationId(updateRecommendationC.getId(), notification);
        return notification;
    }
    private Notification validateDeleteRecommendation(DeleteRecommendationC deleteRecommendationC) {
        Notification notification = new Notification();
        validateRecommendationId(deleteRecommendationC.getId(), notification);
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

    @EventSourcingHandler
    public void on(RecommendationUpdatedEvent recommendationUpdatedEvent){
        this.id = recommendationUpdatedEvent.getId();
        this.name = recommendationUpdatedEvent.getName();
        this.description = recommendationUpdatedEvent.getDescription();
        this.createdAt = recommendationUpdatedEvent.getCreatedAt();
        this.lastModification = recommendationUpdatedEvent.getLastModification();
        this.nutritionistId = recommendationUpdatedEvent.getNutritionistId();
    }

    @EventSourcingHandler
    public void on(RecommendationDeletedEvent recommendationDeletedEvent){
        this.id = recommendationDeletedEvent.getId();
    }
}
