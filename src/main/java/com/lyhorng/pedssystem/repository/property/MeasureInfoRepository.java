package com.lyhorng.pedssystem.repository.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lyhorng.pedssystem.model.property.PropertyMeasureInfo;

@Repository
public interface MeasureInfoRepository extends JpaRepository<PropertyMeasureInfo, Long> {

}
