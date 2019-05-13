package com.zak.repository;

import com.zak.domain.ShoppingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ShoppingList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    @Query(value = "select distinct shopping_list from ShoppingList shopping_list left join fetch shopping_list.ingredients",
        countQuery = "select count(distinct shopping_list) from ShoppingList shopping_list")
    Page<ShoppingList> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct shopping_list from ShoppingList shopping_list left join fetch shopping_list.ingredients")
    List<ShoppingList> findAllWithEagerRelationships();

    @Query("select shopping_list from ShoppingList shopping_list left join fetch shopping_list.ingredients where shopping_list.id =:id")
    Optional<ShoppingList> findOneWithEagerRelationships(@Param("id") Long id);

}
