package com.zak.service;

import com.zak.service.dto.RecipeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Recipe.
 */
public interface RecipeService {

    /**
     * Save a recipe.
     *
     * @param recipeDTO the entity to save
     * @return the persisted entity
     */
    RecipeDTO save(RecipeDTO recipeDTO);

    /**
     * Get all the recipes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecipeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" recipe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RecipeDTO> findOne(Long id);

    /**
     * Delete the "id" recipe.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
