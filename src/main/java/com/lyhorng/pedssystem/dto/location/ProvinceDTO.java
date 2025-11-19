package com.lyhorng.pedssystem.dto.location;

import com.lyhorng.pedssystem.model.property.location.Province;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProvinceDTO {
    
    private Long id;
    private String type;
    private String code;
    private String khmerName;
    private String name;

    public ProvinceDTO(Province province) {
        this.id = province.getId();
        this.type = province.getType();
        this.code = province.getCode();
        this.khmerName = province.getKhmerName();
        this.name = province.getName();
    }
}
