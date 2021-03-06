package com.nutrix.query.application.handlers;
import com.nutrix.command.infra.IRecipeRepository;
import com.nutrix.command.infra.Recipe;
import com.nutrix.query.models.CreateRecipeModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import queries.GetRecipesQuery;
import result.RecipeResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeQueryHandler {
    private final IRecipeRepository recipeRepository;

    @Autowired
    public RecipeQueryHandler(IRecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @QueryHandler
    public List<RecipeResult> handle(GetRecipesQuery query) {
        List<Recipe> recipes = recipeRepository.findAll();

        List<RecipeResult> recipeModels =
                recipes.stream()
                        .map(recipe -> new RecipeResult(
                                recipe.getId(),
                                recipe.getName(),
                                recipe.getDescription(),
                                recipe.getPreparation(),
                                recipe.getIngredients(),
                                recipe.getFavorite(),
                                recipe.getCreatedAt(),
                                recipe.getLastModification(),
                                recipe.getNutritionistId()
                        )).collect(Collectors.toList());
        return recipeModels;
    }
}
