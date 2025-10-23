package familytree;

import java.util.concurrent.atomic.AtomicInteger;

public class PersonFactory {
    private static final AtomicInteger counter = new AtomicInteger(1);

    public static Person createPerson(String fullName, Gender gender, int birthYear, Integer deathYear) {
        String id = "P" + String.format("%03d", counter.getAndIncrement());
        int currentYear = 2025; // As per current date
        int age = currentYear - birthYear;
        if (age < 18) {
            return new Minor(id, fullName, gender, birthYear, deathYear);
        } else {
            return new Adult(id, fullName, gender, birthYear, deathYear);
        }
    }
}