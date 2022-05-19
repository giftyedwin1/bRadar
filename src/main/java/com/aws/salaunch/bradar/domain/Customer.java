package com.aws.salaunch.bradar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "age")
    private String age;

    @Column(name = "gender")
    private String gender;

    @ManyToMany
    @JoinTable(
        name = "rel_customer__aisle_discount",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "aisle_discount_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customers" }, allowSetters = true)
    private Set<AisleDiscount> aisleDiscounts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public Customer customerId(String customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAge() {
        return this.age;
    }

    public Customer age(String age) {
        this.setAge(age);
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return this.gender;
    }

    public Customer gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<AisleDiscount> getAisleDiscounts() {
        return this.aisleDiscounts;
    }

    public void setAisleDiscounts(Set<AisleDiscount> aisleDiscounts) {
        this.aisleDiscounts = aisleDiscounts;
    }

    public Customer aisleDiscounts(Set<AisleDiscount> aisleDiscounts) {
        this.setAisleDiscounts(aisleDiscounts);
        return this;
    }

    public Customer addAisleDiscount(AisleDiscount aisleDiscount) {
        this.aisleDiscounts.add(aisleDiscount);
        aisleDiscount.getCustomers().add(this);
        return this;
    }

    public Customer removeAisleDiscount(AisleDiscount aisleDiscount) {
        this.aisleDiscounts.remove(aisleDiscount);
        aisleDiscount.getCustomers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerId='" + getCustomerId() + "'" +
            ", age='" + getAge() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
