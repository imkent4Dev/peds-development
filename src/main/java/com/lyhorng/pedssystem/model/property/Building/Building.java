package com.lyhorng.pedssystem.model.property.building;

import com.lyhorng.pedssystem.model.agency.Agency;
import com.lyhorng.pedssystem.model.property.Property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.lyhorng.pedssystem.enums.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "building")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Building {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    
    // Replace enum with entity relationship
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_type_id", nullable = false)
    private BuildingSourceType sourceType;
    
    // Add agency relationship (only populated if sourceType is AGENCY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agency_id")
    private Agency agency;
    
    // Building Evaluation (Yes/No/Remark)
    @Enumerated(EnumType.STRING)
    @Column(name = "building_evaluation")
    private BuildingEvaluation buildingEvaluation;
    
    // Building Type (Villa, Townhouse, Apartment, etc.)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_type_id")
    private BuildingType buildingType;

    // Building Stories (total input)
    @Column(name = "building_storey")
    private Integer buildingStorey;
    
    // Building Size Unit (use input As: 1(sqm))
    @Column(name = "building_size_unit", precision = 15, scale = 2)
    private BigDecimal buildingSizeUnit;
    
    // Building Year Built
    @Column(name = "building_year_built")
    private Integer buildingYearBuilt;
    
    // Remark
    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;
    
    // One-to-Many: Floor Areas (MAIN and ANCILLARY)
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FloorArea> floorAreas = new ArrayList<>();
    
    // Auto-calculated: Sum of Main Floor Areas
    @Column(name = "total_mfa", precision = 15, scale = 2)
    private BigDecimal totalMFA;
    
    // Auto-calculated: Sum of Ancillary Floor Areas
    @Column(name = "total_afa", precision = 15, scale = 2)
    private BigDecimal totalAFA;
    
    // Auto-calculated: MFA + AFA
    @Column(name = "accommodation_unit", precision = 15, scale = 2)
    private BigDecimal accommodationUnit;
    
    // Auto-calculated: Same as accommodation_unit
    @Column(name = "total_approximately", precision = 15, scale = 2)
    private BigDecimal totalApproximately;
    
    // Audit fields
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Calculate all totals from floor areas
    public void calculateTotals() {
        if (floorAreas == null || floorAreas.isEmpty()) {
            this.totalMFA = BigDecimal.ZERO;
            this.totalAFA = BigDecimal.ZERO;
            this.accommodationUnit = BigDecimal.ZERO;
            this.totalApproximately = BigDecimal.ZERO;
            return;
        }
        
        BigDecimal mainTotal = floorAreas.stream()
            .filter(fa -> fa.getFloorType() == FloorAreaType.MAIN)
            .map(FloorArea::getSize)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal ancillaryTotal = floorAreas.stream()
            .filter(fa -> fa.getFloorType() == FloorAreaType.ANCILLARY)
            .map(FloorArea::getSize)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.totalMFA = mainTotal;
        this.totalAFA = ancillaryTotal;
        this.accommodationUnit = mainTotal.add(ancillaryTotal);
        this.totalApproximately = mainTotal.add(ancillaryTotal);
    }
    
    // Helper method to add floor area
    public void addFloorArea(FloorArea floorArea) {
        floorAreas.add(floorArea);
        floorArea.setBuilding(this);
    }
    
    // Helper method to remove floor area
    public void removeFloorArea(FloorArea floorArea) {
        floorAreas.remove(floorArea);
        floorArea.setBuilding(null);
    }
}