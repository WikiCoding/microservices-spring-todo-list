package com.wikicoding.todo_ms.repository.datamodel;

import com.wikicoding.todo_ms.domain.Todo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo")
@NoArgsConstructor
@Data
public class TodoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "todo_todoid_seq", sequenceName = "todo_todoid_seq", allocationSize = 1)
    @Column(name = "todoid")
    private int id;
    private String description;
    private boolean completed = false;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;

    public TodoModel(Todo todo) {
        this.id = todo.getTodoId().getId();
        this.description = todo.getTodoDescr().getDescr();
        this.completed = todo.getTodoComplete().isComplete();
    }

    public void updateTodo(Todo todo) {
        this.id = todo.getTodoId().getId();
        this.description = todo.getTodoDescr().getDescr();
        this.completed = todo.getTodoComplete().isComplete();
    }
}
