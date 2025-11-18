package com.lyhorng.pedssystem.model.branchRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lyhorng.pedssystem.model.customer.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "branch_request_coborrower")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BranchRequestCoBorrower {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "branch_request_id")
    private BranchRequest branchRequest;
    
    @ManyToOne
    @JoinColumn(name = "coborrower_id")
    private Customer coBorrower;
    
    @ManyToOne
    @JoinColumn(name = "relationship_type_id")
    private RelationshipType relationshipType;
}