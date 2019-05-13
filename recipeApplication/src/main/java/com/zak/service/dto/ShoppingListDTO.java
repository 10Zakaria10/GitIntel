package com.zak.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ShoppingList entity.
 */
public class ShoppingListDTO implements Serializable {

    private Long id;


    private Set<IngredientDTO> ingredients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingListDTO shoppingListDTO = (ShoppingListDTO) o;
        if (shoppingListDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingListDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingListDTO{" +
            "id=" + getId() +
            "}";
    }
}
