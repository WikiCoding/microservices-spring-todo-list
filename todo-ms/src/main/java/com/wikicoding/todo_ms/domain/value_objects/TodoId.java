package com.wikicoding.todo_ms.domain.value_objects;

import com.wikicoding.todo_ms.domain.ddd.DomainId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class TodoId implements DomainId {
    private final int id;
}
