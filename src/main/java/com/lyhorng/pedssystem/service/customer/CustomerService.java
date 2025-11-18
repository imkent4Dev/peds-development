package com.lyhorng.pedssystem.service.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyhorng.pedssystem.model.customer.Customer;
import com.lyhorng.pedssystem.repository.customer.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // Create new customer with parameters (firstName, lastName, nid)
    @Transactional
    public Customer createCustomer(String firstName, String lastName, String nid) {
        // Add validation logic if needed
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setNid(nid);
        return customerRepository.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Update customer with parameters (firstName, lastName, nid)
    @Transactional
    public Customer updateCustomer(Long id, String firstName, String lastName, String nid) {
        Customer updatedCustomer = customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(firstName);
                    customer.setLastName(lastName);
                    customer.setNid(nid); // Update the nid as well
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
        return updatedCustomer;
    }

    // Delete customer by ID
    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
    }
}
