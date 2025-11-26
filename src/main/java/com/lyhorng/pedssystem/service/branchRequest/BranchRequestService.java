package com.lyhorng.pedssystem.service.branchRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyhorng.pedssystem.dto.BranchRequest.BranchRequestDTO;
import com.lyhorng.pedssystem.dto.BranchRequest.CoBorrowerDTO;
import com.lyhorng.pedssystem.model.branchRequest.Branch;
import com.lyhorng.pedssystem.model.branchRequest.BranchRequest;
import com.lyhorng.pedssystem.model.branchRequest.Currency;
import com.lyhorng.pedssystem.model.branchRequest.LoanType;
import com.lyhorng.pedssystem.model.branchRequest.RelationshipType;
import com.lyhorng.pedssystem.model.branchRequest.RequestFor;
import com.lyhorng.pedssystem.model.customer.Customer;
import com.lyhorng.pedssystem.repository.branchRequest.BranchRepository;
import com.lyhorng.pedssystem.repository.branchRequest.BranchRequestRepository;
import com.lyhorng.pedssystem.repository.branchRequest.CurrencyRepository;
import com.lyhorng.pedssystem.repository.branchRequest.LoanTypeRepository;
import com.lyhorng.pedssystem.repository.branchRequest.RelationshipTypeRepository;
import com.lyhorng.pedssystem.repository.branchRequest.RequestForRepository;
import com.lyhorng.pedssystem.repository.customer.CustomerRepository;

@Service
public class BranchRequestService {

    @Autowired
    private BranchRequestRepository branchRequestRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Autowired
    private RequestForRepository requestForRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private RelationshipTypeRepository relationshipTypeRepository;

