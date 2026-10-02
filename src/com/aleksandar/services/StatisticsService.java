package com.aleksandar.services;

import com.aleksandar.model.PracticeSession;
import com.aleksandar.repository.SessionRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class StatisticsService {
    private final SessionRepository sessionRepo;

    public StatisticsService(SessionRepository sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    public int getPracticeDurationForDate(LocalDate date) {
        int practiceDuration = 0;
        if (sessionRepo.containsDate(date)) {
            for (PracticeSession session: sessionRepo.findByDate(date)) {
                practiceDuration += session.getDuration();
            }
        }
        return practiceDuration;
    }

    public int getPracticeDurationForWeek(LocalDate date) {
        int practiceDuration = 0;
        LocalDate weekStart = date.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = date.with(DayOfWeek.SUNDAY);
        for (LocalDate day = weekStart; !day.isAfter(weekEnd); day = day.plusDays(1)) {
            if (sessionRepo.containsDate(day)) {
                practiceDuration += getPracticeDurationForDate(day);
            }
        }
        return practiceDuration;
    }

    public ArrayList<LocalDate> getPracticeDaysForMonth(LocalDate date) {
        ArrayList<LocalDate> practiceDays = new ArrayList<>();
        YearMonth month = YearMonth.from(date);
        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            LocalDate currentDate = month.atDay(day);
            if (sessionRepo.containsDate(currentDate)) {
                practiceDays.add(currentDate);
            }
        }
        return practiceDays;
    }
}
