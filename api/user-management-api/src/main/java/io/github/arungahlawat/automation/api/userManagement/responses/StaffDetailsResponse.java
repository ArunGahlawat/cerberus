package io.github.arungahlawat.automation.api.userManagement.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.arungahlawat.automation.api.userManagement.responses.dto.UserDetailsDto;
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
public class StaffDetailsResponse {
    private String billTo;
    @JsonProperty("__user_id")
    private Long userId;
    private OrganizationDetailsResponse organization;
    private String code;
    private String referrerCode;
    private String city;
    private String bankDetails;
    private String userName;
    private List<String> roles;
    private List<String> role;
    private List<String> permissions;
    private Double rating;
    private Long createdAt;
    private String language;
    private String accessLevel;
    private Boolean demoUser;
    private String userType;
    private Long updatedAt;
    private Integer numRatings;
    private String passwordHash;
    private Long id;
    private String email;
    private Boolean active;
    private String createdBy;
    private Integer version;
    private String token;
    private Boolean allowReferral;
    private String aadhaarNumber;
    private String referralCode;
    private String name;
    private String updatedBy;
    private Long phoneNumber;
    private String childId;
    private String organizationId;
    private List<String> extraPerm;
    private UserDetailsDto userDetails;
    private Integer twofaRetryCount;
    private Integer backupcodeRetryCount;
    private Object twofaSecret;
    private Object twofaLastWrongOtp;
    private String twofaState;
    private String refreshToken;
}