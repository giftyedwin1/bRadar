package com.aws.salaunch.bradar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aws.salaunch.bradar.IntegrationTest;
import com.aws.salaunch.bradar.domain.AisleDiscount;
import com.aws.salaunch.bradar.repository.AisleDiscountRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AisleDiscountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AisleDiscountResourceIT {

    private static final String DEFAULT_AISLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AISLE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aisle-discounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AisleDiscountRepository aisleDiscountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAisleDiscountMockMvc;

    private AisleDiscount aisleDiscount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AisleDiscount createEntity(EntityManager em) {
        AisleDiscount aisleDiscount = new AisleDiscount().aisleId(DEFAULT_AISLE_ID).product(DEFAULT_PRODUCT).discount(DEFAULT_DISCOUNT);
        return aisleDiscount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AisleDiscount createUpdatedEntity(EntityManager em) {
        AisleDiscount aisleDiscount = new AisleDiscount().aisleId(UPDATED_AISLE_ID).product(UPDATED_PRODUCT).discount(UPDATED_DISCOUNT);
        return aisleDiscount;
    }

    @BeforeEach
    public void initTest() {
        aisleDiscount = createEntity(em);
    }

    @Test
    @Transactional
    void createAisleDiscount() throws Exception {
        int databaseSizeBeforeCreate = aisleDiscountRepository.findAll().size();
        // Create the AisleDiscount
        restAisleDiscountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aisleDiscount)))
            .andExpect(status().isCreated());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeCreate + 1);
        AisleDiscount testAisleDiscount = aisleDiscountList.get(aisleDiscountList.size() - 1);
        assertThat(testAisleDiscount.getAisleId()).isEqualTo(DEFAULT_AISLE_ID);
        assertThat(testAisleDiscount.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testAisleDiscount.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void createAisleDiscountWithExistingId() throws Exception {
        // Create the AisleDiscount with an existing ID
        aisleDiscount.setId(1L);

        int databaseSizeBeforeCreate = aisleDiscountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAisleDiscountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aisleDiscount)))
            .andExpect(status().isBadRequest());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAisleDiscounts() throws Exception {
        // Initialize the database
        aisleDiscountRepository.saveAndFlush(aisleDiscount);

        // Get all the aisleDiscountList
        restAisleDiscountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aisleDiscount.getId().intValue())))
            .andExpect(jsonPath("$.[*].aisleId").value(hasItem(DEFAULT_AISLE_ID)))
            .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)));
    }

    @Test
    @Transactional
    void getAisleDiscount() throws Exception {
        // Initialize the database
        aisleDiscountRepository.saveAndFlush(aisleDiscount);

        // Get the aisleDiscount
        restAisleDiscountMockMvc
            .perform(get(ENTITY_API_URL_ID, aisleDiscount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aisleDiscount.getId().intValue()))
            .andExpect(jsonPath("$.aisleId").value(DEFAULT_AISLE_ID))
            .andExpect(jsonPath("$.product").value(DEFAULT_PRODUCT))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT));
    }

    @Test
    @Transactional
    void getNonExistingAisleDiscount() throws Exception {
        // Get the aisleDiscount
        restAisleDiscountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAisleDiscount() throws Exception {
        // Initialize the database
        aisleDiscountRepository.saveAndFlush(aisleDiscount);

        int databaseSizeBeforeUpdate = aisleDiscountRepository.findAll().size();

        // Update the aisleDiscount
        AisleDiscount updatedAisleDiscount = aisleDiscountRepository.findById(aisleDiscount.getId()).get();
        // Disconnect from session so that the updates on updatedAisleDiscount are not directly saved in db
        em.detach(updatedAisleDiscount);
        updatedAisleDiscount.aisleId(UPDATED_AISLE_ID).product(UPDATED_PRODUCT).discount(UPDATED_DISCOUNT);

        restAisleDiscountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAisleDiscount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAisleDiscount))
            )
            .andExpect(status().isOk());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeUpdate);
        AisleDiscount testAisleDiscount = aisleDiscountList.get(aisleDiscountList.size() - 1);
        assertThat(testAisleDiscount.getAisleId()).isEqualTo(UPDATED_AISLE_ID);
        assertThat(testAisleDiscount.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testAisleDiscount.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void putNonExistingAisleDiscount() throws Exception {
        int databaseSizeBeforeUpdate = aisleDiscountRepository.findAll().size();
        aisleDiscount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAisleDiscountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aisleDiscount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aisleDiscount))
            )
            .andExpect(status().isBadRequest());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAisleDiscount() throws Exception {
        int databaseSizeBeforeUpdate = aisleDiscountRepository.findAll().size();
        aisleDiscount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAisleDiscountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aisleDiscount))
            )
            .andExpect(status().isBadRequest());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAisleDiscount() throws Exception {
        int databaseSizeBeforeUpdate = aisleDiscountRepository.findAll().size();
        aisleDiscount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAisleDiscountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aisleDiscount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAisleDiscountWithPatch() throws Exception {
        // Initialize the database
        aisleDiscountRepository.saveAndFlush(aisleDiscount);

        int databaseSizeBeforeUpdate = aisleDiscountRepository.findAll().size();

        // Update the aisleDiscount using partial update
        AisleDiscount partialUpdatedAisleDiscount = new AisleDiscount();
        partialUpdatedAisleDiscount.setId(aisleDiscount.getId());

        partialUpdatedAisleDiscount.aisleId(UPDATED_AISLE_ID).discount(UPDATED_DISCOUNT);

        restAisleDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAisleDiscount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAisleDiscount))
            )
            .andExpect(status().isOk());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeUpdate);
        AisleDiscount testAisleDiscount = aisleDiscountList.get(aisleDiscountList.size() - 1);
        assertThat(testAisleDiscount.getAisleId()).isEqualTo(UPDATED_AISLE_ID);
        assertThat(testAisleDiscount.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testAisleDiscount.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void fullUpdateAisleDiscountWithPatch() throws Exception {
        // Initialize the database
        aisleDiscountRepository.saveAndFlush(aisleDiscount);

        int databaseSizeBeforeUpdate = aisleDiscountRepository.findAll().size();

        // Update the aisleDiscount using partial update
        AisleDiscount partialUpdatedAisleDiscount = new AisleDiscount();
        partialUpdatedAisleDiscount.setId(aisleDiscount.getId());

        partialUpdatedAisleDiscount.aisleId(UPDATED_AISLE_ID).product(UPDATED_PRODUCT).discount(UPDATED_DISCOUNT);

        restAisleDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAisleDiscount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAisleDiscount))
            )
            .andExpect(status().isOk());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeUpdate);
        AisleDiscount testAisleDiscount = aisleDiscountList.get(aisleDiscountList.size() - 1);
        assertThat(testAisleDiscount.getAisleId()).isEqualTo(UPDATED_AISLE_ID);
        assertThat(testAisleDiscount.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testAisleDiscount.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingAisleDiscount() throws Exception {
        int databaseSizeBeforeUpdate = aisleDiscountRepository.findAll().size();
        aisleDiscount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAisleDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aisleDiscount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aisleDiscount))
            )
            .andExpect(status().isBadRequest());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAisleDiscount() throws Exception {
        int databaseSizeBeforeUpdate = aisleDiscountRepository.findAll().size();
        aisleDiscount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAisleDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aisleDiscount))
            )
            .andExpect(status().isBadRequest());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAisleDiscount() throws Exception {
        int databaseSizeBeforeUpdate = aisleDiscountRepository.findAll().size();
        aisleDiscount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAisleDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(aisleDiscount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AisleDiscount in the database
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAisleDiscount() throws Exception {
        // Initialize the database
        aisleDiscountRepository.saveAndFlush(aisleDiscount);

        int databaseSizeBeforeDelete = aisleDiscountRepository.findAll().size();

        // Delete the aisleDiscount
        restAisleDiscountMockMvc
            .perform(delete(ENTITY_API_URL_ID, aisleDiscount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AisleDiscount> aisleDiscountList = aisleDiscountRepository.findAll();
        assertThat(aisleDiscountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
