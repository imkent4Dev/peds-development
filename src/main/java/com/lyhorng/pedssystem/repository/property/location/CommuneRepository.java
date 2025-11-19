package com.lyhorng.pedssystem.repository.property.location;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.property.location.Commune;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
    // Custom queries can be added here if needed
    List<Commune> findByDistrictId(Long districtId);
    
    // Optional: Find communes by province ID directly
    List<Commune> findByProvinceId(Long provinceId);
}
