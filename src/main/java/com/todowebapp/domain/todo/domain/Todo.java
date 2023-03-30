package com.todowebapp.domain.todo.domain;

import com.todowebapp.domain.todo.dto.TodoDTO;
import com.todowebapp.domain.users.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_id")
    private Long id;

    @Column(name = "todo_title")
    private String title;

    @Column(name = "todo_contents")
    private String contents;

    @Column(name="todo_done")
    private boolean done;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id")
   private Users users;

    @Builder
    public Todo(Long id, String title, String contents, boolean done, Users users) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.done = done;
        this.users = users;
    }

    public static Todo createTodo(TodoDTO todoDTO) {
        return Todo.builder()
                .title(todoDTO.getTitle())
                .contents(todoDTO.getContents())
                .users(Users.builder().seq(todoDTO.getUsersSeq()).build())
                .build();
    }
}
