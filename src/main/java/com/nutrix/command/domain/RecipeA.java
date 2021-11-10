package com.nutrix.command.domain;

import com.nutrix.command.application.Notification;
import command.CreateRecipeC;
import command.DeleteRecipeC;
import command.UpdateRecipeC;
import events.RecipeCreatedEvent;
import events.RecipeDeletedEvent;
import events.RecipeUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class RecipeA {

    @AggregateIdentifier
    private String id;

    private String name;
    private String description;
    private String preparation;
    private String ingredients;
    private Integer favorite;
    private Date createdAt;
    private Date lastModification;
    private Integer nutritionistId;

    public RecipeA(){
    }

    @CommandHandler
    public RecipeA(CreateRecipeC createRecipeC){
        Notification notification = validateRecipe(createRecipeC);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        RecipeCreatedEvent event = new RecipeCreatedEvent(
                createRecipeC.getId(),
                createRecipeC.getName(),
                createRecipeC.getDescription(),
                createRecipeC.getPreparation(),
                createRecipeC.getIngredients(),
                createRecipeC.getFavorite(),
                createRecipeC.getCreatedAt(),
                createRecipeC.getLastModification(),
                createRecipeC.getNutritionistId()
        );
        apply(event);
    }

    @CommandHandler
    public void on(UpdateRecipeC updateRecipeC){
        Notification notification = validateUpdateRecipe(updateRecipeC);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        RecipeUpdatedEvent event = new RecipeUpdatedEvent(
                updateRecipeC.getId(),
                updateRecipeC.getName(),
                updateRecipeC.getDescription(),
                updateRecipeC.getPreparation(),
                updateRecipeC.getIngredients(),
                updateRecipeC.getFavorite(),
                updateRecipeC.getCreatedAt(),
                updateRecipeC.getLastModification(),
                updateRecipeC.getNutritionistId()
        );
        apply(event);
    }

    @CommandHandler
    public void on(DeleteRecipeC deleteRecipeC){
        Notification notification = validateDeleteRecipe(deleteRecipeC);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        RecipeDeletedEvent event = new RecipeDeletedEvent(
                deleteRecipeC.getId()
        );
        apply(event);
    }

    private Notification validateRecipe(CreateRecipeC createRecipeC) {
        Notification notification = new Notification();
        validateRecipeId(createRecipeC.getId(), notification);
        return notification;
    }

    private Notification validateUpdateRecipe(UpdateRecipeC updateRecipeC) {
        Notification notification = new Notification();
        validateRecipeId(updateRecipeC.getId(), notification);
        return notification;
    }
    private Notification validateDeleteRecipe(DeleteRecipeC deleteRecipeC) {
        Notification notification = new Notification();
        validateRecipeId(deleteRecipeC.getId(), notification);
        return notification;
    }
    private void validateRecipeId(String recipeId, Notification notification) {
        if (recipeId == null) {
            notification.addError("Recipe id is missing");
        }
    }

    //Event Sourcing Handlers

    @EventSourcingHandler
    public void on(RecipeCreatedEvent recipeCreatedEvent){
        this.id = recipeCreatedEvent.getId();
        this.name = recipeCreatedEvent.getName();
        this.description = recipeCreatedEvent.getDescription();
        this.preparation = recipeCreatedEvent.getPreparation();
        this.ingredients = recipeCreatedEvent.getIngredients();
        this.favorite = recipeCreatedEvent.getFavorite();
        this.nutritionistId = recipeCreatedEvent.getNutritionistId();
    }

    @EventSourcingHandler
    public void on(RecipeUpdatedEvent recipeUpdatedEvent){
        this.id = recipeUpdatedEvent.getId();
        this.name = recipeUpdatedEvent.getName();
        this.description = recipeUpdatedEvent.getDescription();
        this.preparation = recipeUpdatedEvent.getPreparation();
        this.ingredients = recipeUpdatedEvent.getIngredients();
        this.lastModification = recipeUpdatedEvent.getLastModification();
        this.createdAt = recipeUpdatedEvent.getCreatedAt();
        this.favorite = recipeUpdatedEvent.getFavorite();
        this.nutritionistId = recipeUpdatedEvent.getNutritionistId();
    }

    @EventSourcingHandler
    public void on(RecipeDeletedEvent recipeDeletedEvent){
        this.id = recipeDeletedEvent.getId();
    }
}
