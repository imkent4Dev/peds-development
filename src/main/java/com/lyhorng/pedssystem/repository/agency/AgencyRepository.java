package com.lyhorng.pedssystem.repository.agency;

import com.lyhorng.pedssystem.model.agency.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    
    List<Agency> findBySourceTypeId(Long sourceTypeId);
    
    // Add this method
    @Query("SELECT a FROM Agency a WHERE a.sourceType.name = :sourceTypeCode")
    List<Agency> findBySourceTypeCode(@Param("sourceTypeCode") String sourceTypeCode);
}