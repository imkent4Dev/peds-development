package com.lyhorng.pedssystem.controller.customer;

import com.lyhorng.pedssystem.common.ApiResponse;
import com.lyhorng.pedssystem.model.customer.Customer;
import com.lyhorng.pedssystem.service.customer.CustomerService;
import com.lyhorng.pedssystem.dto.customer.CustomerCreateRequest;
import com.lyhorng.pedssystem.dto.customer.CustomerUpdateRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // ✔ Return LIST without paging
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Customer>>> listAll() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(
                ApiResponse.success("CUS-200", "Customer list fetched", "ទាញយកបញ្ជីអតិថិជនបានជោគជ័យ", customers));
    }

    //  CREATE WITH DTO
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Customer>> create(@RequestBody CustomerCreateRequest request) {
        Customer customer = customerService.createCustomer(
                request.getFirstName(),
                request.getLastName(),
                request.getFullName(),
                request.getPhoneNumber(),
                request.getNid());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "CUS-201",
                        "Customer created successfully",
                        "បង្កើតអតិថិជនបានជោគជ័យ",
                        customer));
    }

    //  UPDATE WITH DTO
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Customer>> update(
            @PathVariable Long id,
            @RequestBody CustomerUpdateRequest request) {
        Customer updated = customerService.updateCustomer(
                id,
                request.getFirstName(),
                request.getLastName(),
                request.getFullName(),
                request.getPhoneNumber(),
                request.getNid());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "CUS-200",
                        "Customer updated successfully",
                        "កែប្រែអតិថិជនបានជោគជ័យ",
                        updated));
    }

    //  GET BY ID
    @GetMapping("/view/{id}")
    public ResponseEntity<ApiResponse<Customer>> view(@PathVariable Long id) {
        // The service already throws ResourceNotFoundException if not found
        Customer customer = customerService.getCustomerById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "CUS-200",
                        "Customer fetched successfully",
                        "ទាញយកព័ត៌មានអតិថិជនបានជោគជ័យ",
                        customer));
    }

    //  DELETE BY ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "CUS-200",
                        "Customer deleted successfully",
                        "លុបអតិថិជនបានជោគជ័យ",
                        null));
    }
}
