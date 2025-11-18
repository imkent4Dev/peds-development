package com.lyhorng.pedssystem.controller.customer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lyhorng.common.response.ApiResponse;
import com.lyhorng.pedssystem.model.customer.Customer;
import com.lyhorng.pedssystem.service.customer.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get all customers
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Customer>>> listAll() {
        List<Customer> customerList = customerService.getAllCustomers();
        return ResponseEntity.ok(ApiResponse.success("Customers fetched successfully.", customerList));
    }

    // Create new customer
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Customer>> create(
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("nid") String nid) {
        try {
            Customer customer = customerService.createCustomer(firstName, lastName, nid);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Customer created successfully.", customer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create customer: " + e.getMessage(), null));
        }
    }

    // Update customer
    @PostMapping("/update")
    public ResponseEntity<ApiResponse<Customer>> update(
            @RequestParam("id") Long id,
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("nid") String nid) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, firstName, lastName, nid);
            return ResponseEntity.ok(ApiResponse.success("Customer updated successfully.", updatedCustomer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update customer: " + e.getMessage(), null));
        }
    }

    // View customer by ID
    @GetMapping("/view")
    public ResponseEntity<ApiResponse<Customer>> view(@RequestParam("id") Long id) {
        try {
            Customer customer = customerService.getCustomerById(id)
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + id));

            return ResponseEntity.ok(ApiResponse.success("Customer details fetched successfully.", customer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Customer not found: " + e.getMessage(), null));
        }
    }

    // Delete customer
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> delete(@RequestParam("id") Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ApiResponse.success("Customer deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Customer not found: " + e.getMessage(), null));
        }
    }
}
