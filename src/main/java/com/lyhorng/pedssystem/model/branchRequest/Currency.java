package com.lyhorng.pedssystem.model.branchRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currency")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Currency code is required")
    @Column(nullable = false, unique = true, length = 3)
    private String code; // Currency code (e.g., USD, EUR, GBP)

    @NotBlank(message = "Currency name is required")
    @Column(nullable = false, length = 100)
    private String name; // Currency name (e.g., United States Dollar)

    @NotBlank(message = "Currency symbol is required")
    @Column(nullable = false, length = 10)
    private String symbol; // Currency symbol (e.g., $, €, £)

    @Column(nullable = false)
    private boolean isActive = true; // Whether the currency is currently active/usable
}
