package com.lyhorng.pedssystem.model.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.lyhorng.pedssystem.enums.EvaStatus;
import com.lyhorng.pedssystem.model.customer.Customer;
import com.lyhorng.pedssystem.model.branchRequest.BranchRequest;
import com.lyhorng.pedssystem.model.property.land.Land;
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

    // Row 1: Requester - Link to Request from Branch
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_request_id")
    private BranchRequest branchRequest;

    // Row 2: Status of Evaluation (New/Renew)
    @Enumerated(EnumType.STRING)
    @Column(name = "eva_status")
    private EvaStatus evaStatus;

    // Row 3: Existing Application code (only for Renew)
    @Column(name = "exist_application_code", length = 50)
    private String existApplicationCode;

    // Row 4: Application Code - Auto generate by system as unique code (11 digits)
    @Column(name = "application_code", unique = true, length = 11)
    private String applicationCode;

    // Row 5: Evaluation Cycle (Default 1)
    @Column(name = "eva_cycle")
    private Integer evaCycle = 1;

    // Row 6: Ownership Title (Yes/No)
    @Column(name = "is_ownership_title")
    private Boolean isOwnershipTitle = false;

    // Row 7: Owner - Link to customer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Customer owner;

    // Row 8: Co-Owner - Link to customer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "co_owner_id")
    private Customer coOwner;

    // Row 9: Type of Title
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_title_type_id")
    private PropertyTitleType propertyTitleType;

    // Row 10: Title Number (Free text)
    @Column(name = "title_number", length = 100)
    private String titleNumber;

    // Row 11: Category Property
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    // Row 12: Property Specifics
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_specific_id")
    private PropertySpecific propertySpecific;

    // ================== LOCATION (Rows 13-16) ==================

    // Row 13: Province
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id")
    private Province province;

    // Row 14: City/District
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    private District district;

    // Row 15: Commune
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commune_id")
    private Commune commune;

    // Row 16: Village
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "village_id")
    private Village village;

    // Row 17: Information about measure
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measure_info_id")
    private PropertyMeasureInfo measureInfo;

    // Row 18: Keep record Evaluation (Activate/Deactivate, Default Activate)
    @Column(name = "is_keep_record_evaluation")
    private Boolean isKeepRecordEvaluation = true;

    // Row 19: Remark (Free text)
    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;

    // ================== LAND RELATIONSHIP (1-to-1) ==================
    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Land land;

    // ================== AUDIT FIELDS ==================
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
}