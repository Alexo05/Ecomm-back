package org.cataloguemicroservice.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cataloguemicroservice.dtos.CategoriesTree;
import org.cataloguemicroservice.dtos.PageRequestDTO;
import org.cataloguemicroservice.dtos.ThreeCategory;
import org.cataloguemicroservice.entities.Category;
import org.cataloguemicroservice.entities.Product;
import org.cataloguemicroservice.enums.CustomerMessageError;
import org.cataloguemicroservice.exceptions.CategoryAlreadyExistException;
import org.cataloguemicroservice.exceptions.CategoryEmptyException;
import org.cataloguemicroservice.exceptions.CategoryNotFoundException;
import org.cataloguemicroservice.exceptions.ProductNotFoundException;
import org.cataloguemicroservice.repositories.CategoryRepository;
import org.cataloguemicroservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public Category save(Category category) {
        category.setSlug(this.slugify(category.getLabel()));
        category.setCreatedDate(new Date());
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category =categoryRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        CustomerMessageError.CATEGORY_PARENT_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        categoryRepository.delete(category);
    }


    @Override
    public void add(Category category) {
        if(category.getCategoryId() == null)
            throw new CategoryEmptyException(CustomerMessageError.CATEGORY_INPUT_IS_EMPTY.getMessage());
        if( categoryRepository.existsById(category.getCategoryId()))
            throw new CategoryAlreadyExistException(CustomerMessageError.CATEGORY_ALREADY_EXISTS_WITH_ID_EQUALS.getMessage()+category.getCategoryId());
        this.save(category);
    }

    @Override
    public void update(Long id, Category p) {
        if(id == null) throw new RuntimeException("Id not Valid  " +id);
        Category category =categoryRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        CustomerMessageError.CATEGORY_PARENT_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        category.setCategoryId(p.getCategoryId());
        category.setCategoryTitle(p.getCategoryTitle());
        category.setLabel(p.getLabel());
        category.setDescription(p.getDescription());
        category.setIdParent(p.getIdParent());
        category.setImageUrl(p.getImageUrl());
        category.setValid(p.isValid());
        this.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                ()->new CategoryNotFoundException(CustomerMessageError.CATEGORY_NOT_FOUND_WITH_ID_EQUALS.getMessage()+id)
        );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Page<Category>> findAllCategoriesPageable(Pageable pageable) {
        Pageable pageable1 = PageRequest.of(0,4);
        return List.of(categoryRepository.findAll(pageable1));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new
                        CategoryNotFoundException(
                                CustomerMessageError.CATEGORY_NOT_FOUND_WITH_ID_EQUALS.getMessage()+ id));
    }

    @Override
    public Category getCategoryByIdParent(Long idParent) {
        return categoryRepository.findById(idParent)
                .orElseThrow(() -> new
                        CategoryNotFoundException(
                                CustomerMessageError.CATEGORY_NOT_FOUND_WITH_ID_PARENT_EQUALS.getMessage()
                                        +idParent));
    }

    @Override
    public Category getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new
                        CategoryNotFoundException(
                                CustomerMessageError.CATEGORY_NOT_FOUND_SLUG_EQUALS.getMessage()+slug));
    }

    @Override
    public Category getCategoryByLabel(String label) {
        return categoryRepository.findByLabel(label)
                .orElseThrow(() -> new
                        CategoryNotFoundException(
                                CustomerMessageError.CATEGORY_NOT_FOUND_WITH_LABEL_EQUALS.getMessage()+label));
    }

    @Override
    public List<Category> getParentCategory() {
        return categoryRepository.findCategoriesByIdParent(0L);
    }

    public ThreeCategory getHierarchyCategories(){
        ThreeCategory threeCategory = new ThreeCategory();
        List<Category> rootCategoryList = categoryRepository.findCategoriesByIdParent(0L);
        for (Category cat : rootCategoryList) {
            CategoriesTree categoriesTree = new CategoriesTree();
            categoriesTree.setRootCategory(cat);
            List<Category> subCategories = categoryRepository.findByIdParent(cat.getCategoryId());
            categoriesTree.setChildren(subCategories);
            threeCategory.getCategoriesTrees().add(categoriesTree);
        }
        return threeCategory;
    }

    @Override
    public PageRequestDTO<Category> getCategoryPagination(Integer pageNumber, Integer pageSize, String sort) {
        Pageable pageable;
        if (sort != null) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sort);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        Page<Category> productPage = categoryRepository.findAll(pageable);
        return new PageRequestDTO<>(
                productPage.getContent(),
                productPage.getNumber(),
                productPage.getTotalPages(),
                (int) productPage.getTotalElements()
        );
    }

}
