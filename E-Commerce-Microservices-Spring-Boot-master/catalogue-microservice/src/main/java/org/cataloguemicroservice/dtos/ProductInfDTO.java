package org.cataloguemicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cataloguemicroservice.entities.Product;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductInfDTO {
    private String rootCategorySlug;
    private String SubCategorySlug;
    private Product product;
}
