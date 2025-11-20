package com.lyhorng.pedssystem.dto.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private ProvinceSummaryDto province;
    private DistrictSummaryDto district;
    private CommuneSummaryDto commune;
    private VillageSummaryDto village;
}
