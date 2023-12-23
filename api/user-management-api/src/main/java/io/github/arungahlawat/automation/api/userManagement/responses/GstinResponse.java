package io.github.arungahlawat.automation.api.userManagement.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.arungahlawat.automation.api.userManagement.responses.dto.ContactDto;
import io.github.arungahlawat.automation.api.userManagement.responses.dto.StateDto;
import io.github.arungahlawat.automation.api.userManagement.responses.dto.SubsidiaryDto;
import io.github.arungahlawat.automation.api.userManagement.responses.dto.VerticalDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GstinResponse {
    private String billingAddress;
    private ConglomerateResponse conglomerate;
    private String createdAt;
    private String createdBy;
    @JsonProperty("finance_contact_1")
    private ContactDto financeContact1;
    @JsonProperty("finance_contact_2")
    private ContactDto financeContact2;
    private String gstinNumber;
    private StateDto gstinState;
    private Boolean isDummy;
    private ContactDto operationsContact;
    private StateDto state;
    private String status;
    private SubsidiaryDto subsidiary;
    private String updatedAt;
    private String updatedBy;
    private VerticalDto vertical;
    private Long id;
}
