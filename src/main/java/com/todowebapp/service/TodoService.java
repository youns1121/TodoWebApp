package com.todowebapp.service;

import com.todowebapp.model.TodoEntity;
import com.todowebapp.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public String testService(){
        return "Test Service";
    }

    public List<TodoEntity> create(final TodoEntity entity) {

        if(entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity be null");
        }

        if (entity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }

        todoRepository.save(entity);

        log.info("Entity Id : {} is saved", entity.getId());

        return todoRepository.findByUserId(entity.getUserId());
    }
}
