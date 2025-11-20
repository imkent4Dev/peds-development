package com.lyhorng.pedssystem.repository.property.land;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyhorng.pedssystem.model.property.land.TypeOfLot;

@Repository
public interface TypeOfLotRepository extends JpaRepository<TypeOfLot, Long> {
    Optional<TypeOfLot> findByLotTypeName(String lotTypeName);
}