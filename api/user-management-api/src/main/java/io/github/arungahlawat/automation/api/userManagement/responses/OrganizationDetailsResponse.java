package io.github.arungahlawat.automation.api.userManagement.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.arungahlawat.automation.api.userManagement.responses.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrganizationDetailsResponse {
    private BankDetailsDto bankDetails;
    private Boolean active;
    private Boolean allowReferral;
    private Boolean deactivated;
    private Boolean captureOdometer;
    private Boolean demoUser;
    private Boolean geofenceEnabled;
    private Boolean isBilled;
    private Boolean isActive;
    private Boolean isAvailable;
    private Boolean isAdhoc;
    private Boolean isBlocked;
    private Boolean isLt;
    private Boolean isMisRequired;
    private Boolean isOdBilled;
    private Boolean isPodRequired;
    private Boolean isTripsheetRequired;
    private Boolean isVerified;
    private ConglomerateResponse conglomerate;
    private Double geofenceRadius;
    private Double organizationRating;
    private GstinResponse gstin;
    private Integer numRatings;
    private Integer rating;
    private Integer tripRatio;
    private String childId;
    private Integer version;
    private List<String> attributes;
    private List<OrganizationLocationDto> locations;
    private List<String> roles;
    private List<String> role;
    private List<String> vehicleAttributes;
    private Long vehicle;
    private List<String> permissions;
    private List<String> extraPerm;
    private Long createdAt;
    private Long id;
    private Long phoneNumber;
    private Long updatedAt;
    @JsonProperty("__user_id")
    private Long userId;
    private Long vendorNumber;
    private String vehicleNumber;
    private String aadhaarNumber;
    private String abbreviation;
    private String accessLevel;
    private AddressDto address;
    private String appType;
    private String billTo;
    private String city;
    private String code;
    private String createdBy;
    private String email;
    private Map<String, Integer> invoiceNumber;
    private String language;
    private String name;
    private String officialName;
    private String pan;
    private String passwordHash;
    private String primaryContactName;
    private String primaryContactNumber;
    private String referralCode;
    private String referrerCode;
    private String serviceTaxRegistrationNumber;
    private String token;
    private String type;
    private String updatedBy;
    private String userName;
    private String userType;
    private SubsidiaryDto subsidiary;
    private UserDetailsDto userDetails;
    private VerticalDto vertical;
    private Long vehicleConfirmationCutoff;
    private OrganizationDetailsResponse organization;
    private String organizationId;
    private String child;
    private Integer twofaRetryCount;
    private Integer backupcodeRetryCount;
    private Object twofaSecret;
    private Object twofaLastWrongOtp;
    private String twofaState;
}
