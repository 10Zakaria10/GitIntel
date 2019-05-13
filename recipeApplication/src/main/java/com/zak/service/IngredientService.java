package com.zak.service;

import com.zak.service.dto.IngredientDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Ingredient.
 */
public interface IngredientService {

    /**
     * Save a ingredient.
     *
     * @param ingredientDTO the entity to save
     * @return the persisted entity
     */
    IngredientDTO save(IngredientDTO ingredientDTO);

    /**
     * Get all the ingredients.
     *
     * @return the list of entities
     */
    List<IngredientDTO> findAll();


    /**
     * Get the "id" ingredient.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IngredientDTO> findOne(Long id);

    /**
     * Delete the "id" ingredient.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
