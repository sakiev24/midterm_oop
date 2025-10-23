package familytree;

public class Adult extends Person {
    protected Adult(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        super(id, fullName, gender, birthYear, deathYear);
    }

    @Override
    public boolean canMarry() {
        return true;
    }
}