package org.cataloguemicroservice.mappers;

import org.cataloguemicroservice.base.BaseMapper;
import org.cataloguemicroservice.dtos.CategoryDTO;
import org.cataloguemicroservice.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper  extends BaseMapper<Category,CategoryDTO> {



}
