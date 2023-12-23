package io.github.arungahlawat.automation.api.userManagement.responses.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.arungahlawat.automation.api.userManagement.responses.ConglomerateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubsidiaryDto {
    private ConglomerateResponse conglomerate;
    private Long createdAt;
    private String createdBy;
    private String name;
    private String normalizedName;
    private String organizationType;
    private String panNumber;
    private String registeredOfficeAddress;
    private String status;
    private Long updatedAt;
    private String updatedBy;
    private Long id;
}
