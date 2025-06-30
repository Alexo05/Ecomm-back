package org.cataloguemicroservice.base;

import java.util.List;
public interface BaseMapper<E,D> {
    E toEntity(D dto);
    D toDto(E ent);
    List<E> toDtoList(List<D> dto);
    List<D> toEntityList(List<E> ent);
}
