package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class SearchEngine {
    private final Scanner fileScanner;
    private final Scanner scanner;

    private final Map<String, List<Integer>> invertedIndex;
    private final List<String> people;

    public SearchEngine(String filename) throws FileNotFoundException {
        File file = new File(filename);
        this.fileScanner = new Scanner(file);
        this.scanner = new Scanner(System.in);
        this.invertedIndex = new HashMap<>();
        this.people = new ArrayList<>();
    }

    private void buildInvertedIndex() {
        int lineIndex = 0;
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            people.add(line);
            String[] words = line.toLowerCase().split("\\s+");
            for (String word : words) {
                invertedIndex.putIfAbsent(word, new ArrayList<>());
                invertedIndex.get(word).add(lineIndex);
            }
            lineIndex++;
        }
        fileScanner.close();
    }

    private List<String> searchAll(List<String> queryWords) {
        Set<Integer> resultIndexes = new HashSet<>();
        boolean firstWord = true;

        for (String word : queryWords) {
            List<Integer> lineIndexes = invertedIndex.getOrDefault(word, new ArrayList<>());
            if (firstWord) {
                resultIndexes.addAll(lineIndexes);
                firstWord = false;
            } else {
                resultIndexes.retainAll(lineIndexes);
            }
        }

        List<String> foundPeople = new ArrayList<>();
        for (int index : resultIndexes) {
            foundPeople.add(people.get(index));
        }

        return foundPeople;
    }

    private List<String> searchAny(List<String> queryWords) {
        Set<Integer> resultIndexes = new HashSet<>();

        for (String word : queryWords) {
            List<Integer> lineIndexes = invertedIndex.getOrDefault(word, new ArrayList<>());
            resultIndexes.addAll(lineIndexes);
        }

        List<String> foundPeople = new ArrayList<>();
        for (int index : resultIndexes) {
            foundPeople.add(people.get(index));
        }

        return foundPeople;
    }

    private List<String> searchNone(List<String> queryWords) {
        Set<Integer> resultIndexes = new HashSet<>();

        for (String word : queryWords) {
            List<Integer> lineIndexes = invertedIndex.getOrDefault(word, new ArrayList<>());
            resultIndexes.addAll(lineIndexes);
        }

        Set<Integer> allIndexes = new HashSet<>();
        for (int i = 0; i < people.size(); i++) {
            allIndexes.add(i);
        }

        Set<Integer> finalResultIndexes = resultIndexes;
        resultIndexes = allIndexes.stream().filter(index -> !finalResultIndexes.contains(index)).collect(Collectors.toSet());

        List<String> foundPeople = new ArrayList<>();
        for (int index : resultIndexes) {
            foundPeople.add(people.get(index));
        }

        return foundPeople;
    }

    public void searchQuery() {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine().toUpperCase();
        System.out.println("\nEnter a name or email to search all suitable people:");
        String query = scanner.nextLine().toLowerCase();
        List<String> queryWords = Arrays.asList(query.split("\\s+"));

        List<String> foundPeople;

        switch (strategy) {
            case "ALL" -> foundPeople = searchAll(queryWords);
            case "ANY" -> foundPeople = searchAny(queryWords);
            case "NONE" -> foundPeople = searchNone(queryWords);
            default -> {
                System.out.println("Invalid matching strategy. Try again.");
                return;
            }
        }

        if (foundPeople.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            System.out.println(foundPeople.size() + " person(s) found:");
            foundPeople.forEach(System.out::println);
        }
    }

    private void showMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    public void start() {
        buildInvertedIndex(); // Build the inverted index before the loop
        showMenu();
        String option = scanner.nextLine();
        while (!option.equals("0")) {
            switch (option) {
                case "1" -> searchQuery();
                case "2" -> {
                    System.out.println("=== List of people ===");
                    people.forEach(System.out::println);
                }
                default -> System.out.println("Incorrect option! Try again.");
            }
            showMenu();
            option = scanner.nextLine();
        }
        System.out.println("\nBye!");
        scanner.close();
    }
}
