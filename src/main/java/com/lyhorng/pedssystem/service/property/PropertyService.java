package com.lyhorng.pedssystem.service.property;

import com.lyhorng.pedssystem.dto.property.*;
import com.lyhorng.pedssystem.dto.property.building.AgencySummaryDto;
import com.lyhorng.pedssystem.dto.property.building.BuildingRequestDto;
import com.lyhorng.pedssystem.dto.property.building.BuildingResponseDto;
import com.lyhorng.pedssystem.dto.property.building.BuildingSummaryDto;
import com.lyhorng.pedssystem.dto.property.building.BuildingTotalsDto;
import com.lyhorng.pedssystem.dto.property.building.BuildingTypeDto;
import com.lyhorng.pedssystem.dto.property.building.BuildingsBySourceDto;
import com.lyhorng.pedssystem.dto.property.building.FloorAreaRequestDto;
import com.lyhorng.pedssystem.dto.property.building.FloorAreaResponseDto;
import com.lyhorng.pedssystem.dto.property.building.FloorDescriptionDto;
import com.lyhorng.pedssystem.dto.property.building.SourceTypeSummaryDto;
import com.lyhorng.pedssystem.enums.EvaStatus;
import com.lyhorng.pedssystem.enums.FloorAreaType;
import com.lyhorng.pedssystem.exception.ResourceNotFoundException;
import com.lyhorng.pedssystem.model.property.Property;
import com.lyhorng.pedssystem.model.property.building.Building;
import com.lyhorng.pedssystem.model.property.building.BuildingSourceType;
import com.lyhorng.pedssystem.model.property.building.FloorArea;
import com.lyhorng.pedssystem.model.property.building.FloorDescription;
import com.lyhorng.pedssystem.model.property.land.Land;
import com.lyhorng.pedssystem.model.agency.Agency;
import com.lyhorng.pedssystem.model.branchRequest.BranchRequest;
import com.lyhorng.pedssystem.model.customer.Customer;
import com.lyhorng.pedssystem.repository.property.*;
import com.lyhorng.pedssystem.repository.property.building.BuildingRepository;
import com.lyhorng.pedssystem.repository.property.building.BuildingTypeRepository;
import com.lyhorng.pedssystem.repository.property.building.FloorAreaRepository;
import com.lyhorng.pedssystem.repository.property.building.FloorDescriptionRepository;
import com.lyhorng.pedssystem.repository.property.building.SourceTypeRepository;
import com.lyhorng.pedssystem.repository.property.land.FlatUnitTypeRepository;
import com.lyhorng.pedssystem.repository.property.land.LandRepository;
import com.lyhorng.pedssystem.repository.property.land.LandShapeRepository;
import com.lyhorng.pedssystem.repository.property.land.TypeOfLotRepository;
import com.lyhorng.pedssystem.repository.property.location.CommuneRepository;
import com.lyhorng.pedssystem.repository.property.location.DistrictRepository;
import com.lyhorng.pedssystem.repository.property.location.ProvinceRepository;
import com.lyhorng.pedssystem.repository.property.location.VillageRepository;
import com.lyhorng.pedssystem.repository.customer.CustomerRepository;
import com.lyhorng.pedssystem.repository.agency.AgencyRepository;
import com.lyhorng.pedssystem.repository.branchRequest.BranchRequestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private final LandRepository landRepository;
    private final LandShapeRepository landShapeRepository;
    private final TypeOfLotRepository typeOfLotRepository;
    private final FlatUnitTypeRepository flatUnitTypeRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final FloorDescriptionRepository floorDescriptionRepository;
    private final FloorAreaRepository floorAreaRepository;
    private final SourceTypeRepository sourceTypeRepository;
    private final AgencyRepository agencyRepository;
    private final BuildingRepository buildingRepository;

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

        // Create and save Land (1-to-1 relationship)
        if (isLandDataProvided(requestDto.getLand())) {
            // log.info("Land data provided, creating land for property: {}", savedProperty.getApplicationCode());
            Land land = createLandFromDto(requestDto.getLand(), savedProperty);
            savedProperty.setLand(land);
            landRepository.save(land);
        }

        // Create and save Buildings (1-to-Many relationship)
        if (requestDto.getBuildings() != null && !requestDto.getBuildings().isEmpty()) {
            for (BuildingRequestDto buildingDto : requestDto.getBuildings()) {
                Building building = createBuildingFromDto(buildingDto, savedProperty);
                savedProperty.getBuildings().add(building);
            }
        }

        log.info("Property created successfully with application code: {}", savedProperty.getApplicationCode());

        return convertToResponseDto(savedProperty);
    }

    private boolean isLandDataProvided(LandRequestDto landDto) {
        if (landDto == null) {
            return false;
        }

        // Check if any meaningful data is provided
        return landDto.getShapeId() != null ||
                landDto.getTypeOfLotId() != null ||
                landDto.getLandSize() != null ||
                landDto.getLength() != null ||
                landDto.getWidth() != null;
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

        // Update Land
        if (property.getLand() != null) {
            updateLandFromDto(requestDto.getLand(), property.getLand());
        } else {
            Land land = createLandFromDto(requestDto.getLand(), property);
            property.setLand(land);
        }

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
    public Page<PropertyResponseDto> getAllProperties(int page, int size) {
        log.info("Fetching all properties - page: {}, size: {}", page, size);

        // Convert 1-based page to 0-based for Spring Data
        int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, size);

        // Fetch paginated data from repository
        Page<Property> propertyPage = propertyRepository.findAll(pageable);

        // Convert Page<Property> to Page<PropertyResponseDto>
        return propertyPage.map(this::convertToResponseDto);
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
            code = generateRandomCode(11);
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

        // Land (1-to-1)
        if (property.getLand() != null) {
            dto.setLand(convertLandToResponseDto(property.getLand()));
        }

        // Buildings (1-to-Many) grouped by source type
        if (property.getBuildings() != null && !property.getBuildings().isEmpty()) {
            BuildingsBySourceDto buildingsBySource = new BuildingsBySourceDto();
            BuildingTotalsDto totals = new BuildingTotalsDto();

            totals.setTotalBuildings(property.getBuildings().size());
            BigDecimal grandTotalMFA = BigDecimal.ZERO;
            BigDecimal grandTotalAFA = BigDecimal.ZERO;

            for (Building building : property.getBuildings()) {
                BuildingResponseDto buildingDto = convertBuildingToResponseDto(building);

                // Get source type name and group accordingly
                if (building.getSourceType() != null) {
                    String sourceTypeName = building.getSourceType().getName();

                    // Use if-else instead of switch for entity comparison
                    if ("BRANCH".equals(sourceTypeName)) {
                        buildingsBySource.getBranch().add(buildingDto);
                    } else if ("SME".equals(sourceTypeName)) {
                        buildingsBySource.getSme().add(buildingDto);
                    } else if ("MEGA".equals(sourceTypeName)) {
                        buildingsBySource.getMega().add(buildingDto);
                    } else if ("AGENCY".equals(sourceTypeName)) {
                        buildingsBySource.getAgency().add(buildingDto);
                    }
                }

                // Accumulate totals
                grandTotalMFA = grandTotalMFA
                        .add(building.getTotalMFA() != null ? building.getTotalMFA() : BigDecimal.ZERO);
                grandTotalAFA = grandTotalAFA
                        .add(building.getTotalAFA() != null ? building.getTotalAFA() : BigDecimal.ZERO);
            }

            totals.setTotalMFA(grandTotalMFA);
            totals.setTotalAFA(grandTotalAFA);
            totals.setGrandTotal(grandTotalMFA.add(grandTotalAFA));

            dto.setBuildingsBySource(buildingsBySource);
            dto.setBuildingTotals(totals);
        }

        dto.setCreatedAt(property.getCreatedAt());
        dto.setUpdatedAt(property.getUpdatedAt());

        return dto;
    }

    // ==================== LAND HELPER METHODS ====================
    private Land createLandFromDto(LandRequestDto landDto, Property property) {
        Land land = new Land();
        land.setProperty(property);

        // Set shape
        land.setShape(landShapeRepository.findById(landDto.getShapeId())
                .orElseThrow(() -> new ResourceNotFoundException("Land shape not found")));

        // Set type of lot
        land.setTypeOfLot(typeOfLotRepository.findById(landDto.getTypeOfLotId())
                .orElseThrow(() -> new ResourceNotFoundException("Type of lot not found")));

        // Set measurements
        land.setLandSize(landDto.getLandSize());
        land.setLength(landDto.getLength());
        land.setWidth(landDto.getWidth());
        land.setFront(landDto.getFront());
        land.setBack(landDto.getBack());

        // Set flat/unit type if provided
        if (landDto.getFlatUnitTypeId() != null) {
            land.setFlatUnitType(flatUnitTypeRepository.findById(landDto.getFlatUnitTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Flat unit type not found")));
        }

        land.setNumberOfLot(landDto.getNumberOfLot());

        return landRepository.save(land);
    }

    private void updateLandFromDto(LandRequestDto landDto, Land land) {
        // Update shape
        land.setShape(landShapeRepository.findById(landDto.getShapeId())
                .orElseThrow(() -> new ResourceNotFoundException("Land shape not found")));

        // Update type of lot
        land.setTypeOfLot(typeOfLotRepository.findById(landDto.getTypeOfLotId())
                .orElseThrow(() -> new ResourceNotFoundException("Type of lot not found")));

        // Update measurements
        land.setLandSize(landDto.getLandSize());
        land.setLength(landDto.getLength());
        land.setWidth(landDto.getWidth());
        land.setFront(landDto.getFront());
        land.setBack(landDto.getBack());

        // Update flat/unit type
        if (landDto.getFlatUnitTypeId() != null) {
            land.setFlatUnitType(flatUnitTypeRepository.findById(landDto.getFlatUnitTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Flat unit type not found")));
        } else {
            land.setFlatUnitType(null);
        }

        land.setNumberOfLot(landDto.getNumberOfLot());

        landRepository.save(land);
    }

    private LandResponseDto convertLandToResponseDto(Land land) {
        LandResponseDto dto = new LandResponseDto();
        dto.setId(land.getId());

        // Shape
        if (land.getShape() != null) {
            LandShapeDto shapeDto = new LandShapeDto();
            shapeDto.setId(land.getShape().getId());
            shapeDto.setShapeName(land.getShape().getShapeName());
            dto.setShape(shapeDto);
        }

        // Type of Lot
        if (land.getTypeOfLot() != null) {
            TypeOfLotDto lotDto = new TypeOfLotDto();
            lotDto.setId(land.getTypeOfLot().getId());
            lotDto.setLotTypeName(land.getTypeOfLot().getLotTypeName());
            dto.setTypeOfLot(lotDto);
        }

        // Measurements
        dto.setLandSize(land.getLandSize());
        dto.setLength(land.getLength());
        dto.setWidth(land.getWidth());
        dto.setFront(land.getFront());
        dto.setBack(land.getBack());
        dto.setDimensionLand(land.getDimensionLand());

        // Flat/Unit Type
        if (land.getFlatUnitType() != null) {
            FlatUnitTypeDto unitTypeDto = new FlatUnitTypeDto();
            unitTypeDto.setId(land.getFlatUnitType().getId());
            unitTypeDto.setUnitTypeName(land.getFlatUnitType().getUnitTypeName());
            dto.setFlatUnitType(unitTypeDto);
        }

        dto.setNumberOfLot(land.getNumberOfLot());
        dto.setCreatedAt(land.getCreatedAt());
        dto.setUpdatedAt(land.getUpdatedAt());

        return dto;
    }

    // ==================== BUILDING HELPER METHODS ====================
    private Building createBuildingFromDto(BuildingRequestDto buildingDto, Property property) {
        Building building = new Building();
        building.setProperty(property);

        // Set source type
        BuildingSourceType sourceType = sourceTypeRepository
                .findById(buildingDto.getSourceTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Source type not found with id: " + buildingDto.getSourceTypeId()));
        building.setSourceType(sourceType);

        // Set agency if provided (only for AGENCY source type)
        if (buildingDto.getAgencyId() != null) {
            if (!"AGENCY".equals(sourceType.getName())) {
                throw new IllegalArgumentException("Agency can only be set when source type is AGENCY");
            }

            Agency agency = agencyRepository.findById(buildingDto.getAgencyId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Agency not found with id: " + buildingDto.getAgencyId()));
            building.setAgency(agency);
        } else if ("AGENCY".equals(sourceType.getName())) {
            throw new IllegalArgumentException("Agency is required when source type is AGENCY");
        }

        building.setBuildingEvaluation(buildingDto.getBuildingEvaluation());

        // Set building type
        building.setBuildingType(buildingTypeRepository.findById(buildingDto.getBuildingTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Building type not found")));

        building.setBuildingUsage(buildingDto.getBuildingUsage());
        building.setBuildingStories(buildingDto.getBuildingStories());
        building.setBuildingSizeUnit(buildingDto.getBuildingSizeUnit());
        building.setBuildingYearBuilt(buildingDto.getBuildingYearBuilt());
        building.setRemark(buildingDto.getRemark());

        // Save building first to get ID
        Building savedBuilding = buildingRepository.save(building);

        // Create floor areas (with null check)
        if (buildingDto.getFloorAreas() != null && !buildingDto.getFloorAreas().isEmpty()) {
            for (FloorAreaRequestDto floorDto : buildingDto.getFloorAreas()) {
                FloorArea floorArea = createFloorAreaFromDto(floorDto, savedBuilding);
                savedBuilding.addFloorArea(floorArea);
            }
        }

        // Calculate totals
        savedBuilding.calculateTotals();

        return buildingRepository.save(savedBuilding);
    }

    private FloorArea createFloorAreaFromDto(FloorAreaRequestDto floorDto, Building building) {
        FloorArea floorArea = new FloorArea();
        floorArea.setBuilding(building);
        floorArea.setFloorType(floorDto.getFloorType());

        // Set floor description
        FloorDescription floorDescription = floorDescriptionRepository.findById(floorDto.getFloorDescriptionId())
                .orElseThrow(() -> new ResourceNotFoundException("Floor description not found"));

        // Validate that floor description type matches floor type
        if (floorDescription.getFloorType() != floorDto.getFloorType()) {
            throw new IllegalArgumentException(
                    String.format("Floor description type %s does not match floor type %s",
                            floorDescription.getFloorType(), floorDto.getFloorType()));
        }

        floorArea.setFloorDescription(floorDescription);
        floorArea.setSize(floorDto.getSize());
        floorArea.setLength(floorDto.getLength());
        floorArea.setDisplayOrder(floorDto.getDisplayOrder());

        return floorAreaRepository.save(floorArea);
    }

    private BuildingResponseDto convertBuildingToResponseDto(Building building) {
        BuildingResponseDto dto = new BuildingResponseDto();
        dto.setId(building.getId());

        // Source type
        if (building.getSourceType() != null) {
            SourceTypeSummaryDto sourceTypeDto = new SourceTypeSummaryDto();
            sourceTypeDto.setId(building.getSourceType().getId());
            sourceTypeDto.setName(building.getSourceType().getName());
            sourceTypeDto.setDisplayName(building.getSourceType().getDisplayName());
            dto.setSourceType(sourceTypeDto);
        }

        // Agency (if applicable)
        if (building.getAgency() != null) {
            AgencySummaryDto agencyDto = new AgencySummaryDto();
            agencyDto.setId(building.getAgency().getId());
            agencyDto.setName(building.getAgency().getName());
            agencyDto.setDisplayName(building.getAgency().getDisplayName());
            dto.setAgency(agencyDto);
        }

        dto.setBuildingEvaluation(building.getBuildingEvaluation());

        // Building type
        if (building.getBuildingType() != null) {
            BuildingTypeDto typeDto = new BuildingTypeDto();
            typeDto.setId(building.getBuildingType().getId());
            typeDto.setTypeName(building.getBuildingType().getTypeName());
            dto.setBuildingType(typeDto);
        }

        dto.setBuildingUsage(building.getBuildingUsage());
        dto.setBuildingStories(building.getBuildingStories());
        dto.setBuildingSizeUnit(building.getBuildingSizeUnit());
        dto.setBuildingYearBuilt(building.getBuildingYearBuilt());
        dto.setRemark(building.getRemark());

        // Separate floor areas by type
        List<FloorAreaResponseDto> mainFloors = new ArrayList<>();
        List<FloorAreaResponseDto> ancillaryFloors = new ArrayList<>();

        if (building.getFloorAreas() != null) {
            for (FloorArea floorArea : building.getFloorAreas()) {
                FloorAreaResponseDto floorDto = convertFloorAreaToResponseDto(floorArea);

                if (floorArea.getFloorType() == FloorAreaType.MAIN) {
                    mainFloors.add(floorDto);
                } else {
                    ancillaryFloors.add(floorDto);
                }
            }
        }

        dto.setMainFloorAreas(mainFloors);
        dto.setAncillaryFloorAreas(ancillaryFloors);

        // Summary
        BuildingSummaryDto summary = new BuildingSummaryDto();
        summary.setTotalMFA(building.getTotalMFA());
        summary.setTotalAFA(building.getTotalAFA());
        summary.setAccommodationUnit(building.getAccommodationUnit());
        summary.setTotalApproximately(building.getTotalApproximately());
        dto.setSummary(summary);

        dto.setCreatedAt(building.getCreatedAt());
        dto.setUpdatedAt(building.getUpdatedAt());

        return dto;
    }

    private FloorAreaResponseDto convertFloorAreaToResponseDto(FloorArea floorArea) {
        FloorAreaResponseDto dto = new FloorAreaResponseDto();
        dto.setId(floorArea.getId());
        dto.setFloorType(floorArea.getFloorType());

        // Floor description
        if (floorArea.getFloorDescription() != null) {
            FloorDescriptionDto descDto = new FloorDescriptionDto();
            descDto.setId(floorArea.getFloorDescription().getId());
            descDto.setDescriptionName(floorArea.getFloorDescription().getDescriptionName());
            descDto.setFloorType(floorArea.getFloorDescription().getFloorType());
            dto.setFloorDescription(descDto);
        }

        dto.setSize(floorArea.getSize());
        dto.setLength(floorArea.getLength());
        dto.setStructure(floorArea.getStructure());
        dto.setDisplayOrder(floorArea.getDisplayOrder());
        dto.setCreatedAt(floorArea.getCreatedAt());
        dto.setUpdatedAt(floorArea.getUpdatedAt());

        return dto;
    }
}