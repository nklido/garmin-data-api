package com.nklido.garmin.data.api.adapter.garmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserConnectionResponseDto {

    @JsonProperty("userConnections")
    private List<UserConnectionDto> userConnections = new ArrayList<>();
}
