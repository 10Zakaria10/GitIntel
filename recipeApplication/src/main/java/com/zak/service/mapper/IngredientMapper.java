package com.zak.service.mapper;

import com.zak.domain.*;
import com.zak.service.dto.IngredientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ingredient and its DTO IngredientDTO.
 */
@Mapper(componentModel = "spring", uses = {RecipeMapper.class})
public interface IngredientMapper extends EntityMapper<IngredientDTO, Ingredient> {

    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "recipe.id", target = "recipeId")
    IngredientDTO toDto(Ingredient ingredient);

    @Mapping(source = "recipeId", target = "recipe")
    @Mapping(target = "shoppinglists", ignore = true)
    @Mapping(source = "recipeId", target = "recipe")
    Ingredient toEntity(IngredientDTO ingredientDTO);

    default Ingredient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        return ingredient;
    }
}