    @Transactional
    public BranchRequest createBranchRequest(BranchRequestDTO branchRequestDTO) {
        BranchRequest branchRequest = new BranchRequest();

        // Batch fetch all customers
        List<Long> allCustomerIds = new ArrayList<>();
        allCustomerIds.add(branchRequestDTO.getMainBorrowerId());

        if (branchRequestDTO.getCoBorrowers() != null) {
            branchRequestDTO.getCoBorrowers().forEach(cb -> allCustomerIds.add(cb.getCustomerId()));
        }

        List<Customer> customers = customerRepository.findAllById(allCustomerIds);
        Map<Long, Customer> customerMap = customers.stream()
                .collect(Collectors.toMap(Customer::getId, customer -> customer));

        // Validate all customers
        if (customerMap.size() != allCustomerIds.size()) {
            List<Long> missingIds = allCustomerIds.stream()
                    .filter(id -> !customerMap.containsKey(id))
                    .collect(Collectors.toList());
            throw new RuntimeException("Customers not found with IDs: " + missingIds);
        }

        // Batch fetch relationship types
        List<Long> relationshipTypeIds = new ArrayList<>();
        if (branchRequestDTO.getCoBorrowers() != null) {
            branchRequestDTO.getCoBorrowers().forEach(cb -> relationshipTypeIds.add(cb.getRelationshipTypeId()));
        }

        Map<Long, RelationshipType> relationshipTypeMap = relationshipTypeRepository
                .findAllById(relationshipTypeIds)
                .stream()
                .collect(Collectors.toMap(RelationshipType::getId, rt -> rt));

        // Set Main Borrower
        Customer mainBorrower = customerMap.get(branchRequestDTO.getMainBorrowerId());
        if (mainBorrower == null) {
            throw new RuntimeException("Main Borrower not found");
        }
        branchRequest.setMainBorrower(mainBorrower);

        // Set Co-Borrowers and update their relationship
        if (branchRequestDTO.getCoBorrowers() != null && !branchRequestDTO.getCoBorrowers().isEmpty()) {
            List<Customer> coBorrowerList = new ArrayList<>();

            for (CoBorrowerDTO coBorrowerDTO : branchRequestDTO.getCoBorrowers()) {
                Customer coBorrower = customerMap.get(coBorrowerDTO.getCustomerId());
                RelationshipType relationshipType = relationshipTypeMap.get(coBorrowerDTO.getRelationshipTypeId());

                if (coBorrower == null) {
                    throw new RuntimeException("Co-Borrower not found with ID: " + coBorrowerDTO.getCustomerId());
                }
                if (relationshipType == null) {
                    throw new RuntimeException(
                            "Relationship Type not found with ID: " + coBorrowerDTO.getRelationshipTypeId());
                }

                // Update customer's relationship (for this request context)
                coBorrower.setRelationshipType(relationshipType);
                coBorrowerList.add(coBorrower);
            }

            branchRequest.setCoBorrowers(coBorrowerList);
        }

        // Fetch other entities
        Branch branch = branchRepository.findById(branchRequestDTO.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        branchRequest.setBranch(branch);

        LoanType loanType = loanTypeRepository.findById(branchRequestDTO.getLoanTypeId())
                .orElseThrow(() -> new RuntimeException("Loan Type not found"));
        branchRequest.setLoanType(loanType);

        RequestFor requestFor = requestForRepository.findById(branchRequestDTO.getRequestForId())
                .orElseThrow(() -> new RuntimeException("Request For not found"));
        branchRequest.setRequestFor(requestFor);

        Currency currency = currencyRepository.findById(branchRequestDTO.getCurrencyId())
                .orElseThrow(() -> new RuntimeException("Currency not found"));
        branchRequest.setCurrency(currency);

        branchRequest.setRequestLimit(branchRequestDTO.getRequestLimit());
        branchRequest.setRemark(branchRequestDTO.getRemark());

        if (branchRequestDTO.getSubmitDate() != null) {
            branchRequest.setSubmitDate(branchRequestDTO.getSubmitDate());
        }

        return branchRequestRepository.save(branchRequest);
    }

    public Page<BranchRequest> getAllBranchRequests(int page, int size) {
        int pageIndex = Math.max(0, page -1);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return branchRequestRepository.findAll(pageable);
    }

    public BranchRequest getBranchRequestById(Long id) {
        return branchRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch Request not found"));
    }

    @Transactional
    public BranchRequest updateBranchRequest(Long id, BranchRequestDTO branchRequestDTO) {
        // Get existing branch request
        BranchRequest branchRequest = getBranchRequestById(id);

        // Update basic fields
        branchRequest.setRequestLimit(branchRequestDTO.getRequestLimit());
        branchRequest.setRemark(branchRequestDTO.getRemark());

        // Update Branch if provided
        if (branchRequestDTO.getBranchId() != null) {
            Branch branch = branchRepository.findById(branchRequestDTO.getBranchId())
                    .orElseThrow(
                            () -> new RuntimeException("Branch not found with ID: " + branchRequestDTO.getBranchId()));
            branchRequest.setBranch(branch);
        }

        // Update Main Borrower if provided
        if (branchRequestDTO.getMainBorrowerId() != null) {
            Customer mainBorrower = customerRepository.findById(branchRequestDTO.getMainBorrowerId())
                    .orElseThrow(() -> new RuntimeException(
                            "Main Borrower not found with ID: " + branchRequestDTO.getMainBorrowerId()));
            branchRequest.setMainBorrower(mainBorrower);
        }

        // Update Co-Borrowers with relationships if provided
        if (branchRequestDTO.getCoBorrowers() != null && !branchRequestDTO.getCoBorrowers().isEmpty()) {
            // Batch fetch all customers
            List<Long> customerIds = branchRequestDTO.getCoBorrowers().stream()
                    .map(CoBorrowerDTO::getCustomerId)
                    .collect(Collectors.toList());

            List<Customer> customers = customerRepository.findAllById(customerIds);
            Map<Long, Customer> customerMap = customers.stream()
                    .collect(Collectors.toMap(Customer::getId, customer -> customer));

            // Batch fetch relationship types
            List<Long> relationshipTypeIds = branchRequestDTO.getCoBorrowers().stream()
                    .map(CoBorrowerDTO::getRelationshipTypeId)
                    .collect(Collectors.toList());

            Map<Long, RelationshipType> relationshipTypeMap = relationshipTypeRepository
                    .findAllById(relationshipTypeIds)
                    .stream()
                    .collect(Collectors.toMap(RelationshipType::getId, rt -> rt));

            // Clear existing co-borrowers and add new ones
            branchRequest.getCoBorrowers().clear();

            List<Customer> coBorrowerList = new ArrayList<>();
            for (CoBorrowerDTO coBorrowerDTO : branchRequestDTO.getCoBorrowers()) {
                Customer coBorrower = customerMap.get(coBorrowerDTO.getCustomerId());
                RelationshipType relationshipType = relationshipTypeMap.get(coBorrowerDTO.getRelationshipTypeId());

                if (coBorrower == null) {
                    throw new RuntimeException("Co-Borrower not found with ID: " + coBorrowerDTO.getCustomerId());
                }
                if (relationshipType == null) {
                    throw new RuntimeException(
                            "Relationship Type not found with ID: " + coBorrowerDTO.getRelationshipTypeId());
                }

                // Update customer's relationship
                coBorrower.setRelationshipType(relationshipType);
                coBorrowerList.add(coBorrower);
            }

            branchRequest.setCoBorrowers(coBorrowerList);
        }

        // Update Loan Type if provided
        if (branchRequestDTO.getLoanTypeId() != null) {
            LoanType loanType = loanTypeRepository.findById(branchRequestDTO.getLoanTypeId())
                    .orElseThrow(() -> new RuntimeException(
                            "Loan Type not found with ID: " + branchRequestDTO.getLoanTypeId()));
            branchRequest.setLoanType(loanType);
        }

        // Update Request For if provided
        if (branchRequestDTO.getRequestForId() != null) {
            RequestFor requestFor = requestForRepository.findById(branchRequestDTO.getRequestForId())
                    .orElseThrow(() -> new RuntimeException(
                            "Request For not found with ID: " + branchRequestDTO.getRequestForId()));
            branchRequest.setRequestFor(requestFor);
        }

        // Update Currency if provided
        if (branchRequestDTO.getCurrencyId() != null) {
            Currency currency = currencyRepository.findById(branchRequestDTO.getCurrencyId())
                    .orElseThrow(() -> new RuntimeException(
                            "Currency not found with ID: " + branchRequestDTO.getCurrencyId()));
            branchRequest.setCurrency(currency);
        }

        // Update Submit Date if provided
        if (branchRequestDTO.getSubmitDate() != null) {
            branchRequest.setSubmitDate(branchRequestDTO.getSubmitDate());
        }

        return branchRequestRepository.save(branchRequest);
    }

    @Transactional
    public void deleteBranchRequest(Long id) {
        if (!branchRequestRepository.existsById(id)) {
            throw new RuntimeException("Branch Request not found");
        }
        branchRequestRepository.deleteById(id);
    }

    @Async
    public void sendConfirmationEmail(String email) {
        System.out.println("Sending confirmation email to: " + email);
    }
}