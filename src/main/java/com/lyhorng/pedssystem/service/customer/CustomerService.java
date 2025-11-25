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

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public Customer updateCustomer(Long id, String firstName, String lastName, String fullName, String phoneNumber, String nid) {
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
