package com.lyhorng.pedssystem.repository.property.building;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.property.building.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    
    /**
     * Find all buildings by property ID
     */
    List<Building> findByPropertyId(Long propertyId);
    
    /**
     * Find all buildings by source type ID
     */
    @Query("SELECT b FROM Building b WHERE b.sourceType.id = :sourceTypeId")
    List<Building> findBySourceTypeId(@Param("sourceTypeId") Long sourceTypeId);
    
    /**
     * Find all buildings by source type name
     */
    @Query("SELECT b FROM Building b WHERE b.sourceType.name = :sourceTypeName")
    List<Building> findBySourceTypeName(@Param("sourceTypeName") String sourceTypeName);
    
    /**
     * Find all buildings by agency ID
     */
    @Query("SELECT b FROM Building b WHERE b.agency.id = :agencyId")
    List<Building> findByAgencyId(@Param("agencyId") Long agencyId);
    
    /**
     * Find all buildings by property ID and source type
     */
    @Query("SELECT b FROM Building b WHERE b.property.id = :propertyId AND b.sourceType.name = :sourceTypeName")
    List<Building> findByPropertyIdAndSourceTypeName(
        @Param("propertyId") Long propertyId, 
        @Param("sourceTypeName") String sourceTypeName
    );
    
    /**
     * Find all buildings by building type ID
     */
    @Query("SELECT b FROM Building b WHERE b.buildingType.id = :buildingTypeId")
    List<Building> findByBuildingTypeId(@Param("buildingTypeId") Long buildingTypeId);
    
    /**
     * Count buildings by property ID
     */
    long countByPropertyId(Long propertyId);
    
    /**
     * Count buildings by source type
     */
    @Query("SELECT COUNT(b) FROM Building b WHERE b.sourceType.name = :sourceTypeName")
    long countBySourceTypeName(@Param("sourceTypeName") String sourceTypeName);
    
    /**
     * Count buildings by agency
     */
    @Query("SELECT COUNT(b) FROM Building b WHERE b.agency.id = :agencyId")
    long countByAgencyId(@Param("agencyId") Long agencyId);
    
    /**
     * Delete all buildings by property ID
     */
    void deleteByPropertyId(Long propertyId);
    
    /**
     * Check if building exists by property ID
     */
    boolean existsByPropertyId(Long propertyId);
}