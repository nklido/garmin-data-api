package com.nklido.garmin.data.api.core.services;

import com.nklido.garmin.data.api.adapter.garmin.Client;
import com.nklido.garmin.data.api.core.model.Activity;
import com.nklido.garmin.data.api.core.model.ActivityType;
import com.nklido.garmin.data.api.core.model.UserConnection;
import com.nklido.garmin.data.api.core.model.UserProfile;
import com.nklido.garmin.data.api.mapper.ActivityMapper;
import com.nklido.garmin.data.api.mapper.ActivityTypeMapper;
import com.nklido.garmin.data.api.mapper.UserConnectionMapper;
import com.nklido.garmin.data.api.mapper.UserProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarminService {

    private static final Logger logger = LoggerFactory.getLogger(GarminService.class);

    @Autowired
    private Client garminClient;

    public List<Activity> getActivities(String displayName, Integer start, Integer limit) {
        return garminClient.getActivities(displayName, start, limit)
                .getActivityList()
                .stream()
                .map(ActivityMapper::toDomain)
                .toList();
    }

    public List<ActivityType> getActivityTypes() {
        return garminClient.getActivityTypes().stream().map(ActivityTypeMapper::toDomain).toList();
    }

    public UserProfile getUser(String displayName) {
        return UserProfileMapper.toDomain(garminClient.getUser(displayName));
    }

    public List<UserConnection> getUserConnections(String displayName) {
        return garminClient.getUserConnections(displayName).getUserConnections()
                .stream()
                .map(UserConnectionMapper::toDomain)
                .toList();
    }
}
