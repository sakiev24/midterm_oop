package familytree;

public class Minor extends Person {
    protected Minor(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        super(id, fullName, gender, birthYear, deathYear);
    }

    @Override
    public boolean canMarry() {
        return false;
    }
}