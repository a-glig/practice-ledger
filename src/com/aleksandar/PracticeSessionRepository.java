package com.aleksandar;

public class PracticeSessionRepository {

    private PracticeSession[] sessions;
    private int count;

    public PracticeSessionRepository(int capacity) {
        sessions = new PracticeSession[capacity];
        count = 0;
    }

    public void add(PracticeSession session) {
        if (count >= sessions.length) {
            System.out.println("The repository is full.");
            return;
        }
        sessions[count] = session;
        count++;
    }

    public void show() {
        if (count == 0) {
            System.out.println("No sessions in the repository.");
            return;
        }
        for (int i = 0; i < count; i++) {
            sessions[i].show();
        }
    }

}
