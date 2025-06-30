package org.cataloguemicroservice.repositories;

import org.cataloguemicroservice.base.BaseRepositories;
import org.cataloguemicroservice.entities.Product;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository  extends BaseRepositories<Product,Long> {
    List<Product> findProductsByIdCategory(Long idCategory);
    boolean existsByProductId(Long id);
}
