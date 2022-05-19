package com.aws.salaunch.bradar.repository;

import com.aws.salaunch.bradar.domain.Customer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CustomerRepositoryWithBagRelationshipsImpl implements CustomerRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Customer> fetchBagRelationships(Optional<Customer> customer) {
        return customer.map(this::fetchAisleDiscounts);
    }

    @Override
    public Page<Customer> fetchBagRelationships(Page<Customer> customers) {
        return new PageImpl<>(fetchBagRelationships(customers.getContent()), customers.getPageable(), customers.getTotalElements());
    }

    @Override
    public List<Customer> fetchBagRelationships(List<Customer> customers) {
        return Optional.of(customers).map(this::fetchAisleDiscounts).orElse(Collections.emptyList());
    }

    Customer fetchAisleDiscounts(Customer result) {
        return entityManager
            .createQuery(
                "select customer from Customer customer left join fetch customer.aisleDiscounts where customer is :customer",
                Customer.class
            )
            .setParameter("customer", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Customer> fetchAisleDiscounts(List<Customer> customers) {
        return entityManager
            .createQuery(
                "select distinct customer from Customer customer left join fetch customer.aisleDiscounts where customer in :customers",
                Customer.class
            )
            .setParameter("customers", customers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
