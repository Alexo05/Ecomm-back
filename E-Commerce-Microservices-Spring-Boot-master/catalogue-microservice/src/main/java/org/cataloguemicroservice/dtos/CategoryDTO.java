package org.cataloguemicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Integer categoryId;
    private Integer idParent;
    private String label;
    private String slug;
    private String categoryTitle;
    private String imageUrl;
    private boolean valid;
}
