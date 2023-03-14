package com.todowebapp.domain.todo.controller;

import com.todowebapp.dto.ResponseDTO;
import com.todowebapp.domain.todo.dto.TodoDTO;
import com.todowebapp.domain.todo.domain.TodoEntity;
import com.todowebapp.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        List<String> list = new ArrayList<>();
        list.add(todoService.testService());
        return ResponseEntity.ok().body(ResponseDTO.<String>builder().data(list).build());
    }

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setId(null);
            entity.setUserId(userId);

            return ResponseEntity.ok()
                    .body(ResponseDTO.<TodoDTO>builder().data(todoService.create(entity).stream()
                    .map(TodoDTO::new)
                    .collect(Collectors.toList())).build());
        } catch (Exception e){
            return ResponseEntity.badRequest()
                    .body(ResponseDTO.<TodoDTO>builder().error(e.getMessage()).build());
        }
    }

    @GetMapping("/todo")
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){

        List<TodoEntity> entities = todoService.retrieve(userId);

        List<TodoDTO> dtos = entities.stream()
                .map(TodoDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder().data(dtos).build());
    }

    @PutMapping("/todo")
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {

        TodoEntity entity = TodoDTO.toEntity(dto);

        entity.setUserId(userId);

        List<TodoEntity> entities = todoService.update(entity);

        return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder()
                .data(entities.stream().map(TodoDTO::new).collect(Collectors.toList())).build());
    }

    @DeleteMapping("/todo")
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(userId);
            List<TodoEntity> entities = todoService.delete(entity);

            return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder()
                    .data(entities.stream().map(TodoDTO::new).collect(Collectors.toList())).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.<TodoDTO>builder().error(e.getMessage()).build());
        }
    }
}
