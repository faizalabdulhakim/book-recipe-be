package com.faizal.book.recipe.dto.response;

import com.faizal.book.recipe.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDTO {
    private String message;
    private int statusCode;
    private String status;
    private User user;
}
