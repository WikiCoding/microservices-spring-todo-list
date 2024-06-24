package com.wikicoding.todo_ms.domain.ddd;

import java.util.Optional;

public interface Repository<E extends AggregateRoot, ID extends DomainId> {
    E save(E entity);

    Iterable<E> findAll();

    Optional<E> findById(ID id);

    boolean existsById(ID id);
}
