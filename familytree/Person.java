package familytree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public abstract class Person {
    private final String id;
    private String fullName;
    private Gender gender;
    private int birthYear;
    private Integer deathYear;
    private final Set<Person> parents = new HashSet<>();
    private final List<Person> children = new ArrayList<>();
    private final List<Marriage> marriages = new ArrayList<>();

    protected Person(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID cannot be blank");
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Full name cannot be blank");
        if (gender == null) throw new IllegalArgumentException("Gender cannot be null");
        if (birthYear < 0) throw new IllegalArgumentException("Birth year must be non-negative");
        if (deathYear != null && deathYear < birthYear) throw new IllegalArgumentException("Death year cannot be before birth year");

        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Full name cannot be blank");
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        if (gender == null) throw new IllegalArgumentException("Gender cannot be null");
        this.gender = gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        if (birthYear < 0) throw new IllegalArgumentException("Birth year must be non-negative");
        if (this.deathYear != null && this.deathYear < birthYear) throw new IllegalArgumentException("Death year cannot be before birth year");
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        if (deathYear != null && deathYear < this.birthYear) throw new IllegalArgumentException("Death year cannot be before birth year");
        this.deathYear = deathYear;
    }

    public boolean isAlive() {
        return deathYear == null;
    }

    public int ageIn(int year) {
        if (year < birthYear) throw new IllegalArgumentException("Year cannot be before birth year");
        if (deathYear != null && year > deathYear) {
            return deathYear - birthYear;
        }
        return year - birthYear;
    }

    public void addParent(Person parent) {
        if (parent == null) throw new IllegalArgumentException("Parent cannot be null");
        if (parents.size() >= 2) throw new IllegalArgumentException("Cannot have more than 2 parents");
        if (parent == this) throw new IllegalArgumentException("Cannot be own parent");
        if (this.isAncestorOf(parent)) throw new IllegalArgumentException("Cycle detected");
        parents.add(parent);
        parent.children.add(this);
    }

    public void addChild(Person child) {
        if (child == null) throw new IllegalArgumentException("Child cannot be null");
        child.addParent(this);
    }

    public abstract boolean canMarry();

    public void marry(Person spouse, int year) {
        if (spouse == null) throw new IllegalArgumentException("Spouse cannot be null");
        if (spouse == this) throw new IllegalArgumentException("Cannot marry self");
        if (!this.canMarry() || !spouse.canMarry()) throw new IllegalArgumentException("One or both cannot marry (e.g., minor)");
        if (this.getCurrentSpouse() != null) throw new IllegalArgumentException("Already married");
        if (spouse.getCurrentSpouse() != null) throw new IllegalArgumentException("Spouse already married");
        if (year < this.birthYear || year < spouse.getBirthYear()) throw new IllegalArgumentException("Marriage year before birth");

        Marriage m1 = new Marriage(spouse, year);
        Marriage m2 = new Marriage(this, year);
        this.marriages.add(m1);
        spouse.marriages.add(m2);
    }

    public Person getCurrentSpouse() {
        if (marriages.isEmpty()) return null;
        Marriage last = marriages.get(marriages.size() - 1);
        if (last.getDivorceYear() == null) return last.getSpouse();
        return null;
    }

    public boolean isAncestorOf(Person p) {
        if (p == null) return false;
        Set<Person> visited = new HashSet<>();
        Queue<Person> queue = new LinkedList<>();
        queue.add(this);
        while (!queue.isEmpty()) {
            Person curr = queue.poll();
            if (curr == p) return true;
            if (visited.contains(curr)) continue;
            visited.add(curr);
            queue.addAll(curr.children);
        }
        return false;
    }

    public List<Person> getParents() {
        return new ArrayList<>(parents);
    }

    public List<Person> getChildren() {
        return new ArrayList<>(children);
    }

    // Composite pattern: recursive print for ancestors (upwards tree)
    public void printAncestorTree(int level, int remainingGen) {
        System.out.println("-".repeat(level + 1) + " " + toSummary());
        if (remainingGen == 0) return;
        for (Person parent : parents) {
            parent.printAncestorTree(level + 1, remainingGen - 1);
        }
    }

    // Composite pattern: recursive print for descendants (downwards tree)
    public void printDescendantTree(int level, int remainingGen) {
        System.out.println("-".repeat(level + 1) + " " + toSummary());
        if (remainingGen == 0) return;
        for (Person child : children) {
            child.printDescendantTree(level + 1, remainingGen - 1);
        }
    }

    public String toSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(fullName).append(" (b.").append(birthYear);
        if (deathYear != null) sb.append(" d.").append(deathYear);
        sb.append(")");
        return sb.toString();
    }
}