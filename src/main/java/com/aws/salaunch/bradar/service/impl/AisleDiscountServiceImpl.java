package com.aws.salaunch.bradar.service.impl;

import com.aws.salaunch.bradar.domain.AisleDiscount;
import com.aws.salaunch.bradar.repository.AisleDiscountRepository;
import com.aws.salaunch.bradar.service.AisleDiscountService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AisleDiscount}.
 */
@Service
@Transactional
public class AisleDiscountServiceImpl implements AisleDiscountService {

    private final Logger log = LoggerFactory.getLogger(AisleDiscountServiceImpl.class);

    private final AisleDiscountRepository aisleDiscountRepository;

    public AisleDiscountServiceImpl(AisleDiscountRepository aisleDiscountRepository) {
        this.aisleDiscountRepository = aisleDiscountRepository;
    }

    @Override
    public AisleDiscount save(AisleDiscount aisleDiscount) {
        log.debug("Request to save AisleDiscount : {}", aisleDiscount);
        return aisleDiscountRepository.save(aisleDiscount);
    }

    @Override
    public AisleDiscount update(AisleDiscount aisleDiscount) {
        log.debug("Request to save AisleDiscount : {}", aisleDiscount);
        return aisleDiscountRepository.save(aisleDiscount);
    }

    @Override
    public Optional<AisleDiscount> partialUpdate(AisleDiscount aisleDiscount) {
        log.debug("Request to partially update AisleDiscount : {}", aisleDiscount);

        return aisleDiscountRepository
            .findById(aisleDiscount.getId())
            .map(existingAisleDiscount -> {
                if (aisleDiscount.getAisleId() != null) {
                    existingAisleDiscount.setAisleId(aisleDiscount.getAisleId());
                }
                if (aisleDiscount.getProduct() != null) {
                    existingAisleDiscount.setProduct(aisleDiscount.getProduct());
                }
                if (aisleDiscount.getDiscount() != null) {
                    existingAisleDiscount.setDiscount(aisleDiscount.getDiscount());
                }

                return existingAisleDiscount;
            })
            .map(aisleDiscountRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AisleDiscount> findAll() {
        log.debug("Request to get all AisleDiscounts");
        return aisleDiscountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AisleDiscount> findOne(Long id) {
        log.debug("Request to get AisleDiscount : {}", id);
        return aisleDiscountRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AisleDiscount : {}", id);
        aisleDiscountRepository.deleteById(id);
    }
}
