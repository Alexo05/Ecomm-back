package org.cataloguemicroservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends AbstractEntities {
    @Id
    private Long productId;
    private String label;
    @Indexed(unique = true)
    private String slug;
    private String productTitle;
    private String imageUrl;
    private Long price;
    private String description;
    private Long idCategory;
    private boolean valid;
}
