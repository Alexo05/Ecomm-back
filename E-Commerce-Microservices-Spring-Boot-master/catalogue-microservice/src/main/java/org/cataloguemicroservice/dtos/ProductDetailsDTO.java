package org.cataloguemicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cataloguemicroservice.entities.Category;
import org.cataloguemicroservice.entities.Product;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailsDTO {
    private Category rootCategory;
    private Category subCategory;
    private Product product;
    private List<BreadcrumbDTO> breadcrumbDTO = new ArrayList<>();

}
