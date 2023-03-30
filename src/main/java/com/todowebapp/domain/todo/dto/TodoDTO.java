package com.todowebapp.domain.todo.dto;

import com.todowebapp.domain.todo.domain.Todo;
import com.todowebapp.domain.users.domain.Users;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TodoDTO {

    private String title;

    private String contents;

    @ApiModelProperty(hidden = true)
    private long usersSeq;

    private boolean done;

    @Builder
    public TodoDTO(final Todo entity) {
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.done = entity.isDone();
    }

    public Todo toEntity(final TodoDTO dto){
        return Todo.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .users(Users.builder().seq(dto.getUsersSeq()).build())
                .done(dto.isDone())
                .build();
    }
}
