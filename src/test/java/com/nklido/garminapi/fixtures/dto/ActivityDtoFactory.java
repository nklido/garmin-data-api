package com.nklido.garminapi.fixtures.dto;

import com.github.javafaker.Faker;
import com.nklido.garminapi.adapter.garmin.dto.ActivityDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class ActivityDtoFactory {

    private static Faker faker = new Faker();

    private ActivityDtoFactory() {}

    @SafeVarargs
    public static ActivityDto create(Consumer<ActivityDto>... modifiers) {
        ActivityDto dto = new ActivityDto();
        // Faker‐powered defaults:
        dto.setActivityId(faker.random().nextLong());
        dto.setActivityName(faker.country().capital() + " Run");


        Instant pastDate = faker.date().past(10, TimeUnit.DAYS).toInstant();

        dto.setStartTimeGMT(LocalDateTime.ofInstant(pastDate, ZoneId.of("GMT")));
        dto.setStartTimeLocal(LocalDateTime.ofInstant(pastDate, ZoneId.of("UTC")));


        // apply any overrides
        for (Consumer<ActivityDto> mod : modifiers) {
            mod.accept(dto);
        }
        return dto;
    }
}