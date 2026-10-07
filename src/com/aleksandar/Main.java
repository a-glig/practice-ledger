package com.aleksandar;

import com.aleksandar.repository.PieceRepository;
import com.aleksandar.repository.SessionRepository;
import com.aleksandar.services.StatisticsService;
import com.aleksandar.ui.MainFrame;

import javax.swing.*;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        PieceRepository pieceRepo = new PieceRepository();
        SessionRepository sessionRepo = new SessionRepository();
        StatisticsService statistics = new StatisticsService(sessionRepo);
        SwingUtilities.invokeLater(() ->
                new MainFrame(pieceRepo, sessionRepo, statistics)
        );

        // Test statistics service
        var testDate = LocalDate.parse("2026-06-15");
        var practiceTimeDay = statistics.getPracticeDurationForDate(testDate);
        var practiceTimeWeek = statistics.getPracticeDurationForWeek(testDate);
        var practiceDaysMonth = statistics.getPracticeDaysForMonth(testDate);
        System.out.println(practiceTimeDay);
        System.out.println(practiceTimeWeek);
        System.out.println(practiceDaysMonth);

    }
}
