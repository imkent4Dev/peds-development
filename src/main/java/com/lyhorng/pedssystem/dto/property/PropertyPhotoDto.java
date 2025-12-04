package com.lyhorng.pedssystem.dto.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple DTO for property photos.
 * All fields are optional on request; id is only used on response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyPhotoDto {

    private Long id;
    private String photoType; // FRONT / LEFT / RIGHT / INSIDE
    private String filePath;  // MinIO path or URL
    private Integer displayOrder;
}


