package familytree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class FamilyTree implements RelativesQuery {
    private final Map<String, Person> persons = new HashMap<>();

    public String addPerson(String fullName, Gender gender, int birthYear, Integer deathYear) {
        Person person = PersonFactory.createPerson(fullName, gender, birthYear, deathYear);
        persons.put(person.getId(), person);
        return person.getId();
    }

    public void addParentChild(String parentId, String childId) {
        Person parent = getPerson(parentId);
        Person child = getPerson(childId);
        child.addParent(parent);
    }

    public void marry(String personAId, String personBId, int year) {
        Person a = getPerson(personAId);
        Person b = getPerson(personBId);
        a.marry(b, year);
    }

    public void printAncestors(String personId, int generations) {
        Person p = getPerson(personId);
        p.printAncestorTree(0, generations);
    }

    public void printDescendants(String personId, int generations) {
        Person p = getPerson(personId);
        p.printDescendantTree(0, generations);
    }

    @Override
    public List<Person> getSiblings(String personId) {
        Person p = getPerson(personId);
        Set<Person> siblings = new HashSet<>();
        for (Person parent : p.getParents()) {
            for (Person child : parent.getChildren()) {
                if (!child.equals(p)) {
                    siblings.add(child);
                }
            }
        }
        return new ArrayList<>(siblings);
    }

    @Override
    public List<Person> getChildren(String personId) {
        Person p = getPerson(personId);
        return p.getChildren();
    }

    @Override
    public Person getSpouse(String personId) {
        Person p = getPerson(personId);
        return p.getCurrentSpouse();
    }

    public String show(String personId) {
        Person p = getPerson(personId);
        String spouseId = p.getCurrentSpouse() != null ? p.getCurrentSpouse().getId() : null;
        int childCount = p.getChildren().size();
        return p.getId() + " | " + p.getFullName() + " | " + p.getGender() + " | b." + p.getBirthYear() +
                (p.getDeathYear() != null ? " d." + p.getDeathYear() : "") +
                (spouseId != null ? " | spouse=" + spouseId : "") + " | children=" + childCount;
    }

    private Person getPerson(String id) {
        Person p = persons.get(id);
        if (p == null) throw new IllegalArgumentException("Unknown ID: " + id);
        return p;
    }

    // Returns persons exactly at the specified generation back (0 = self, 1 = parents, etc.)
    @Override
    public List<Person> getAncestorsAtGeneration(String personId, int generation) {
        if (generation < 0) throw new IllegalArgumentException("Generation cannot be negative");
        Person start = getPerson(personId);
        List<Person> result = new ArrayList<>();
        if (generation == 0) {
            result.add(start);
            return result;
        }
        Set<Person> visited = new HashSet<>();
        Queue<Person> queue = new LinkedList<>();
        queue.add(start);
        int currentGen = 0;
        while (!queue.isEmpty() && currentGen < generation) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Person curr = queue.poll();
                for (Person parent : curr.getParents()) {
                    if (!visited.contains(parent)) {
                        visited.add(parent);
                        queue.add(parent);
                    }
                }
            }
            currentGen++;
        }
        if (currentGen == generation) {
            result.addAll(queue);
        }
        return result;
    }

    // Similar for descendants
    @Override
    public List<Person> getDescendantsAtGeneration(String personId, int generation) {
        if (generation < 0) throw new IllegalArgumentException("Generation cannot be negative");
        Person start = getPerson(personId);
        List<Person> result = new ArrayList<>();
        if (generation == 0) {
            result.add(start);
            return result;
        }
        Set<Person> visited = new HashSet<>();
        Queue<Person> queue = new LinkedList<>();
        queue.add(start);
        int currentGen = 0;
        while (!queue.isEmpty() && currentGen < generation) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Person curr = queue.poll();
                for (Person child : curr.getChildren()) {
                    if (!visited.contains(child)) {
                        visited.add(child);
                        queue.add(child);
                    }
                }
            }
            currentGen++;
        }
        if (currentGen == generation) {
            result.addAll(queue);
        }
        return result;
    }
}