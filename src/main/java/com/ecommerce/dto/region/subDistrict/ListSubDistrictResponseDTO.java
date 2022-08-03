package com.ecommerce.dto.region.subDistrict;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ListSubDistrictResponseDTO {

    private List<SubDistrictDTO> subDistrictDTOList;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean last;
}
