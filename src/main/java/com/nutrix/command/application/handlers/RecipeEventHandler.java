package com.nutrix.command.application.handlers;

import com.nutrix.command.infra.IRecipeRepository;
import com.nutrix.command.infra.Recipe;
import events.RecipeCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("recipe")
public class RecipeEventHandler {
    private final IRecipeRepository recipeRepository;

    @Autowired
    public RecipeEventHandler(IRecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @EventHandler
    public void on(RecipeCreatedEvent event){
        recipeRepository.save(new Recipe(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getPreparation(),
                event.getIngredients(),
                event.getFavorite(),
                event.getCreatedAt(),
                event.getLastModification(),
                event.getNutritionistId()
        )
        );
    }
}
