package io.github.arungahlawat.automation.api.userManagement.responses.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CountryDto {
    private String code;
    private Long updatedAt;
    private String name;
    private Boolean active;
    private Long createdAt;
    private Integer rank;
    private Long startedAt;
    private Boolean started;
    private Long id;
}
