package org.cataloguemicroservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@EqualsAndHashCode(callSuper = true)
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractEntities {
    @Id
    private Long categoryId;
    private Long idParent;
    private String label;
    @Indexed(unique = true)
    private String slug;
    private String description;
    private String categoryTitle;
    private String imageUrl;
    private boolean valid;
}
