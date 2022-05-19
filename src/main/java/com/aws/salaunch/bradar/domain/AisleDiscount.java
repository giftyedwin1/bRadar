package com.aws.salaunch.bradar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AisleDiscount.
 */
@Entity
@Table(name = "aisle_discount")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AisleDiscount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aisle_id")
    private String aisleId;

    @Column(name = "product")
    private String product;

    @Column(name = "discount")
    private String discount;

    @ManyToMany(mappedBy = "aisleDiscounts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aisleDiscounts" }, allowSetters = true)
    private Set<Customer> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AisleDiscount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAisleId() {
        return this.aisleId;
    }

    public AisleDiscount aisleId(String aisleId) {
        this.setAisleId(aisleId);
        return this;
    }

    public void setAisleId(String aisleId) {
        this.aisleId = aisleId;
    }

    public String getProduct() {
        return this.product;
    }

    public AisleDiscount product(String product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDiscount() {
        return this.discount;
    }

    public AisleDiscount discount(String discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customer> customers) {
        if (this.customers != null) {
            this.customers.forEach(i -> i.removeAisleDiscount(this));
        }
        if (customers != null) {
            customers.forEach(i -> i.addAisleDiscount(this));
        }
        this.customers = customers;
    }

    public AisleDiscount customers(Set<Customer> customers) {
        this.setCustomers(customers);
        return this;
    }

    public AisleDiscount addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.getAisleDiscounts().add(this);
        return this;
    }

    public AisleDiscount removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.getAisleDiscounts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AisleDiscount)) {
            return false;
        }
        return id != null && id.equals(((AisleDiscount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AisleDiscount{" +
            "id=" + getId() +
            ", aisleId='" + getAisleId() + "'" +
            ", product='" + getProduct() + "'" +
            ", discount='" + getDiscount() + "'" +
            "}";
    }
}
