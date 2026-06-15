package com.aleksandar;

import java.io.*;
import java.util.ArrayList;

public class SessionRepository {

    private final File sessionFile = new File("sessions.txt");

    public void save(PracticeSession session) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(sessionFile, true))){
            writer.write(session.toFileString());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(PracticeSession session) {
        ArrayList<PracticeSession> sessions = findAll();
        sessions.remove(session);
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(sessionFile))) {
            for (PracticeSession currentSession : sessions) {
                writer.write(currentSession.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<PracticeSession> findAll() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(sessionFile))) {
            ArrayList<PracticeSession> sessions = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                sessions.add(new PracticeSession(
                        parts[0],
                        parts[1],
                        Integer.parseInt(parts[2]),
                        parts[3])
                );
            }
            return sessions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<PracticeSession> findByDate(String date) {
        ArrayList<PracticeSession> sessions = new ArrayList<>();
        for (PracticeSession session: findAll()) {
            if (date.equals(session.getDate())) {
                sessions.add(session);
            }
        }
        return sessions;
    }

}
