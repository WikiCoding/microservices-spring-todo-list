package com.wikicoding.todo_ms.repository;

import com.wikicoding.todo_ms.repository.datamodel.TodoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoModel, Integer> {
}
