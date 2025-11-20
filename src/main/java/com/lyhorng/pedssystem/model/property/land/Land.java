package com.lyhorng.pedssystem.model.property.land;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lyhorng.pedssystem.model.property.Property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "land")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Land {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // One-to-One relationship with Property
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", unique = true)
    private Property property;
    
    // Row 20: Shape
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shape_id")
    private LandShape shape;
    
    // Row 21: Type of Lot
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_of_lot_id")
    private TypeOfLot typeOfLot;
    
    // Row 22: Land Size (Unit: m²)
    @Column(name = "land_size", precision = 15, scale = 2)
    private BigDecimal landSize;
    
    // Row 23: Length (Unit: m)
    @Column(name = "length", precision = 15, scale = 2)
    private BigDecimal length;
    
    // Row 24: Width (Unit: m)
    @Column(name = "width", precision = 15, scale = 2)
    private BigDecimal width;
    
    // Row 25: Front (Unit: m²)
    @Column(name = "front", precision = 15, scale = 2)
    private BigDecimal front;
    
    // Row 26: Back (Unit: m²)
    @Column(name = "back", precision = 15, scale = 2)
    private BigDecimal back;
    
    // Row 27: Dimension land (Auto calculated: Width x Length)
    @Column(name = "dimension_land")
    private String dimensionLand;
    
    // Row 28: Flat/Unit Type
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flat_unit_type_id")
    private FlatUnitType flatUnitType;
    
    // Row 29: # of Lot (Default 1)
    @Column(name = "number_of_lot")
    private Integer numberOfLot = 1;
    
    // ================== AUDIT FIELDS ==================
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        calculateDimensionLand();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateDimensionLand();
    }
    
    // Auto calculate dimension land (Width x Length)
    private void calculateDimensionLand() {
        if (width != null && length != null) {
            this.dimensionLand = String.format("%.2fx%.2f", width, length);
        }
    }
}