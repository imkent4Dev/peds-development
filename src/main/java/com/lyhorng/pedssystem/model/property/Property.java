package com.lyhorng.pedssystem.model.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import com.lyhorng.pedssystem.model.property.location.Commune;
import com.lyhorng.pedssystem.model.property.location.District;
import com.lyhorng.pedssystem.model.property.location.Province;
import com.lyhorng.pedssystem.model.property.location.Village;

@Entity
@Table(name = "property")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch_request_id")
    private Integer branchRequestId;

    @Column(name = "old_property_id", length = 50)
    private String oldPropertyId;

    @Column(name = "property_code", length = 50, unique = true)
    private String propertyCode;

    @Column(name = "property_version", length = 20)
    private String propertyVersion;

    @Column(name = "is_owner")
    private Boolean isOwner = false;

    @Column(name = "title_number", length = 100)
    private String titleNumber;

    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;

    // ================== RELATIONSHIPS ==================
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_title_type_id")
    private PropertyTittleType propertyTitleType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_info_id")
    private PropertyMeasureInfo propertyInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measure_info_id")
    private PropertyMeasureInfo measureInfo;

    // ================== LOCATION RELATIONSHIPS ==================
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commune_id")
    private Commune commune;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "village_id")
    private Village village;

    @Column(name = "map_data")
    private String mapData;

    @Column(name = "color")
    private String color;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "attachment")
    private String attachment;

    // ================== AUDIT FIELDS ==================
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "file_path")
    private String filePath;
}