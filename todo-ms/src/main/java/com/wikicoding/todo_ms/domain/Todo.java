package com.wikicoding.todo_ms.domain;

import com.wikicoding.todo_ms.domain.ddd.AggregateRoot;
import com.wikicoding.todo_ms.domain.value_objects.TodoComplete;
import com.wikicoding.todo_ms.domain.value_objects.TodoDescr;
import com.wikicoding.todo_ms.domain.value_objects.TodoId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class Todo implements AggregateRoot {
    @Setter
    private TodoId todoId;
    private final TodoDescr todoDescr;
    private TodoComplete todoComplete;
}
