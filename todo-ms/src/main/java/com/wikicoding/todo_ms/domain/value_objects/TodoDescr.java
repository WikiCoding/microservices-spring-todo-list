package com.wikicoding.todo_ms.domain.value_objects;

import com.wikicoding.todo_ms.domain.ddd.ValueObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class TodoDescr implements ValueObject {
    @NotBlank
    @NotEmpty
    @NotNull
    private final String descr;
}
