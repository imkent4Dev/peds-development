package com.lyhorng.pedssystem.model.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Store property photos such as front/left/right/inside.
 * All fields are optional except the relation to Property.
 */
@Entity
@Table(name = "property_photo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Parent property.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    /**
     * Logical type of the photo: FRONT, LEFT, RIGHT, INSIDE.
     * Kept as free text for flexibility instead of enum to keep DB migration simple.
     */
    @Column(name = "photo_type", length = 50)
    private String photoType;

    /**
     * File path or URL stored in MinIO.
     */
    @Column(name = "file_path", length = 500)
    private String filePath;

    /**
     * Optional display order when rendering in UI.
     */
    @Column(name = "display_order")
    private Integer displayOrder;
}


