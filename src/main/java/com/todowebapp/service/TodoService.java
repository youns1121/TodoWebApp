package com.todowebapp.service;

import com.todowebapp.model.TodoEntity;
import com.todowebapp.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


//    public List<TodoEntity> update(final TodoEntity entity) {
//        validate(entity);
//
//        Optional<TodoEntity> original = todoRepository.findById(entity.getId());
//        original.ifPresent(todo -> {
//            todo.setTitle(entity.getTitle());
//            todo.setDone(entity.isDone());
//
//            todoRepository.save(todo);
//        });
//
//        return retrieve(entity.getUserId());
//    }

    public List<TodoEntity> update(final TodoEntity entity) {

        validate(entity);

        Optional<TodoEntity> original = todoRepository.findById(entity.getId());
        if(original.isPresent()) {
            final TodoEntity todo = original.get();
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            todoRepository.save(todo);
        }

        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    private void validate(final TodoEntity entity){
        if(entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user.");
        }
    }
}
