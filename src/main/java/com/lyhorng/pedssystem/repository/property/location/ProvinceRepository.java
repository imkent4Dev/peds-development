package com.lyhorng.pedssystem.repository.property.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.property.location.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
}
