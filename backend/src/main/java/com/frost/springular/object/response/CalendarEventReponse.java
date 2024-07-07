package com.frost.springular.object.response;

import java.time.Instant;

import com.frost.springular.object.enumerated.CalendarEventRepeat;
import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.request.CalendarEventRequest;

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
        private CalendarEventRepeat repeat;
        private CalendarEventRequest.RepeatEvery repeatEvery;
        private UserInfoResponse userInfoResponseDto;

        public CalendarEventReponse(CalendarEventModel calendarEventEntity) {
                id = calendarEventEntity.getId();
                title = calendarEventEntity.getTitle();
                description = calendarEventEntity.getDescription();
                color = calendarEventEntity.getColor();
                start = calendarEventEntity.getStart();
                durationMinutes = calendarEventEntity.getDurationMinutes();
                repeat = calendarEventEntity.getRepeat();
                repeatEvery = repeat == CalendarEventRepeat.custom
                                ? new CalendarEventRequest.RepeatEvery(
                                                calendarEventEntity.getRepeatEveryValue(),
                                                calendarEventEntity.getRepeatEveryUnit())
                                : null;
                userInfoResponseDto = new UserInfoResponse(
                                calendarEventEntity.getUserEntity());
        }
}
