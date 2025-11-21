package com.lyhorng.pedssystem.dto.property.building;
import com.lyhorng.pedssystem.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorAreaResponseDto {
    
    private Long id;
    private FloorAreaType floorType;
    private FloorDescriptionDto floorDescription;
    private BigDecimal size;
    private BigDecimal length;
    private String structure; // Auto-calculated: "Floor name/size"
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
