package com.todowebapp.domain.todo.repository;

import com.todowebapp.domain.todo.domain.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    List<TodoEntity> findByUserId(String userId);
}
