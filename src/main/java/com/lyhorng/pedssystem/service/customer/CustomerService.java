package com.lyhorng.pedssystem.service.customer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyhorng.pedssystem.exception.BusinessException;
import com.lyhorng.pedssystem.exception.ResourceNotFoundException;
import com.lyhorng.pedssystem.model.customer.Customer;
import com.lyhorng.pedssystem.repository.customer.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // ✔ Create Customer
    @Transactional
    public Customer createCustomer(String firstName, String lastName, String fullName, String phoneNumber, String nid) {

        if (customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new BusinessException("Phone number already used by another customer.");
        }

        if (customerRepository.existsByNid(nid)) {
            throw new BusinessException("NID already used by another customer.");
        }

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setFullName(fullName);
        customer.setPhoneNumber(phoneNumber);
        customer.setNid(nid);

        return customerRepository.save(customer);
    }

    // ✔ Get all customers (No paging)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // ✔ Get by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    // ✔ Update Customer
    @Transactional
    public Customer updateCustomer(Long id, String firstName, String lastName, String fullName, String phoneNumber, String nid) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));

        // Phone duplication check
        if (phoneNumber != null && !phoneNumber.isEmpty() && !phoneNumber.equals(customer.getPhoneNumber())) {
            if (customerRepository.existsByPhoneNumber(phoneNumber)) {
                throw new BusinessException("Phone number already used by another customer.");
            }
            customer.setPhoneNumber(phoneNumber);
        }

        // NID duplication check
        if (nid != null && !nid.isEmpty() && !nid.equals(customer.getNid())) {
            if (customerRepository.existsByNid(nid)) {
                throw new BusinessException("NID already used by another customer.");
            }
            customer.setNid(nid);
        }

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setFullName(fullName);

        return customerRepository.save(customer);
    }

    // ✔ Delete Customer
    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
    }
}
