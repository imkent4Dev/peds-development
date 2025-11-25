package com.lyhorng.pedssystem.repository.customer;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    Optional<Customer> findByFirstName(String firstName);

    Optional<Customer> findByLastName(String lastName);

    boolean existsByNid(String nid);

    boolean existsByPhoneNumber(String phoneNumber);

}