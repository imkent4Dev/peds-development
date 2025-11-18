package com.lyhorng.pedssystem.model.branchRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lyhorng.pedssystem.model.customer.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "branch_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BranchRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    
    private LocalDateTime submitDate;
    
    @ManyToOne
    @JoinColumn(name = "main_borrower_id")
    private Customer mainBorrower;
    
    @ManyToMany
    @JoinTable(
        name = "branch_request_coborrower",
        joinColumns = @JoinColumn(name = "branch_request_id"),
        inverseJoinColumns = @JoinColumn(name = "coborrower_id")
    )
    private List<Customer> coBorrowers = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;
    
    @ManyToOne
    @JoinColumn(name = "request_for_id")
    private RequestFor requestFor;
    
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    
    private BigDecimal requestLimit;
    private String remark;
    
    @PrePersist
    protected void onCreate() {
        if (this.submitDate == null) {
            this.submitDate = LocalDateTime.now();
        }
    }
}