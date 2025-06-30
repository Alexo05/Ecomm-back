package org.cataloguemicroservice.repositories;

import org.cataloguemicroservice.base.BaseRepositories;
import org.cataloguemicroservice.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository  extends BaseRepositories<Category,Long> {
    List<Category> findCategoriesByIdParent(Long id);
    List<Category> findByIdParent(Long id);
    List<Category> findCategoriesByCategoryIdIs(Long id);
    boolean existsByCategoryId(Long id);
}
