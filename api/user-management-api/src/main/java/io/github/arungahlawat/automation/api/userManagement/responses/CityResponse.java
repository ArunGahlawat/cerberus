package io.github.arungahlawat.automation.api.userManagement.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.arungahlawat.automation.api.userManagement.responses.dto.CountryDto;
import io.github.arungahlawat.automation.api.userManagement.responses.dto.PositionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CityResponse {
    private CountryDto country;
    private Long helpline;
    private String code;
    private Boolean active;
    private Long createdAt;
    private Long driverHelpline;
    private Boolean started;
    private String defaultLanguage;
    private Boolean odActive;
    private Long updatedAt;
    private Map<String, String> languageNames;
    private String name;
    private Integer rank;
    private Long startedAt;
    private PositionDto position;
    private Long id;
}
