package com.nklido.garmin.data.api.core.model;

import lombok.Data;

@Data
public class ActivityType {

    private long id;

    private String typeKey;

    private long parentId;
}
