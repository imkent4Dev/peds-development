package com.lyhorng.pedssystem.repository.property;

import com.lyhorng.pedssystem.model.property.Property;
import com.lyhorng.pedssystem.enums.EvaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    
    // Find by application code
    Optional<Property> findByApplicationCode(String applicationCode);
    
    // Find by existing application code
    Optional<Property> findByExistApplicationCode(String existApplicationCode);
    
    // Find by evaluation status
    List<Property> findByEvaStatus(EvaStatus evaStatus);
    
    // Find by owner
    @Query("SELECT p FROM Property p WHERE p.owner.id = :ownerId")
    List<Property> findByOwnerId(@Param("ownerId") Long ownerId);
    
    // Find by co-owner
    @Query("SELECT p FROM Property p WHERE p.coOwner.id = :coOwnerId")
    List<Property> findByCoOwnerId(@Param("coOwnerId") Long coOwnerId);
    
    // Find by branch request
    @Query("SELECT p FROM Property p WHERE p.branchRequest.id = :branchRequestId")
    List<Property> findByBranchRequestId(@Param("branchRequestId") Long branchRequestId);
    
    // Find by province
    @Query("SELECT p FROM Property p WHERE p.province.id = :provinceId")
    List<Property> findByProvinceId(@Param("provinceId") Long provinceId);
    
    // Find by district
    @Query("SELECT p FROM Property p WHERE p.district.id = :districtId")
    List<Property> findByDistrictId(@Param("districtId") Long districtId);
    
    // Find by category
    @Query("SELECT p FROM Property p WHERE p.category.id = :categoryId")
    List<Property> findByCategoryId(@Param("categoryId") Long categoryId);
    
    // Find active properties (keep record = true)
    List<Property> findByIsKeepRecordEvaluationTrue();
    
    // Find inactive properties (keep record = false)
    List<Property> findByIsKeepRecordEvaluationFalse();
    
    // Check if application code exists
    boolean existsByApplicationCode(String applicationCode);
}