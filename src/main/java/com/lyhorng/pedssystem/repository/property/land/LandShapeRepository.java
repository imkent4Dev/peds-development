package com.lyhorng.pedssystem.repository.property.land;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyhorng.pedssystem.model.property.land.LandShape;

public interface LandShapeRepository extends JpaRepository<LandShape, Long>{
    Optional<LandShape> findByShapeName(String shapeName);
}
