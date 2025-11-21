package com.lyhorng.pedssystem.model.property.building;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "building_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "type_name", unique = true, nullable = false)
    private String typeName;
    
    // Values: Villa, Townhouse, Apartment, Industrial, Hotel, Guest house, Hospital,
    // Education Shop, Warehouse, Office, gas station, Room Rent, Vacation home,
    // Condominium, Pig Blood, Chicken Blood, Other
}