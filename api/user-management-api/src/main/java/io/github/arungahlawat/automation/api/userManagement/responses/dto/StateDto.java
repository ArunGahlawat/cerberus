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
public class StateDto {
    private Long createdAt;
    private String displayName;
    private String stateCode;
    private String stateName;
    private Long updatedAt;
    private Long id;
}
