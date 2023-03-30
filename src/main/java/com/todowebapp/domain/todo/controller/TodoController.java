package com.todowebapp.domain.todo.controller;

import com.todowebapp.dto.ResponseDTO;
import com.todowebapp.domain.todo.dto.TodoDTO;
import com.todowebapp.domain.todo.domain.Todo;
import com.todowebapp.domain.todo.service.TodoService;
import com.todowebapp.enums.ResponseEnum;
import com.todowebapp.handler.ResponseHandler;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "TODO API", tags = "TODO API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TodoController {

    private final TodoService todoService;
    private final ResponseHandler responseHandler;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        List<String> list = new ArrayList<>();
        list.add(todoService.testService());
        return responseHandler.ok(list);
    }

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO){

        ResponseEnum response = todoService.create(todoDTO);

        if(response != ResponseEnum.OK){
            responseHandler.fail(response.getKey(), response.getValue());
        }
        return responseHandler.ok();
    }

    @GetMapping("/todo")
    public ResponseEntity<?> retrieveTodoList(){

//        List<Todo> entities = todoService.retrieve(userId);

//        List<TodoDTO> dtos = entities.stream()
//                .map(TodoDTO::new).collect(Collectors.toList());

//        return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder().data(dtos).build());
        return null;
    }

    @PutMapping("/todo")
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {

//        Todo entity = TodoDTO.toEntity(dto);
//
//
//        List<Todo> entities = todoService.update(entity);
//
//        return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder()
//                .data(entities.stream().map(TodoDTO::new).collect(Collectors.toList())).build());
        return null;
    }

    @DeleteMapping("/todo")
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
//        try {
//            Todo entity = TodoDTO.toEntity(dto);
//            List<Todo> entities = todoService.delete(entity);
//
//            return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder()
//                    .data(entities.stream().map(TodoDTO::new).collect(Collectors.toList())).build());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(ResponseDTO.<TodoDTO>builder().error(e.getMessage()).build());
//        }
        return null;
    }
}
