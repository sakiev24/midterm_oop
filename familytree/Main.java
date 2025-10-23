package familytree;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        FamilyTree tree = new FamilyTree();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.equalsIgnoreCase("EXIT")) break;
            try {
                parseAndExecute(line, tree);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void parseAndExecute(String line, FamilyTree tree) {
        Scanner s = new Scanner(line);
        String cmd = s.next().toUpperCase();
        switch (cmd) {
            case "ADD_PERSON" -> {
                Pattern pat = Pattern.compile("ADD_PERSON \"(.*)\" (MALE|FEMALE|OTHER) (\\d+)( \\d+)?");
                Matcher m = pat.matcher(line);
                if (m.find()) {
                    String name = m.group(1);
                    Gender g = Gender.valueOf(m.group(2));
                    int by = Integer.parseInt(m.group(3));
                    Integer dy = m.group(4) != null ? Integer.parseInt(m.group(4).trim()) : null;
                    String id = tree.addPerson(name, g, by, dy);
                    System.out.println("-> " + id);
                } else {
                    throw new IllegalArgumentException("Invalid ADD_PERSON format");
                }
            }
            case "ADD_PARENT_CHILD" -> {
                String parentId = s.next();
                String childId = s.next();
                tree.addParentChild(parentId, childId);
                System.out.println("OK");
            }
            case "MARRY" -> {
                String a = s.next();
                String b = s.next();
                int y = s.nextInt();
                tree.marry(a, b, y);
                System.out.println("OK");
            }
            case "ANCESTORS" -> {
                String pid = s.next();
                int gen = s.nextInt();
                tree.printAncestors(pid, gen);
            }
            case "DESCENDANTS" -> {
                String pid = s.next();
                int gen = s.nextInt();
                tree.printDescendants(pid, gen);
            }
            case "SIBLINGS" -> {
                String pid = s.next();
                List<Person> sibs = tree.getSiblings(pid);
                if (sibs.isEmpty()) {
                    System.out.println("<none>");
                } else {
                    for (Person sib : sibs) {
                        System.out.println("- " + sib.toSummary());
                    }
                }
            }
            case "SHOW" -> {
                String pid = s.next();
                System.out.println(tree.show(pid));
            }
            default -> throw new IllegalArgumentException("Unknown command");
        }
        s.close();
    }
}