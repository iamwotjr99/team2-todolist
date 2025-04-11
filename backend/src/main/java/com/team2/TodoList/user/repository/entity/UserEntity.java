package com.team2.TodoList.user.repository.entity;

import com.team2.TodoList.user.domain.Password;
import com.team2.TodoList.user.domain.User;
import com.team2.TodoList.user.domain.UserInfo;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserInfoEntity userInfo;

    private String password;

    public UserEntity(User user) {
        this.id = user.getId();
        this.userInfo = new UserInfoEntity(user.getUserInfo().name(), user.getUserInfo().email());
        this.password = user.getPassword().getValue();
    }

    public User toUser() {
        return User.builder()
                .id(this.id)
                .userInfo(new UserInfo(this.userInfo.getName(), this.userInfo.getEmail()))
                .password(Password.fromEncodedValue(this.password))
                .build();
    }
}
