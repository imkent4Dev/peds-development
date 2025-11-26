package com.lyhorng.pedssystem.service.customer;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyhorng.pedssystem.model.customer.Customer;
import com.lyhorng.pedssystem.repository.customer.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String firstName, String lastName, String fullName, String phoneNumber, String nid) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setFullName(fullName);
        customer.setPhoneNumber(phoneNumber);
        customer.setNid(nid);
        return customerRepository.save(customer);
    }

    public Page<Customer> getAllCustomers(int page, int size, String sortBy, String sortDir, String search) {
        // Convert 1-based page to 0-based for Spring Data
        int pageIndex = page > 0 ? page - 1 : 0;

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageIndex, size, sort);

        if (search != null && !search.trim().isEmpty()) {
            return customerRepository.searchCustomers(search.trim(), pageable);
        }

        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public Customer updateCustomer(Long id, String firstName, String lastName, String fullName, String phoneNumber,
            String nid) {
        if (nid != null && !nid.isEmpty()) {
            boolean isNidExist = customerRepository.existsByNid(nid);
            if (isNidExist) {
                throw new RuntimeException("The provided NID is already in use by another customer.");
            }
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            boolean isPhoneExist = customerRepository.existsByPhoneNumber(phoneNumber);
            if (isPhoneExist) {
                throw new RuntimeException("The provided phone number is already in use by another customer.");
            }
        }

        Customer updatedCustomer = customerRepository.findById(id)
                .map(customer -> {
                    if (nid != null && !nid.isEmpty()) {
                        customer.setNid(nid);
                    }
                    customer.setFirstName(firstName);
                    customer.setLastName(lastName);
                    customer.setFullName(fullName);
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));

        return updatedCustomer;
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
    }
}
