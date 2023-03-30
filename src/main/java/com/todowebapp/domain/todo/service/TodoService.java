package com.todowebapp.domain.todo.service;

import com.todowebapp.domain.todo.dto.TodoDTO;
import com.todowebapp.domain.todo.repository.TodoRepository;
import com.todowebapp.enums.ResponseEnum;
import com.todowebapp.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final AuthenticationUtil authenticationUtil;

    public String testService(){
        return "Test Service";
    }

    public ResponseEnum create(final TodoDTO todoDTO) {

        todoDTO.setUsersSeq(authenticationUtil.getUsersSeq());
        if(todoDTO == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity be null");
        }

        todoRepository.save(todoDTO.toEntity(todoDTO));

        return ResponseEnum.OK;
    }
}
