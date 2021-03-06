package com.nutrix.query.application.handlers;
import com.nutrix.command.infra.IRecommendationRepository;
import com.nutrix.command.infra.Recommendation;
import com.nutrix.query.models.CreateRecommendationModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import queries.GetRecommendationsQuery;
import result.RecommendationResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecommendationQueryHandler {
    private final IRecommendationRepository recommendationRepository;

    @Autowired
    public RecommendationQueryHandler(IRecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @QueryHandler
    public List<RecommendationResult> handle(GetRecommendationsQuery query) {
        List<Recommendation> recipes = recommendationRepository.findAll();

        List<RecommendationResult> recommendationModels =
                recipes.stream()
                        .map(recipe -> new RecommendationResult(
                                recipe.getId(),
                                recipe.getName(),
                                recipe.getDescription(),
                                recipe.getCreatedAt(),
                                recipe.getLastModification(),
                                recipe.getNutritionistId()
                        )).collect(Collectors.toList());
        return recommendationModels;
    }
}
