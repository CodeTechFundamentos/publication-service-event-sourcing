package com.nutrix.command.application.handlers;

import com.nutrix.command.infra.IRecommendationRepository;
import com.nutrix.command.infra.Recommendation;
import events.RecipeDeletedEvent;
import events.RecommendationCreatedEvent;
import events.RecommendationDeletedEvent;
import events.RecommendationUpdatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("recommendation")
public class RecommendationEventHandler {
    private final IRecommendationRepository recommendationRepository;

    @Autowired
    public RecommendationEventHandler(IRecommendationRepository recommendationRepository){
        this.recommendationRepository = recommendationRepository;
    }

    @EventHandler
    public void on(RecommendationCreatedEvent event){
        recommendationRepository.save(new Recommendation(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getCreatedAt(),
                event.getLastModification(),
                event.getNutritionistId()
        )
        );
    }
    @EventHandler
    public void on(RecommendationUpdatedEvent event){
        recommendationRepository.save(new Recommendation(
                        event.getId(),
                        event.getName(),
                        event.getDescription(),
                        event.getCreatedAt(),
                        event.getLastModification(),
                        event.getNutritionistId()
                )
        );
    }
    @EventHandler
    public void on(RecommendationDeletedEvent event){
        recommendationRepository.deleteById(event.getId());
    }
}
