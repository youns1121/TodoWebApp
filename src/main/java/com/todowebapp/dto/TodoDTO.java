package com.todowebapp.dto;

import com.todowebapp.model.TodoEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TodoDTO {

    private String id;

    private String title;

    private boolean done;

    @Builder
    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }
}
