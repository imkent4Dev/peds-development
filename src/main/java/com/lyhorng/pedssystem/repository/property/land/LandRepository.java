package com.lyhorng.pedssystem.repository.property.land;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyhorng.pedssystem.model.property.land.Land;

public interface LandRepository extends JpaRepository<Land,Long>{
     Optional<Land> findByPropertyId(Long propertyId);
}
