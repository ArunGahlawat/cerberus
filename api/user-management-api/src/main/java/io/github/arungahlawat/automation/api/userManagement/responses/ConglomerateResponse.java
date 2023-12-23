package io.github.arungahlawat.automation.api.userManagement.responses;

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
public class ConglomerateResponse {
    private Long createdAt;
    private String createdBy;
    private IndustryResponse industry;
    private String name;
    private String normalizedName;
    private String status;
    private Long updatedAt;
    private String updatedBy;
    private Long id;
}
