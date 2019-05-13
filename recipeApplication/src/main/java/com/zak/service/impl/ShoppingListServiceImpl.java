package com.zak.service.impl;

import com.zak.service.ShoppingListService;
import com.zak.domain.ShoppingList;
import com.zak.repository.ShoppingListRepository;
import com.zak.service.dto.ShoppingListDTO;
import com.zak.service.mapper.ShoppingListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ShoppingList.
 */
@Service
@Transactional
public class ShoppingListServiceImpl implements ShoppingListService {

    private final Logger log = LoggerFactory.getLogger(ShoppingListServiceImpl.class);

    private final ShoppingListRepository shoppingListRepository;

    private final ShoppingListMapper shoppingListMapper;

    public ShoppingListServiceImpl(ShoppingListRepository shoppingListRepository, ShoppingListMapper shoppingListMapper) {
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingListMapper = shoppingListMapper;
    }

    /**
     * Save a shoppingList.
     *
     * @param shoppingListDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShoppingListDTO save(ShoppingListDTO shoppingListDTO) {
        log.debug("Request to save ShoppingList : {}", shoppingListDTO);
        ShoppingList shoppingList = shoppingListMapper.toEntity(shoppingListDTO);
        shoppingList = shoppingListRepository.save(shoppingList);
        return shoppingListMapper.toDto(shoppingList);
    }

    /**
     * Get all the shoppingLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShoppingListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShoppingLists");
        return shoppingListRepository.findAll(pageable)
            .map(shoppingListMapper::toDto);
    }

    /**
     * Get all the ShoppingList with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ShoppingListDTO> findAllWithEagerRelationships(Pageable pageable) {
        return shoppingListRepository.findAllWithEagerRelationships(pageable).map(shoppingListMapper::toDto);
    }
    

    /**
     * Get one shoppingList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingListDTO> findOne(Long id) {
        log.debug("Request to get ShoppingList : {}", id);
        return shoppingListRepository.findOneWithEagerRelationships(id)
            .map(shoppingListMapper::toDto);
    }

    /**
     * Delete the shoppingList by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingList : {}", id);        shoppingListRepository.deleteById(id);
    }
}
