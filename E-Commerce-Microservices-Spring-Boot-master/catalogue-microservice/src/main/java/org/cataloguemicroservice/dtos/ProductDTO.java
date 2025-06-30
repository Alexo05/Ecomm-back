package org.cataloguemicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String label;
    private String slug;
    private String productTitle;
    private String imageUrl;
    private Long price;
    private String description;
    private Long idCategory;
    private boolean valid;
}
