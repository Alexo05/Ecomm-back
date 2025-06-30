package org.cataloguemicroservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public abstract class AbstractEntities {

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
