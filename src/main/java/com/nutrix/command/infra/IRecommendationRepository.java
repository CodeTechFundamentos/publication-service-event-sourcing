package com.nutrix.command.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecommendationRepository extends JpaRepository<Recommendation, String> {
}