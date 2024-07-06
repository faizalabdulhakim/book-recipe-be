package com.faizal.book.recipe.dto.response;

import com.faizal.book.recipe.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninResponseDTO {
    private Integer id;
    private String token;
    private String type;
    private String username;
    private String role;
}
