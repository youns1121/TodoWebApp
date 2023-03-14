package com.todowebapp.domain.todo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "todo")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_id")
    private Long id;

    @Column(name = "todo_userId")
    private String userId;

    @Column(name = "todo_title")
    private String title;

    @Column(name="todo_done")
    private boolean done;

    @Builder
    public TodoEntity(Long id, String userId, String title, boolean done) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.done = done;
    }
}
