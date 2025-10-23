package familytree;

import java.util.List;

public interface RelativesQuery {
    List<Person> getAncestorsAtGeneration(String personId, int generation);
    List<Person> getDescendantsAtGeneration(String personId, int generation);
    List<Person> getSiblings(String personId);
    List<Person> getChildren(String personId);
    Person getSpouse(String personId);
}