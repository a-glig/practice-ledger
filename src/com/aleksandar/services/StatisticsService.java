package com.aleksandar.services;

import com.aleksandar.model.Piece;
import com.aleksandar.model.PracticeSession;
import com.aleksandar.repository.PieceRepository;
import com.aleksandar.repository.SessionRepository;

import javax.swing.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class StatisticsService {
    private final PieceRepository pieceRepo;
    private final SessionRepository sessionRepo;

    public StatisticsService(PieceRepository pieceRepo, SessionRepository sessionRepo) {
        this.pieceRepo = pieceRepo;
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

    public int daysPracticedInMonth(LocalDate date) {
        return getPracticeDaysForMonth(date).toArray().length;
    }

    public ArrayList<Piece> getNeglectedRepertoire(int numberOfPieces) {
        ArrayList<Piece> repertoire = pieceRepo.getRepertoire();
        ArrayList<Piece> neglected = new ArrayList<>();

        sortByLastPractice(repertoire);

        numberOfPieces = Math.min(numberOfPieces, repertoire.size());
        for (int i = 0; i < numberOfPieces; i++) {
            neglected.add(repertoire.get(i));
        }
        return neglected;
    }

    public void sortByLastPractice(ArrayList<Piece> repertoire) {
        repertoire.removeIf(piece ->
                sessionRepo.findLatestDate(piece.getTitle()) == null
        );

        repertoire.sort((piece1, piece2) -> {
            LocalDate date1 = sessionRepo.findLatestDate(piece1.getTitle());
            LocalDate date2 = sessionRepo.findLatestDate(piece2.getTitle());

            return date1.compareTo(date2);
        });
    }

    public long getDaysNotPracticed(Piece piece, LocalDate date) {
        LocalDate lastPracticed = sessionRepo.findLatestDate(piece.getTitle());
        if (lastPracticed != null)
            return ChronoUnit.DAYS.between(lastPracticed, date);
        else
            return 0;
    }

}
