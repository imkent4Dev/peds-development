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
@Table(name = "type_of_lot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfLot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "lot_type_name", unique = true, nullable = false)
    private String lotTypeName;
    
    // Values: Corner Lot, Reverse corner lot, Interior Lot, Flag Lot, Through Lot
}