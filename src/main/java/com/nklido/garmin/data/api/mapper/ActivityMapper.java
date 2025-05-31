package com.nklido.garmin.data.api.mapper;

import com.nklido.garmin.data.api.adapter.garmin.dto.ActivityDto;
import com.nklido.garmin.data.api.core.model.Activity;
import com.nklido.garmin.data.api.core.model.EffortLabel;
import com.nklido.garmin.data.api.util.PaceConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ActivityMapper {
    public static Activity toDomain(ActivityDto activityDto) {
        Activity activity = new Activity();

        activity.setId(activityDto.getActivityId());
        activity.setName(Optional.ofNullable(activityDto.getActivityName())
          .orElse(""));
        activity.setStartTimeGMT(activityDto.getStartTimeGMT());
        activity.setStartTimeLocal(activityDto.getStartTimeLocal());
        activity.setDuration(activityDto.getDuration());
        activity.setDistance(activityDto.getDistance());
        activity.setAverageSpeed(Optional.ofNullable(activityDto.getAverageSpeed()).orElse(0.0));
        activity.setActivityType(ActivityTypeMapper.toDomain(activityDto.getActivityType()));
        activity.setAverageHR(Optional.ofNullable(activityDto.getAverageHR()).orElse(0.0));

        activity.setHrTimeInZone1(activityDto.getHrTimeInZone1());
        activity.setHrTimeInZone2(activityDto.getHrTimeInZone2());
        activity.setHrTimeInZone3(activityDto.getHrTimeInZone3());
        activity.setHrTimeInZone4(activityDto.getHrTimeInZone4());
        activity.setHrTimeInZone5(activityDto.getHrTimeInZone5());

        activity.setAerobicTrainingEffect(activityDto.getAerobicTrainingEffect());
        activity.setAnaerobicTrainingEffect(activityDto.getAnaerobicTrainingEffect());
        activity.setTrainingEffectLabel(activityDto.getTrainingEffectLabel());
        activity.setActivityTrainingLoad(activityDto.getActivityTrainingLoad());

        activity.setAverageRunningCadenceInStepsPerMinute(
                activityDto.getAverageRunningCadenceInStepsPerMinute()
        );

        activity.setMaxRunningCadenceInStepsPerMinute(
                activityDto.getMaxRunningCadenceInStepsPerMinute()
        );

        activity.setAverageStrideLength(activityDto.getAvgStrideLength());
        activity.setSteps(activityDto.getSteps());
        activity.setIsPersonalBest(activityDto.getPr());

        setComputedProperties(activity);

        return activity;
    }

    private static void setComputedProperties(Activity activity) {
        activity.setEffortLabel(EffortLabel.get(activity));
        activity.setAveragePace(PaceConverter.speedToPace(activity.getAverageSpeed()));
    }
}
