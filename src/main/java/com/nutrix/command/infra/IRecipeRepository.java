package com.nutrix.command.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecipeRepository extends JpaRepository<Recipe, String> {
}