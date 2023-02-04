package com.yougrocery.yougrocery.authentication.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FacebookAccessTokenDataDTO(
        @JsonProperty("app_id") long appId,
        @JsonProperty("user_id") String userId,
        @JsonProperty("is_valid") boolean isValid) {
}
