# Family Tree Application
This is an in-memory Java console application for managing a family tree. It models persons with relationships (parent-child, spouses), prevents cycles and invalid states, and supports queries via CLI commands.

## Features
- Add persons with auto-generated IDs.
- Link parents to children (max 2 parents, no cycles).
- Marry persons (one active marriage, minors cannot marry).
- Queries: ancestors/descendants (printed as indented tree), siblings, show summary.
- In-memory storage using collections.

## OOP Concepts Used
- **Encapsulation**: All fields private, accessed via getters/setters with validation (e.g., years, names).
- **Inheritance**: Abstract `Person` extended by `Adult` and `Minor` for age-based rules.
- **Polymorphism**: Runtime polymorphism via `canMarry()` abstract method (Adults can marry, Minors cannot).
- **Abstraction**: Abstract `Person` class hides implementation details.
- **Access Modifiers**: Private fields, protected constructors, public methods.
- **Interfaces**: `RelativesQuery` for query methods, implemented by `FamilyTree`.
- **Composition**: `Person` composes `Marriage` objects and collections of other `Person` (parents/children).

## Design Patterns Used
- **Composite (required)**: Applied in `Person` class (fields: children, parents; methods: printAncestorTree, printDescendantTree).
- **Simple Factory**: Used in `PersonFactory` class (method: createPerson for subtype selection and ID generation).

## How to Run
- Compile: `javac -d bin src/main/java/familytree/*.java`
- Run: `java -cp bin familytree.Main`
- Commands:
  - `ADD_PERSON "<Full Name>" <Gender> <BirthYear> [DeathYear]` (e.g., `ADD_PERSON "Aizada Toktobekova" FEMALE 1975`)
  - `ADD_PARENT_CHILD <parentId> <childId>`
  - `MARRY <personAId> <personBId> <Year>`
  - `ANCESTORS <personId> <generations>`
  - `DESCENDANTS <personId> <generations>`
  - `SIBLINGS <personId>`
  - `SHOW <personId>`
  - `EXIT` to quit.

## Sample Outputs
- `ADD_PERSON "Aizada Toktobekova" FEMALE 1975` → `-> P001`
- `MARRY P001 P002 2009` → `OK`
- `ANCESTORS P003 2` → 
