package com.nklido.garmin.data.api.adapter.garmin;

import com.nklido.garmin.data.api.adapter.garmin.dto.*;
import com.nklido.garmin.data.api.security.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;


@Component
public class Client {

    @Value("${garmin.api-url}")
    private String garminBaseUrl;

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final String ACTIVITIES_ENDPOINT = "/activitylist-service/activities";

    private static final String ACTIVITIES_SEARCH_ENDPOINT = "/activitylist-service/activities/search/activities";

    private static final String ACTIVITY_TYPES_ENDPOINT = "/activity-service/activity/activityTypes";

    private static final String USER_PROFILE_ENDPOINT = "/userprofile-service/socialProfile";

    private static final String USER_CONNECTIONS_ENDPOINT = "/connection-service/connection/connections/pagination";

    @Autowired
    @Qualifier("garminRestTemplate")
    private RestTemplate garminRestTemplate;

    @Autowired
    private RequestContext requestContext;

    public ActivityResponseDto getActivities(String displayName, Integer start, Integer limit) {
        String url = UriComponentsBuilder.fromHttpUrl(garminBaseUrl + ACTIVITIES_ENDPOINT + "/" + displayName)
                .queryParam("start", start)
                .queryParam("limit", limit)
                .toUriString();

        return garminRestTemplate
                .exchange(url, HttpMethod.GET, null, ActivityResponseDto.class)
                .getBody();
    }

    public List<ActivityDto> getActivitiesByDateRange(LocalDate startDate, LocalDate endDate) {
        String url = UriComponentsBuilder.fromHttpUrl(garminBaseUrl + ACTIVITIES_SEARCH_ENDPOINT)
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .queryParam("start", 0)
                .queryParam("limit", 100)
                .toUriString();

        return garminRestTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ActivityDto>>() {
                })
                .getBody();
    }

    public List<ActivityTypeDto> getActivityTypes() {
        String url = UriComponentsBuilder
                .fromHttpUrl(garminBaseUrl + ACTIVITY_TYPES_ENDPOINT)
                .toUriString();

        return garminRestTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ActivityTypeDto>>() {})
                .getBody();
    }

    public UserProfileDto getUser(String displayName) {
        String url = UriComponentsBuilder
                .fromHttpUrl(garminBaseUrl + USER_PROFILE_ENDPOINT + "/" + displayName)
                .toUriString();

        return garminRestTemplate.exchange(url, HttpMethod.GET, null, UserProfileDto.class).getBody();
    }

    public UserConnectionResponseDto getUserConnections(String displayName) {
        String url = UriComponentsBuilder
                .fromHttpUrl(garminBaseUrl + USER_CONNECTIONS_ENDPOINT + "/" + displayName)
                .queryParam("start", 0)
                .queryParam("limit", 1000)
                .toUriString();

        return garminRestTemplate.exchange(url, HttpMethod.GET, null, UserConnectionResponseDto.class).getBody();
    }
}


