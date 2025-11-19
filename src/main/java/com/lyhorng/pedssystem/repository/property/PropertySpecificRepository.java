package com.lyhorng.pedssystem.repository.property;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lyhorng.pedssystem.model.property.PropertySpecific;

public interface PropertySpecificRepository extends JpaRepository<PropertySpecific, Long> {
    // custom query methods (if needed)
}
