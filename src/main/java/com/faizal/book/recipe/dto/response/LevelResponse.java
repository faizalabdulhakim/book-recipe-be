package com.faizal.book.recipe.dto.response;

import com.faizal.book.recipe.model.Level;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LevelResponse {
    @JsonProperty("level_id")
    private Integer levelId;

    @JsonProperty("level_name")
    private String levelName;

    public static LevelResponse createFromModel(Level level) {
        return LevelResponse.builder()
                .levelId(level.getId())
                .levelName(level.getLevelName())
                .build();
    }
}
