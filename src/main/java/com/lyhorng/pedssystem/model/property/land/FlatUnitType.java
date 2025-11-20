package com.lyhorng.pedssystem.model.property.land;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "flat_unit_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlatUnitType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "unit_type_name", unique = true, nullable = false)
    private String unitTypeName;
    
    // Values: Lot, Blank
}