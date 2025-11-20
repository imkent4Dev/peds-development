package com.lyhorng.pedssystem.service.property;

import com.lyhorng.pedssystem.dto.property.*;
import com.lyhorng.pedssystem.enums.EvaStatus;
import com.lyhorng.pedssystem.exception.ResourceNotFoundException;
import com.lyhorng.pedssystem.model.property.Property;
import com.lyhorng.pedssystem.model.branchRequest.BranchRequest;
import com.lyhorng.pedssystem.model.customer.Customer;
import com.lyhorng.pedssystem.repository.property.*;
import com.lyhorng.pedssystem.repository.property.location.CommuneRepository;
import com.lyhorng.pedssystem.repository.property.location.DistrictRepository;
import com.lyhorng.pedssystem.repository.property.location.ProvinceRepository;
import com.lyhorng.pedssystem.repository.property.location.VillageRepository;
import com.lyhorng.pedssystem.repository.customer.CustomerRepository;
import com.lyhorng.pedssystem.repository.branchRequest.BranchRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final BranchRequestRepository branchRequestRepository;
    private final CustomerRepository customerRepository;
    private final PropertyTitleTypeRepository propertyTitleTypeRepository;
    private final CategoryRepository categoryRepository;
    private final PropertySpecificRepository propertySpecificRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    private final VillageRepository villageRepository;
    private final MeasureInfoRepository propertyMeasureInfoRepository;

    // ==================== CREATE ====================
    public PropertyResponseDto createProperty(PropertyRequestDto requestDto) {
        log.info("Creating new property with evaluation status: {}", requestDto.getEvaStatus());

        // Validate existing application code for RENEW status
        if (requestDto.getEvaStatus() == EvaStatus.RENEW) {
            if (requestDto.getExistApplicationCode() == null || requestDto.getExistApplicationCode().isEmpty()) {
                throw new IllegalArgumentException("Existing application code is required for RENEW status");
            }
        }

        Property property = new Property();

        // Set branch request
        BranchRequest branchRequest = branchRequestRepository.findById(requestDto.getBranchRequestId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Branch request not found with id: " + requestDto.getBranchRequestId()));
        property.setBranchRequest(branchRequest);

        // Set basic fields
        property.setEvaStatus(requestDto.getEvaStatus());
        property.setExistApplicationCode(requestDto.getExistApplicationCode());
        property.setApplicationCode(generateUniqueApplicationCode());
        property.setEvaCycle(requestDto.getEvaCycle());
        property.setIsOwnershipTitle(requestDto.getIsOwnershipTitle());

        // Set owner
        Customer owner = customerRepository.findById(requestDto.getOwnerId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Owner not found with id: " + requestDto.getOwnerId()));
        property.setOwner(owner);

        // Set co-owner if provided
        if (requestDto.getCoOwnerId() != null) {
            Customer coOwner = customerRepository.findById(requestDto.getCoOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Co-owner not found with id: " + requestDto.getCoOwnerId()));
            property.setCoOwner(coOwner);
        }

        // Set property details
        property.setPropertyTitleType(propertyTitleTypeRepository.findById(requestDto.getPropertyTitleTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Property title type not found")));
        property.setTitleNumber(requestDto.getTitleNumber());
        property.setCategory(categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        property.setPropertySpecific(propertySpecificRepository.findById(requestDto.getPropertySpecificId())
                .orElseThrow(() -> new ResourceNotFoundException("Property specific not found")));

        // Set location
        property.setProvince(provinceRepository.findById(requestDto.getProvinceId())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found")));
        property.setDistrict(districtRepository.findById(requestDto.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("District not found")));
        property.setCommune(communeRepository.findById(requestDto.getCommuneId())
                .orElseThrow(() -> new ResourceNotFoundException("Commune not found")));

        if (requestDto.getVillageId() != null) {
            property.setVillage(villageRepository.findById(requestDto.getVillageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Village not found")));
        }

        // Set measure info
        property.setMeasureInfo(propertyMeasureInfoRepository.findById(requestDto.getMeasureInfoId())
                .orElseThrow(() -> new ResourceNotFoundException("Measure info not found")));

        // Set other fields
        property.setIsKeepRecordEvaluation(requestDto.getIsKeepRecordEvaluation());
        property.setRemark(requestDto.getRemark());

        Property savedProperty = propertyRepository.save(property);
        log.info("Property created successfully with application code: {}", savedProperty.getApplicationCode());

        return convertToResponseDto(savedProperty);
    }

    // ==================== UPDATE ====================
    public PropertyResponseDto updateProperty(Long id, PropertyRequestDto requestDto) {
        log.info("Updating property with id: {}", id);

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));

        // Validate existing application code for RENEW status
        if (requestDto.getEvaStatus() == EvaStatus.RENEW) {
            if (requestDto.getExistApplicationCode() == null || requestDto.getExistApplicationCode().isEmpty()) {
                throw new IllegalArgumentException("Existing application code is required for RENEW status");
            }
        }

        // Update branch request
        BranchRequest branchRequest = branchRequestRepository.findById(requestDto.getBranchRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch request not found"));
        property.setBranchRequest(branchRequest);

        // Update basic fields
        property.setEvaStatus(requestDto.getEvaStatus());
        property.setExistApplicationCode(requestDto.getExistApplicationCode());
        property.setEvaCycle(requestDto.getEvaCycle());
        property.setIsOwnershipTitle(requestDto.getIsOwnershipTitle());

        // Update owner
        Customer owner = customerRepository.findById(requestDto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        property.setOwner(owner);

        // Update co-owner
        if (requestDto.getCoOwnerId() != null) {
            Customer coOwner = customerRepository.findById(requestDto.getCoOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Co-owner not found"));
            property.setCoOwner(coOwner);
        } else {
            property.setCoOwner(null);
        }

        // Update property details
        property.setPropertyTitleType(propertyTitleTypeRepository.findById(requestDto.getPropertyTitleTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Property title type not found")));
        property.setTitleNumber(requestDto.getTitleNumber());
        property.setCategory(categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        property.setPropertySpecific(propertySpecificRepository.findById(requestDto.getPropertySpecificId())
                .orElseThrow(() -> new ResourceNotFoundException("Property specific not found")));

        // Update location
        property.setProvince(provinceRepository.findById(requestDto.getProvinceId())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found")));
        property.setDistrict(districtRepository.findById(requestDto.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("District not found")));
        property.setCommune(communeRepository.findById(requestDto.getCommuneId())
                .orElseThrow(() -> new ResourceNotFoundException("Commune not found")));

        if (requestDto.getVillageId() != null) {
            property.setVillage(villageRepository.findById(requestDto.getVillageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Village not found")));
        } else {
            property.setVillage(null);
        }

        // Update measure info
        property.setMeasureInfo(propertyMeasureInfoRepository.findById(requestDto.getMeasureInfoId())
                .orElseThrow(() -> new ResourceNotFoundException("Measure info not found")));

        // Update other fields
        property.setIsKeepRecordEvaluation(requestDto.getIsKeepRecordEvaluation());
        property.setRemark(requestDto.getRemark());

        Property updatedProperty = propertyRepository.save(property);
        log.info("Property updated successfully with id: {}", id);

        return convertToResponseDto(updatedProperty);
    }

    // ==================== GET BY ID ====================
    @Transactional(readOnly = true)
    public PropertyResponseDto getPropertyById(Long id) {
        log.info("Fetching property with id: {}", id);

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));

        return convertToResponseDto(property);
    }

    // ==================== GET ALL ====================
    @Transactional(readOnly = true)
    public List<PropertyResponseDto> getAllProperties() {
        log.info("Fetching all properties");

        return propertyRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // ==================== DELETE ====================
    public void deleteProperty(Long id) {
        log.info("Deleting property with id: {}", id);

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));

        propertyRepository.delete(property);
        log.info("Property deleted successfully with id: {}", id);
    }

    // ==================== ADDITIONAL QUERIES ====================
    @Transactional(readOnly = true)
    public PropertyResponseDto getPropertyByApplicationCode(String applicationCode) {
        log.info("Fetching property with application code: {}", applicationCode);

        Property property = propertyRepository.findByApplicationCode(applicationCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Property not found with application code: " + applicationCode));

        return convertToResponseDto(property);
    }

    @Transactional(readOnly = true)
    public List<PropertyResponseDto> getPropertiesByEvaStatus(EvaStatus evaStatus) {
        log.info("Fetching properties with evaluation status: {}", evaStatus);

        return propertyRepository.findByEvaStatus(evaStatus).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PropertyResponseDto> getPropertiesByOwnerId(Long ownerId) {
        log.info("Fetching properties for owner id: {}", ownerId);

        return propertyRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // ==================== HELPER METHODS ====================
    private String generateUniqueApplicationCode() {
        String code;
        do {
            code = generateRandomCode(8); // Generate 8 random digits
            code = "Col" + String.format("%08d", Integer.parseInt(code)); // Prefix "Col" and zero-pad to 8 digits
        } while (propertyRepository.existsByApplicationCode(code));

        return code;
    }

    private String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private PropertyResponseDto convertToResponseDto(Property property) {
        PropertyResponseDto dto = new PropertyResponseDto();
        dto.setId(property.getId());

        // Branch Request
        if (property.getBranchRequest() != null) {
            BranchRequestSummaryDto branchDto = new BranchRequestSummaryDto();
            branchDto.setId(property.getBranchRequest().getId());
            // Set other branch fields as needed
            dto.setBranchRequest(branchDto);
        }

        dto.setEvaStatus(property.getEvaStatus());
        dto.setExistApplicationCode(property.getExistApplicationCode());
        dto.setApplicationCode(property.getApplicationCode());
        dto.setEvaCycle(property.getEvaCycle());
        dto.setIsOwnershipTitle(property.getIsOwnershipTitle());

        // Owner
        if (property.getOwner() != null) {
            CustomerSummaryDto ownerDto = new CustomerSummaryDto();
            ownerDto.setId(property.getOwner().getId());
            // Set other owner fields from Customer entity
            dto.setOwner(ownerDto);
        }

        // Co-Owner
        if (property.getCoOwner() != null) {
            CustomerSummaryDto coOwnerDto = new CustomerSummaryDto();
            coOwnerDto.setId(property.getCoOwner().getId());
            // Set other co-owner fields from Customer entity
            dto.setCoOwner(coOwnerDto);
        }

        // Property Title Type
        if (property.getPropertyTitleType() != null) {
            PropertyTitleTypeSummaryDto titleTypeDto = new PropertyTitleTypeSummaryDto();
            titleTypeDto.setId(property.getPropertyTitleType().getId());
            titleTypeDto.setTypeName(property.getPropertyTitleType().getTitleType());
            dto.setPropertyTitleType(titleTypeDto);
        }

        dto.setTitleNumber(property.getTitleNumber());

        // Category
        if (property.getCategory() != null) {
            CategorySummaryDto categoryDto = new CategorySummaryDto();
            categoryDto.setId(property.getCategory().getId());
            categoryDto.setCategoryName(property.getCategory().getCategory());
            dto.setCategory(categoryDto);
        }

        // Property Specific
        if (property.getPropertySpecific() != null) {
            PropertySpecificSummaryDto specificDto = new PropertySpecificSummaryDto();
            specificDto.setId(property.getPropertySpecific().getId());
            specificDto.setSpecificName(property.getPropertySpecific().getName());
            dto.setPropertySpecific(specificDto);
        }

        // Location
        LocationDto locationDto = new LocationDto();

        if (property.getProvince() != null) {
            ProvinceSummaryDto provinceDto = new ProvinceSummaryDto();
            provinceDto.setId(property.getProvince().getId());
            provinceDto.setProvinceName(property.getProvince().getName());
            locationDto.setProvince(provinceDto);
        }

        if (property.getDistrict() != null) {
            DistrictSummaryDto districtDto = new DistrictSummaryDto();
            districtDto.setId(property.getDistrict().getId());
            districtDto.setDistrictName(property.getDistrict().getName());
            locationDto.setDistrict(districtDto);
        }

        if (property.getCommune() != null) {
            CommuneSummaryDto communeDto = new CommuneSummaryDto();
            communeDto.setId(property.getCommune().getId());
            communeDto.setCommuneName(property.getCommune().getName());
            locationDto.setCommune(communeDto);
        }

        if (property.getVillage() != null) {
            VillageSummaryDto villageDto = new VillageSummaryDto();
            villageDto.setId(property.getVillage().getId());
            villageDto.setVillageName(property.getVillage().getName());
            locationDto.setVillage(villageDto);
        }

        dto.setLocation(locationDto);

        // Measure Info
        if (property.getMeasureInfo() != null) {
            PropertyMeasureInfoSummaryDto measureDto = new PropertyMeasureInfoSummaryDto();
            measureDto.setId(property.getMeasureInfo().getId());
            // Set measure info fields
            dto.setMeasureInfo(measureDto);
        }

        dto.setIsKeepRecordEvaluation(property.getIsKeepRecordEvaluation());
        dto.setRemark(property.getRemark());
        dto.setCreatedAt(property.getCreatedAt());
        dto.setUpdatedAt(property.getUpdatedAt());

        return dto;
    }
}