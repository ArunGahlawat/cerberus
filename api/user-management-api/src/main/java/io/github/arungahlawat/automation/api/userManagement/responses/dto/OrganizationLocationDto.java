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
public class OrganizationLocationDto {
    private Long startTime;
    private String contactName;
    private String locationDetails;
    private Long updatedAt;
    private Long endTime;
    private String name;
    private Long createdAt;
    private String phoneNumber;
    private String imagesList;
    private String landmark;
    private String type;
    private Long contactNumber;
}
