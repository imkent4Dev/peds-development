package com.lyhorng.pedssystem.repository.property.location;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lyhorng.pedssystem.model.property.location.Village;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {
    // Find villages by commune ID
    List<Village> findByCommuneId(Long communeId);
    
    // Optional: Find villages by district ID directly
    List<Village> findByDistrictId(Long districtId);
    
    // Optional: Find villages by province ID directly
    List<Village> findByProvinceId(Long provinceId);
}
