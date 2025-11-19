package com.lyhorng.pedssystem.repository.property.location;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.property.location.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    // Custom queries can be added here if needed
    List<District> findByProvinceId(Long provinceId);
}
