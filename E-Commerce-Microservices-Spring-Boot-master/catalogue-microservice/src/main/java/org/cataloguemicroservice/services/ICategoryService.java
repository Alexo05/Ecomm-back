package org.cataloguemicroservice.services;

import org.cataloguemicroservice.base.BaseService;
import org.cataloguemicroservice.dtos.PageRequestDTO;
import org.cataloguemicroservice.dtos.ThreeCategory;
import org.cataloguemicroservice.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService extends BaseService<Category> {
    Category findById(Long id);
    List<Category> getAllCategories();
    List<Page<Category>> findAllCategoriesPageable(Pageable pageable);
    Category getCategoryById(Long id);
    Category getCategoryByIdParent(Long idParent);
    Category getCategoryBySlug(String slug);
    Category getCategoryByLabel(String label);
    List<Category> getParentCategory();
    ThreeCategory getHierarchyCategories();
    PageRequestDTO<Category> getCategoryPagination(Integer pageNumber, Integer pageSize, String sort);
}
