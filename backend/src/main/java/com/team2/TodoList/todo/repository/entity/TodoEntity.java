package com.team2.TodoList.todo.repository.entity;

import com.team2.TodoList.common.domain.PositiveIntegerCount;
import com.team2.TodoList.todo.domain.Todo;
import com.team2.TodoList.todo.domain.TodoCategory;
import com.team2.TodoList.todo.domain.content.TodoContent;
import com.team2.TodoList.user.domain.User;
import com.team2.TodoList.user.repository.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "todos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    private String content;

    private Integer likeCount;

    @Convert(converter = TodoCategoryConverter.class)
    private TodoCategory category;

    @Column(nullable = false)
    private boolean done = false;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDt;

    @LastModifiedDate
    private LocalDateTime updDt;

    public TodoEntity(Todo todo) {
        this.id = todo.getId();
        this.author = new UserEntity(todo.getAuthor());
        this.content = todo.getContent();
        this.likeCount = todo.getCount();
        this.category = todo.getCategory();
        this.done = todo.isDone();
    }

    public Todo toTodo() {
        return Todo.builder()
                .id(id)
                .author(author.toUser())
                .content(new TodoContent(content))
                .category(category)
                .likeCount(new PositiveIntegerCount(likeCount))
                .done(this.done)
                .createdAt(regDt)
                .updatedAt(updDt)
                .build();
    }

}
