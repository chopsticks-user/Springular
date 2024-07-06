package com.frost.springular.object.response;

import java.time.Instant;

import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.request.CalendarEventRequest.Repeat;
import com.frost.springular.object.request.CalendarEventRequest.RepeatEvery;
import com.frost.springular.object.request.CalendarEventRequest.RepeatEvery.Unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEventReponse {
        private String id;
        private String title;
        private String description;
        private String color;
        private Instant start;
        private int durationMinutes;
        private Repeat repeat;
        private RepeatEvery repeatEvery;
        private UserInfoResponse userInfoResponseDto;

        public CalendarEventReponse(CalendarEventModel calendarEventEntity) {
                id = calendarEventEntity.getId();
                title = calendarEventEntity.getTitle();
                description = calendarEventEntity.getDescription();
                color = calendarEventEntity.getColor();
                start = calendarEventEntity.getStart();
                durationMinutes = calendarEventEntity.getDurationMinutes();
                repeat = calendarEventEntity.getRepeat();
                repeatEvery = repeat == Repeat.custom
                                ? new RepeatEvery(
                                                calendarEventEntity.getRepeatEveryValue(),
                                                calendarEventEntity.getRepeatEveryUnit())
                                : null;
                userInfoResponseDto = new UserInfoResponse(
                                calendarEventEntity.getUserEntity());
        }
}
