package com.aws.salaunch.bradar.service.impl;

import com.aws.salaunch.bradar.domain.Customer;
import com.aws.salaunch.bradar.repository.CustomerRepository;
import com.aws.salaunch.bradar.service.CustomerService;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service

public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    
    @PersistenceContext
    private EntityManager manager;
     
    @Override
    @Transactional
    public Customer updateCustomerMatch ( String customerId ) 
    {
      try
      {
    
    	  Query jpqlQuery = manager.createQuery("UPDATE customer SET matched = ? WHERE customer_id = ?");
    	  jpqlQuery.setParameter("matched", true);
    	  return  (Customer) jpqlQuery.getSingleResult();
    	  
      }
      catch (Exception e)
      {
        return null;
      }
    }
    

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Optional<Customer> partialUpdate(Customer customer) {
        log.debug("Request to partially update Customer : {}", customer);

        return customerRepository
            .findById(customer.getId())
            .map(existingCustomer -> {
                if (customer.getCustomerId() != null) {
                    existingCustomer.setCustomerId(customer.getCustomerId());
                }
                if (customer.getAge() != null) {
                    existingCustomer.setAge(customer.getAge());
                }
                if (customer.getGender() != null) {
                    existingCustomer.setGender(customer.getGender());
                }
                if (customer.getMatched() != null) {
                    existingCustomer.setMatched(customer.getMatched());
                }

                return existingCustomer;
            })
            .map(customerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        log.debug("Request to get all Customers");
        return customerRepository.findAllWithEagerRelationships();
    }

    public Page<Customer> findAllWithEagerRelationships(Pageable pageable) {
        return customerRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }


	@Override
	public Customer findByCustomerId(String customerId) {
		return customerRepository.findByCustomerId(customerId);		
	}
    
    
}
