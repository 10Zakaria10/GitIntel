package com.zak.service;

import com.zak.service.dto.ShoppingListDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ShoppingList.
 */
public interface ShoppingListService {

    /**
     * Save a shoppingList.
     *
     * @param shoppingListDTO the entity to save
     * @return the persisted entity
     */
    ShoppingListDTO save(ShoppingListDTO shoppingListDTO);

    /**
     * Get all the shoppingLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShoppingListDTO> findAll(Pageable pageable);

    /**
     * Get all the ShoppingList with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ShoppingListDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" shoppingList.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShoppingListDTO> findOne(Long id);

    /**
     * Delete the "id" shoppingList.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
