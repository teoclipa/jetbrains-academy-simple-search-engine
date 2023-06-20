package search;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2 || !args[0].equals("--data")) {
            System.out.println("Invalid command-line arguments. Usage: --data <filename>");
            return;
        }

        String filename = args[1];


        try {
            SearchEngine searchEngine = new SearchEngine(filename);
            searchEngine.start();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            System.out.println(e.getMessage());
        }

    }



}
