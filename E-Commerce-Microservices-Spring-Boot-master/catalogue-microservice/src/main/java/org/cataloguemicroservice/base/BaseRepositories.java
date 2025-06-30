package org.cataloguemicroservice.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepositories<T, ID> extends MongoRepository<T,ID> {
    Optional<T> findBySlug(String slug);
    Optional<T> findByLabel(String categoryName);
    Page<T> findAll(Pageable pageable);
    List<T> findByValidIsTrue();
}
