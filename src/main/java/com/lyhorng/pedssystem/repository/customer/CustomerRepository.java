package com.lyhorng.pedssystem.repository.customer;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    Optional<Customer> findByFirstName(String firstName);

    Optional<Customer> findByLastName(String lastName);

    boolean existsByNid(String nid);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.nid) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Customer> searchCustomers(@Param("search") String search, Pageable pageable);
}