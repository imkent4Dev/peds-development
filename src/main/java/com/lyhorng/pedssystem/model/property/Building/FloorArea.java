package com.lyhorng.pedssystem.model.property.building;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.lyhorng.pedssystem.enums.FloorAreaType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "floor_area")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorArea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Many-to-One relationship with Building
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
    
    // Floor Type: MAIN or ANCILLARY
    @Enumerated(EnumType.STRING)
    @Column(name = "floor_type", nullable = false)
    private FloorAreaType floorType;
    
    // Row 36/41: Description (from FloorDescription lookup table)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "floor_description_id", nullable = false)
    private FloorDescription floorDescription;
    
    // Row 37/42: Size (latest)
    @Column(name = "size", precision = 15, scale = 2, nullable = false)
    private BigDecimal size;
    
    // Row 38/43: Length (latest)
    @Column(name = "length", precision = 15, scale = 2)
    private BigDecimal length;
    
    // Row 39/44: Structure (Auto-calculated: "Floor/size 1, Floor/size 2, ...")
    @Column(name = "structure")
    private String structure;
    
    // Order/sequence number for display
    @Column(name = "display_order")
    private Integer displayOrder;
    
    // Audit fields
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        generateStructure();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        generateStructure();
    }
    
    // Auto-generate structure string
    private void generateStructure() {
        if (floorDescription != null && size != null) {
            this.structure = String.format("%s/%.2f", 
                floorDescription.getDescriptionName(), 
                size);
        }
    }
}