package com.nklido.garmin.data.api.core.services;

import com.nklido.garmin.data.api.adapter.garmin.Client;
import com.nklido.garmin.data.api.adapter.garmin.dto.ActivityResponseDto;
import com.nklido.garmin.data.api.core.model.Activity;
import com.nklido.garmin.data.api.core.model.EffortLabel;
import com.nklido.garmin.data.api.fixtures.ActivityDtoFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class GarminServiceTest {

    @MockBean
    private Client garminClient;

    @Autowired
    private GarminService garminService;

    @Test
    void testGetActivities() throws IOException {
        ActivityResponseDto response = ActivityDtoFactory.getList();
        when(garminClient.getActivities("example-display-name", 0, 10)).thenReturn(response);

        List<Activity> activities = garminService.getActivities("example-display-name",0, 10);

        assertThat(activities).isNotEmpty();
        Activity activity = activities.get(0);
        assertThat(activity.getName()).isEqualTo("Athens Running");
        assertThat(activity.getEffortLabel()).isEqualTo(EffortLabel.RECOVERY);
        assertThat(activity.getAveragePace()).isEqualTo("6:48");
    }

}