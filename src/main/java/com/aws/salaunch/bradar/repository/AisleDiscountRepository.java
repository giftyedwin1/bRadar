package com.aws.salaunch.bradar.repository;

import com.aws.salaunch.bradar.domain.AisleDiscount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AisleDiscount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AisleDiscountRepository extends JpaRepository<AisleDiscount, Long> {}
