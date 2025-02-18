package org.lab1;

import java.util.List;
import java.util.Scanner;

/**
 * The {@code Library} class provides methods for managing books and authors.
 *
 * <p>This class interacts with the {@code BookDatabaseManager} to perform the following operations:
 * <ul>
 *     <li>Print all books with their authors.</li>
 *     <li>Print all authors with their books.</li>
 *     <li>Edit a book's attributes.</li>
 *     <li>Edit an author's attributes.</li>
 *     <li>Add a new book along with its authors.</li>
 * </ul>
 * </p>
 */
public class Library {

    private final BookDatabaseManager dbManager;

    /**
     * Constructs a {@code Library} object and associates it with a {@code BookDatabaseManager}.
     *
     * @param dbManager the {@code BookDatabaseManager} instance used for database operations
     */
    public Library(BookDatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Prints all books along with their associated authors.
     * If no books are found, a message is displayed.
     */
    public void printAllBooks() {
        List<Book> books = dbManager.getBooks();

        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book book : books) {
                System.out.println(book);
                System.out.println("------------------------------");
            }
        }
    }

    /**
     * Prints all authors along with the books they have written.
     * If no authors are found, a message is displayed.
     */
    public void printAllAuthors() {
        List<Author> authors = dbManager.getAuthors();

        if (authors.isEmpty()) {
            System.out.println("No authors found.");
        } else {
            for (Author author : authors) {
                System.out.println(author);
                System.out.println("------------------------------");
            }
        }
    }

    /**
     * Edits a book's attributes based on user input.
     *
     * <p>The user is prompted to enter an ISBN, and if the book exists, they can update its title,
     * edition number, and copyright information. If no changes are made, the original values are retained.</p>
     *
     * @param scanner the {@code Scanner} instance used to receive user input
     */
    public void editBook(Scanner scanner) {
        System.out.print("Enter the ISBN of the book to edit: ");
        String isbn = scanner.nextLine();
        Book book = dbManager.getBookByISBN(isbn);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.print("Enter new title (or press Enter to keep [" + book.getTitle() + "]): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.trim().isEmpty()) {
            book.setTitle(newTitle);
        }

        System.out.print("Enter new edition number (or press Enter to keep [" + book.getEditionNumber() + "]): ");
        String editionInput = scanner.nextLine();
        if (!editionInput.trim().isEmpty()) {
            try {
                book.setEditionNumber(Integer.parseInt(editionInput));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Keeping current edition number.");
            }
        }

        System.out.print("Enter new copyright (or press Enter to keep [" + book.getCopyright() + "]): ");
        String newCopyright = scanner.nextLine();
        if (!newCopyright.trim().isEmpty()) {
            book.setCopyright(newCopyright);
        }

        if (dbManager.updateBook(book)) {
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Error updating book.");
        }
    }

    /**
     * Edits an author's attributes based on user input.
     *
     * <p>The user is prompted to enter an author ID, and if the author exists, they can update their
     * first and last name. If no changes are made, the original values are retained.</p>
     *
     * @param scanner the {@code Scanner} instance used to receive user input
     */
    public void editAuthor(Scanner scanner) {
        System.out.print("Enter the author ID to edit: ");
        try {
            int authorID = Integer.parseInt(scanner.nextLine());
            Author author = dbManager.getAuthorByID(authorID);

            if (author == null) {
                System.out.println("Author not found.");
                return;
            }

            System.out.print("Enter new first name (or press Enter to keep [" + author.getFirstName() + "]): ");
            String newFirstName = scanner.nextLine();
            if (!newFirstName.trim().isEmpty()) {
                author.setFirstName(newFirstName);
            }

            System.out.print("Enter new last name (or press Enter to keep [" + author.getLastName() + "]): ");
            String newLastName = scanner.nextLine();
            if (!newLastName.trim().isEmpty()) {
                author.setLastName(newLastName);
            }

            if (dbManager.updateAuthor(author)) {
                System.out.println("Author updated successfully.");
            } else {
                System.out.println("Error updating author.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid author ID.");
        }
    }

    /**
     * Adds a new book to the database, along with associated authors.
     *
     * <p>The user is prompted to enter book details such as ISBN, title, edition number, and copyright.
     * They can then associate existing authors or create new authors for the book. Once all authors are
     * added, the book is saved in the database.</p>
     *
     * @param scanner the {@code Scanner} instance used to receive user input
     */
    public void addBook(Scanner scanner) {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        if (dbManager.getBookByISBN(isbn) != null) {
            System.out.println("A book with this ISBN already exists.");
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter edition number: ");

        int editionNumber;
        try {
            editionNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid edition number.");
            return;
        }

        System.out.print("Enter copyright: ");
        String copyright = scanner.nextLine();

        Book newBook = new Book(isbn, title, editionNumber, copyright);

        boolean addingAuthors = true;
        while (addingAuthors) {
            System.out.println("\nSelect an option to add an author for this book:");
            System.out.println("1. Existing author");
            System.out.println("2. New author");
            System.out.println("3. Done adding authors");
            System.out.print("Your choice: ");

            String authChoice = scanner.nextLine();
            if (authChoice.equals("1")) {
                System.out.print("Enter author ID: ");
                try {
                    int authorID = Integer.parseInt(scanner.nextLine());
                    Author existingAuthor = dbManager.getAuthorByID(authorID);
                    if (existingAuthor != null) {
                        newBook.addAuthor(existingAuthor);
                        System.out.println("Author added.");
                    } else {
                        System.out.println("Author not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid author ID.");
                }
            } else if (authChoice.equals("2")) {
                System.out.print("Enter first name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter last name: ");
                String lastName = scanner.nextLine();
                Author newAuthor = new Author(0, firstName, lastName);
                if (dbManager.addAuthor(newAuthor)) {
                    newBook.addAuthor(newAuthor);
                    System.out.println("New author created and added.");
                } else {
                    System.out.println("Error creating new author.");
                }
            } else if (authChoice.equals("3")) {
                addingAuthors = false;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        if (dbManager.addBook(newBook)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Error adding book.");
        }
    }
}


