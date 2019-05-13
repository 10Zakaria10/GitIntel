package com.zak.service.mapper;

import com.zak.domain.*;
import com.zak.service.dto.ShoppingListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShoppingList and its DTO ShoppingListDTO.
 */
@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface ShoppingListMapper extends EntityMapper<ShoppingListDTO, ShoppingList> {



    default ShoppingList fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(id);
        return shoppingList;
    }
}
