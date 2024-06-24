package com.wikicoding.todo_ms.domain.value_objects;

import com.wikicoding.todo_ms.domain.ddd.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class TodoComplete implements ValueObject {
    private final boolean complete;
}
