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
public class VerticalDto {
    private Long createdAt;
    private String createdBy;
    private String name;
    private String normalizedName;
    private String status;
    private SubsidiaryDto subsidiary;
    private Long updatedAt;
    private String updatedBy;
    private Long id;
    private String businessType;
}