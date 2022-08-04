package com.ecommerce.dto.product;


import com.ecommerce.dto.category.CategoryDTO;
import com.ecommerce.dto.productdetail.ProductDetailDTO;
import com.ecommerce.dto.supplier.SupplierDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProductDTO {

    private String id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private ProductDetailDTO productDetail;
    private CategoryDTO category;
    private Set<SupplierDTO> suppliers = new HashSet<>();
}
