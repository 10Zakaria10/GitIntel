package com.zak.web.rest;

import com.zak.RecipeApplicationApp;

import com.zak.domain.ShoppingList;
import com.zak.repository.ShoppingListRepository;
import com.zak.service.ShoppingListService;
import com.zak.service.dto.ShoppingListDTO;
import com.zak.service.mapper.ShoppingListMapper;
import com.zak.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.zak.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShoppingListResource REST controller.
 *
 * @see ShoppingListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecipeApplicationApp.class)
public class ShoppingListResourceIntTest {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Mock
    private ShoppingListRepository shoppingListRepositoryMock;

    @Autowired
    private ShoppingListMapper shoppingListMapper;

    @Mock
    private ShoppingListService shoppingListServiceMock;

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restShoppingListMockMvc;

    private ShoppingList shoppingList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingListResource shoppingListResource = new ShoppingListResource(shoppingListService);
        this.restShoppingListMockMvc = MockMvcBuilders.standaloneSetup(shoppingListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingList createEntity(EntityManager em) {
        ShoppingList shoppingList = new ShoppingList();
        return shoppingList;
    }

    @Before
    public void initTest() {
        shoppingList = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingList() throws Exception {
        int databaseSizeBeforeCreate = shoppingListRepository.findAll().size();

        // Create the ShoppingList
        ShoppingListDTO shoppingListDTO = shoppingListMapper.toDto(shoppingList);
        restShoppingListMockMvc.perform(post("/api/shopping-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingListDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingList in the database
        List<ShoppingList> shoppingListList = shoppingListRepository.findAll();
        assertThat(shoppingListList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingList testShoppingList = shoppingListList.get(shoppingListList.size() - 1);
    }

    @Test
    @Transactional
    public void createShoppingListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingListRepository.findAll().size();

        // Create the ShoppingList with an existing ID
        shoppingList.setId(1L);
        ShoppingListDTO shoppingListDTO = shoppingListMapper.toDto(shoppingList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingListMockMvc.perform(post("/api/shopping-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingList in the database
        List<ShoppingList> shoppingListList = shoppingListRepository.findAll();
        assertThat(shoppingListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShoppingLists() throws Exception {
        // Initialize the database
        shoppingListRepository.saveAndFlush(shoppingList);

        // Get all the shoppingListList
        restShoppingListMockMvc.perform(get("/api/shopping-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingList.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllShoppingListsWithEagerRelationshipsIsEnabled() throws Exception {
        ShoppingListResource shoppingListResource = new ShoppingListResource(shoppingListServiceMock);
        when(shoppingListServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restShoppingListMockMvc = MockMvcBuilders.standaloneSetup(shoppingListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restShoppingListMockMvc.perform(get("/api/shopping-lists?eagerload=true"))
        .andExpect(status().isOk());

        verify(shoppingListServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllShoppingListsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ShoppingListResource shoppingListResource = new ShoppingListResource(shoppingListServiceMock);
            when(shoppingListServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restShoppingListMockMvc = MockMvcBuilders.standaloneSetup(shoppingListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restShoppingListMockMvc.perform(get("/api/shopping-lists?eagerload=true"))
        .andExpect(status().isOk());

            verify(shoppingListServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getShoppingList() throws Exception {
        // Initialize the database
        shoppingListRepository.saveAndFlush(shoppingList);

        // Get the shoppingList
        restShoppingListMockMvc.perform(get("/api/shopping-lists/{id}", shoppingList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingList.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingList() throws Exception {
        // Get the shoppingList
        restShoppingListMockMvc.perform(get("/api/shopping-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingList() throws Exception {
        // Initialize the database
        shoppingListRepository.saveAndFlush(shoppingList);

        int databaseSizeBeforeUpdate = shoppingListRepository.findAll().size();

        // Update the shoppingList
        ShoppingList updatedShoppingList = shoppingListRepository.findById(shoppingList.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingList are not directly saved in db
        em.detach(updatedShoppingList);
        ShoppingListDTO shoppingListDTO = shoppingListMapper.toDto(updatedShoppingList);

        restShoppingListMockMvc.perform(put("/api/shopping-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingListDTO)))
            .andExpect(status().isOk());

        // Validate the ShoppingList in the database
        List<ShoppingList> shoppingListList = shoppingListRepository.findAll();
        assertThat(shoppingListList).hasSize(databaseSizeBeforeUpdate);
        ShoppingList testShoppingList = shoppingListList.get(shoppingListList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingList() throws Exception {
        int databaseSizeBeforeUpdate = shoppingListRepository.findAll().size();

        // Create the ShoppingList
        ShoppingListDTO shoppingListDTO = shoppingListMapper.toDto(shoppingList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingListMockMvc.perform(put("/api/shopping-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingList in the database
        List<ShoppingList> shoppingListList = shoppingListRepository.findAll();
        assertThat(shoppingListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShoppingList() throws Exception {
        // Initialize the database
        shoppingListRepository.saveAndFlush(shoppingList);

        int databaseSizeBeforeDelete = shoppingListRepository.findAll().size();

        // Delete the shoppingList
        restShoppingListMockMvc.perform(delete("/api/shopping-lists/{id}", shoppingList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShoppingList> shoppingListList = shoppingListRepository.findAll();
        assertThat(shoppingListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingList.class);
        ShoppingList shoppingList1 = new ShoppingList();
        shoppingList1.setId(1L);
        ShoppingList shoppingList2 = new ShoppingList();
        shoppingList2.setId(shoppingList1.getId());
        assertThat(shoppingList1).isEqualTo(shoppingList2);
        shoppingList2.setId(2L);
        assertThat(shoppingList1).isNotEqualTo(shoppingList2);
        shoppingList1.setId(null);
        assertThat(shoppingList1).isNotEqualTo(shoppingList2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingListDTO.class);
        ShoppingListDTO shoppingListDTO1 = new ShoppingListDTO();
        shoppingListDTO1.setId(1L);
        ShoppingListDTO shoppingListDTO2 = new ShoppingListDTO();
        assertThat(shoppingListDTO1).isNotEqualTo(shoppingListDTO2);
        shoppingListDTO2.setId(shoppingListDTO1.getId());
        assertThat(shoppingListDTO1).isEqualTo(shoppingListDTO2);
        shoppingListDTO2.setId(2L);
        assertThat(shoppingListDTO1).isNotEqualTo(shoppingListDTO2);
        shoppingListDTO1.setId(null);
        assertThat(shoppingListDTO1).isNotEqualTo(shoppingListDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shoppingListMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shoppingListMapper.fromId(null)).isNull();
    }
}
