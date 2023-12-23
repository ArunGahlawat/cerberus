package io.github.arungahlawat.automation.api.userManagement.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginDetailsResponse {
    private List<CityResponse> cities;
    private OrganizationDetailsResponse user;
    private OrganizationDetailsResponse driver;
    private List<OrganizationDetailsResponse> organization;
}