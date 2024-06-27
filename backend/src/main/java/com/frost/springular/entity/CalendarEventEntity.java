package com.frost.springular.entity;

import java.time.Instant;

import com.frost.springular.dto.CalendarEventDto.Repeat;
import com.frost.springular.dto.CalendarEventDto.RepeatEvery;
import com.frost.springular.dto.CalendarEventDto.RepeatEvery.Unit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "calendar_events")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Instant start;

    @Column(nullable = false)
    private int durationMinutes;

    @Column(nullable = false)
    private Repeat repeat;

    @Column()
    private int repeatEveryValue;

    @Column()
    private Unit repeatEveryUnit;
}
