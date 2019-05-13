package com.zak.web.rest;
import com.zak.service.ShoppingListService;
import com.zak.web.rest.errors.BadRequestAlertException;
import com.zak.web.rest.util.HeaderUtil;
import com.zak.web.rest.util.PaginationUtil;
import com.zak.service.dto.ShoppingListDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ShoppingList.
 */
@RestController
@RequestMapping("/api")
public class ShoppingListResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingListResource.class);

    private static final String ENTITY_NAME = "shoppingList";

    private final ShoppingListService shoppingListService;

    public ShoppingListResource(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    /**
     * POST  /shopping-lists : Create a new shoppingList.
     *
     * @param shoppingListDTO the shoppingListDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shoppingListDTO, or with status 400 (Bad Request) if the shoppingList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shopping-lists")
    public ResponseEntity<ShoppingListDTO> createShoppingList(@RequestBody ShoppingListDTO shoppingListDTO) throws URISyntaxException {
        log.debug("REST request to save ShoppingList : {}", shoppingListDTO);
        if (shoppingListDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoppingList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingListDTO result = shoppingListService.save(shoppingListDTO);
        return ResponseEntity.created(new URI("/api/shopping-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shopping-lists : Updates an existing shoppingList.
     *
     * @param shoppingListDTO the shoppingListDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shoppingListDTO,
     * or with status 400 (Bad Request) if the shoppingListDTO is not valid,
     * or with status 500 (Internal Server Error) if the shoppingListDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shopping-lists")
    public ResponseEntity<ShoppingListDTO> updateShoppingList(@RequestBody ShoppingListDTO shoppingListDTO) throws URISyntaxException {
        log.debug("REST request to update ShoppingList : {}", shoppingListDTO);
        if (shoppingListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShoppingListDTO result = shoppingListService.save(shoppingListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shoppingListDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shopping-lists : get all the shoppingLists.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of shoppingLists in body
     */
    @GetMapping("/shopping-lists")
    public ResponseEntity<List<ShoppingListDTO>> getAllShoppingLists(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ShoppingLists");
        Page<ShoppingListDTO> page;
        if (eagerload) {
            page = shoppingListService.findAllWithEagerRelationships(pageable);
        } else {
            page = shoppingListService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/shopping-lists?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /shopping-lists/:id : get the "id" shoppingList.
     *
     * @param id the id of the shoppingListDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shoppingListDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shopping-lists/{id}")
    public ResponseEntity<ShoppingListDTO> getShoppingList(@PathVariable Long id) {
        log.debug("REST request to get ShoppingList : {}", id);
        Optional<ShoppingListDTO> shoppingListDTO = shoppingListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingListDTO);
    }

    /**
     * DELETE  /shopping-lists/:id : delete the "id" shoppingList.
     *
     * @param id the id of the shoppingListDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shopping-lists/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingList : {}", id);
        shoppingListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
