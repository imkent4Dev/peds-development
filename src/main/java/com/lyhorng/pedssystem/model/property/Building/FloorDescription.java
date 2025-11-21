package com.lyhorng.pedssystem.model.property.building;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.lyhorng.pedssystem.enums.FloorAreaType;

@Entity
@Table(name = "floor_description")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorDescription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "description_name", nullable = false)
    private String descriptionName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "floor_type", nullable = false)
    private FloorAreaType floorType;
    
    // MAIN Floor descriptions: Basement, Mezzanine, Ground Floor, 1st Floor - 20th Floor,
    // Rooftop, 1st Floor, 2nd Floor, 3rd Floor, ..., 20th Floor, single-storey house,
    // Kitchen, Garage, Shop, Bathroom, Bedroom, Pig Blood, Chicken Blood, Other
    
    // ANCILLARY Floor descriptions: Storehouse, Roof extension, Balcony, gas station floor,
    // gazebos, carport, Patio, gardens
}