package com.todowebapp.controller;

import com.todowebapp.dto.ResponseDTO;
import com.todowebapp.dto.TodoDTO;
import com.todowebapp.model.TodoEntity;
import com.todowebapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
        try {
            String temporaryUserId = "temporary-user"; // temporary user id
            TodoEntity entity = TodoDTO.toEntity(dto);

            entity.setId(null);
            entity.setUserId(temporaryUserId);
            List<TodoEntity> entities = todoService.create(entity);

            List<TodoDTO> dtos = entities.stream()
                    .map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO.<TodoDTO>builder().error(e.getMessage()).build());
        }
    }

    @GetMapping("/todo")
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId = "temporary-user"; // temporary user id.

        List<TodoEntity> entities = todoService.retrieve(temporaryUserId);

        List<TodoDTO> dtos = entities.stream()
                .map(TodoDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder().data(dtos).build());
    }

    @PutMapping("/todo")
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {

        String temporaryUserId = "temporary-user";

        TodoEntity entity = TodoDTO.toEntity(dto);

        entity.setUserId(temporaryUserId);

        List<TodoEntity> entities = todoService.update(entity);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder().data(dtos).build());
    }

    @DeleteMapping("/todo")
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";

            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(temporaryUserId);

            List<TodoEntity> entities = todoService.delete(entity);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder().data(dtos).build());
        } catch (Exception e) {
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
