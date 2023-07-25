# Text Search Engine in Java

This repository contains a console-based Java application for a simple text search engine that utilizes the concept of an "inverted index" to efficiently find matching lines in a text file. 

## Application Details

The application reads in a text file given as a command line argument, builds an inverted index based on the text file, and allows the user to interactively perform search operations.

## Features

- **Inverted Index**: The inverted index is a data structure that stores a mapping from content, such as words or numbers, to its locations in a document or a set of documents. In this application, it maps words to the lines in the text file they are found on.
- **Search Engine**: The search engine supports three different search strategies: `ALL`, `ANY`, and `NONE`.
   - `ALL`: The program should print lines containing all the words from the query.
   - `ANY`: The program should print the lines containing any of the words from the query.
   - `NONE`: The program should print lines that do not contain words from the query at all.
- **Interactive Menu**: The program has an interactive menu with options to perform a search or print all data.

## Usage

After starting the program with the name of the file as a command-line argument, it displays a menu with three options:

1. `Find a person`: Search for lines in the file based on a query. The user will be asked to choose a matching strategy (`ALL`, `ANY`, `NONE`) and then input the query.
2. `Print all people`: Prints all lines in the file.
3. `Exit`: Exits the program.

If the user selects an invalid option, they are asked to try again.

## Classes

- **Main**: The main class of the program, which checks the command-line arguments and starts the search engine.
- **SearchEngine**: This class manages all the core logic of the search engine. It builds the inverted index from the file, allows searching the index, and maintains the main interaction loop with the user. It also provides three different search methods: `searchAll()`, `searchAny()`, and `searchNone()`, corresponding to the three supported search strategies.
