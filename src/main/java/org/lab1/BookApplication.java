package org.lab1;

import java.util.Scanner;

/**
 * The {@code BookApplication} class serves as the entry point for the book management system.
 *
 * <p>This class provides a text-based menu that allows users to interact with the book database.
 * Users can perform various operations such as:
 * <ul>
 *     <li>Printing all books with their associated authors.</li>
 *     <li>Printing all authors with their associated books.</li>
 *     <li>Editing book details.</li>
 *     <li>Editing author details.</li>
 *     <li>Adding new books to the database.</li>
 * </ul>
 * </p>
 *
 * <p>The application continuously runs in a loop until the user chooses to exit.</p>
 */
public class BookApplication {

    /**
     * The main entry point for the application.
     *
     * <p>This method initializes the {@code BookDatabaseManager} to load existing data from the database.
     * It then creates a {@code Library} instance to provide book and author management functionality.
     * A {@code Scanner} is used to capture user input for the text-based menu.</p>
     *
     * <p>The menu provides the following options:
     * <ul>
     *     <li>Option 1: Print all books with their associated authors.</li>
     *     <li>Option 2: Print all authors with their associated books.</li>
     *     <li>Option 3: Edit book attributes.</li>
     *     <li>Option 4: Edit author attributes.</li>
     *     <li>Option 5: Add a new book.</li>
     *     <li>Option 6: Quit the application.</li>
     * </ul>
     * </p>
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        BookDatabaseManager dbManager = new BookDatabaseManager();
        dbManager.loadData();
        Library library = new Library(dbManager);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // menu loop
        while (running) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Print all books (with authors)");
            System.out.println("2. Print all authors (with books)");
            System.out.println("3. Edit a book's attributes");
            System.out.println("4. Edit an author's attributes");
            System.out.println("5. Add a book");
            System.out.println("6. Quit");
            System.out.print("\nYour choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                library.printAllBooks();
            } else if (choice.equals("2")) {
                library.printAllAuthors();
            } else if (choice.equals("3")) {
                library.editBook(scanner);
            } else if (choice.equals("4")) {
                library.editAuthor(scanner);
            } else if (choice.equals("5")) {
                library.addBook(scanner);
            } else if (choice.equals("6")) {
                running = false;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
        System.out.println("Goodbye!");
    }
}
