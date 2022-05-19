package com.aws.salaunch.bradar.web.rest;

import com.aws.salaunch.bradar.domain.AisleDiscount;
import com.aws.salaunch.bradar.repository.AisleDiscountRepository;
import com.aws.salaunch.bradar.service.AisleDiscountService;
import com.aws.salaunch.bradar.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aws.salaunch.bradar.domain.AisleDiscount}.
 */
@RestController
@RequestMapping("/api")
public class AisleDiscountResource {

    private final Logger log = LoggerFactory.getLogger(AisleDiscountResource.class);

    private static final String ENTITY_NAME = "aisleDiscount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AisleDiscountService aisleDiscountService;

    private final AisleDiscountRepository aisleDiscountRepository;

    public AisleDiscountResource(AisleDiscountService aisleDiscountService, AisleDiscountRepository aisleDiscountRepository) {
        this.aisleDiscountService = aisleDiscountService;
        this.aisleDiscountRepository = aisleDiscountRepository;
    }

    /**
     * {@code POST  /aisle-discounts} : Create a new aisleDiscount.
     *
     * @param aisleDiscount the aisleDiscount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aisleDiscount, or with status {@code 400 (Bad Request)} if the aisleDiscount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aisle-discounts")
    public ResponseEntity<AisleDiscount> createAisleDiscount(@RequestBody AisleDiscount aisleDiscount) throws URISyntaxException {
        log.debug("REST request to save AisleDiscount : {}", aisleDiscount);
        if (aisleDiscount.getId() != null) {
            throw new BadRequestAlertException("A new aisleDiscount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AisleDiscount result = aisleDiscountService.save(aisleDiscount);
        return ResponseEntity
            .created(new URI("/api/aisle-discounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aisle-discounts/:id} : Updates an existing aisleDiscount.
     *
     * @param id the id of the aisleDiscount to save.
     * @param aisleDiscount the aisleDiscount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aisleDiscount,
     * or with status {@code 400 (Bad Request)} if the aisleDiscount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aisleDiscount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aisle-discounts/{id}")
    public ResponseEntity<AisleDiscount> updateAisleDiscount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AisleDiscount aisleDiscount
    ) throws URISyntaxException {
        log.debug("REST request to update AisleDiscount : {}, {}", id, aisleDiscount);
        if (aisleDiscount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aisleDiscount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aisleDiscountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AisleDiscount result = aisleDiscountService.update(aisleDiscount);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aisleDiscount.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aisle-discounts/:id} : Partial updates given fields of an existing aisleDiscount, field will ignore if it is null
     *
     * @param id the id of the aisleDiscount to save.
     * @param aisleDiscount the aisleDiscount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aisleDiscount,
     * or with status {@code 400 (Bad Request)} if the aisleDiscount is not valid,
     * or with status {@code 404 (Not Found)} if the aisleDiscount is not found,
     * or with status {@code 500 (Internal Server Error)} if the aisleDiscount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/aisle-discounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AisleDiscount> partialUpdateAisleDiscount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AisleDiscount aisleDiscount
    ) throws URISyntaxException {
        log.debug("REST request to partial update AisleDiscount partially : {}, {}", id, aisleDiscount);
        if (aisleDiscount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aisleDiscount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aisleDiscountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AisleDiscount> result = aisleDiscountService.partialUpdate(aisleDiscount);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aisleDiscount.getId().toString())
        );
    }

    /**
     * {@code GET  /aisle-discounts} : get all the aisleDiscounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aisleDiscounts in body.
     */
    @GetMapping("/aisle-discounts")
    public List<AisleDiscount> getAllAisleDiscounts() {
        log.debug("REST request to get all AisleDiscounts");
        return aisleDiscountService.findAll();
    }

    /**
     * {@code GET  /aisle-discounts/:id} : get the "id" aisleDiscount.
     *
     * @param id the id of the aisleDiscount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aisleDiscount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aisle-discounts/{id}")
    public ResponseEntity<AisleDiscount> getAisleDiscount(@PathVariable Long id) {
        log.debug("REST request to get AisleDiscount : {}", id);
        Optional<AisleDiscount> aisleDiscount = aisleDiscountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aisleDiscount);
    }

    /**
     * {@code DELETE  /aisle-discounts/:id} : delete the "id" aisleDiscount.
     *
     * @param id the id of the aisleDiscount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aisle-discounts/{id}")
    public ResponseEntity<Void> deleteAisleDiscount(@PathVariable Long id) {
        log.debug("REST request to delete AisleDiscount : {}", id);
        aisleDiscountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
