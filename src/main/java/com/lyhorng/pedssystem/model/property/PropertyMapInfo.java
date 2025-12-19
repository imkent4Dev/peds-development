package com.lyhorng.pedssystem.model.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Store Google Map information for a property.
 * All business fields are nullable and can be filled/updated later.
 */
@Entity
@Table(name = "property_map_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyMapInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false, unique = true)
    private Property property;

    /**
     * JSON or encoded string representing the drawn line / polygon on the map.
     */
    @Column(name = "draw_line", columnDefinition = "TEXT")
    private String drawLine;

    /**
     * Map style or color (free text).
     */
    @Column(name = "style", length = 100)
    private String style;

    /**
     * Latitude and longitude of the main marker.
     */
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    /**
     * Optional reference document (PDF) path in MinIO.
     */
    @Column(name = "document_path", length = 500)
    private String documentPath;
}


