package com.aws.salaunch.bradar.service;

import com.aws.salaunch.bradar.domain.AisleDiscount;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link AisleDiscount}.
 */
public interface AisleDiscountService {
    /**
     * Save a aisleDiscount.
     *
     * @param aisleDiscount the entity to save.
     * @return the persisted entity.
     */
    AisleDiscount save(AisleDiscount aisleDiscount);

    /**
     * Updates a aisleDiscount.
     *
     * @param aisleDiscount the entity to update.
     * @return the persisted entity.
     */
    AisleDiscount update(AisleDiscount aisleDiscount);

    /**
     * Partially updates a aisleDiscount.
     *
     * @param aisleDiscount the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AisleDiscount> partialUpdate(AisleDiscount aisleDiscount);

    /**
     * Get all the aisleDiscounts.
     *
     * @return the list of entities.
     */
    List<AisleDiscount> findAll();

    /**
     * Get the "id" aisleDiscount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AisleDiscount> findOne(Long id);

    /**
     * Delete the "id" aisleDiscount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
