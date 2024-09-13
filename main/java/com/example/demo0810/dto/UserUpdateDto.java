package com.example.demo0810.dto;

import com.example.demo0810.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private String name;
    private String email;

    public UserEntity UpdateEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .build();
    }
}
